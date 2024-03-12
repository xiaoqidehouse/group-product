package com.ruoyi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.domain.Apply;
import com.ruoyi.domain.User;
import com.ruoyi.service.ApplyService;
import com.ruoyi.service.UserService;
import com.ruoyi.utils.Result;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("act")
public class ActivityController {

    @Autowired
    UserService userService;

    @RequestMapping("login")
    public Result login(@RequestBody User user){

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",user.getName());

        User one = userService.getOne(queryWrapper);

        if (one==null){

            return Result.fail("输入的账号不存在");

        }

        if (!one.getPwd().equals(user.getPwd())){

            return Result.fail("密码输入错误");

        }

        return Result.success(one);

    }

    @Autowired
    HistoryService historyService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    ApplyService applyService;

    @RequestMapping("savework")
    public void save(@RequestBody Apply apply){

        applyService.save(apply);

        String s = apply.getId() + "";

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("lipei", s);

        System.out.println("实例创建成功:"+processInstance.getId());

    }

    @RequestMapping("allList")
    public  List<Apply> allList(String roleName){

        List<Apply> arrayList = new ArrayList();

        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("lipei")
                .taskAssignee(roleName)
                .list();

        for (Task task : list) {

            Map<String, Object> map = new HashMap();

            map.put("id",task.getId());
            map.put("name",task.getName());
            map.put("assignee",task.getAssignee());

            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();

            String businessKey = historicProcessInstance.getBusinessKey();

            Apply byId = applyService.getById(businessKey);
            map.put("num",byId.getNum());
            map.put("sex",byId.getSex());
            map.put("reason",byId.getReason());
            map.put("tel",byId.getTel());
            map.put("shenqing",byId.getShenqing());
            map.put("cxdate",byId.getCxdate());
            map.put("address",byId.getAddress());
            map.put("email",byId.getEmail());

            byId.setTask(map);
            arrayList.add(byId);
        }

        return arrayList;

    }

    @RequestMapping("agreeone")
    public void agreeone(HttpServletResponse response,String taskId) throws IOException {

        taskService.complete(taskId);

        response.sendRedirect("http://localhost/inventory/work");

    }

    @RequestMapping("agreetwo")
    public void agreetwo(HttpServletResponse response,String taskId, String to) throws IOException {

        Map<String,Object> map = new HashMap();

        if (to!=null){
            map.put("to",to);
            taskService.complete(taskId,map);
        }

        System.out.println("主任审核通过");

        response.sendRedirect("http://localhost/inventory/work");

    }

    @RequestMapping("agreethree")
    public void agreethree(HttpServletResponse response,String taskId) throws Exception {

        taskService.complete(taskId);
        response.sendRedirect("http://localhost/inventory/work");

    }

    @Autowired
    ProcessEngine processEngine;

    @RequestMapping("findhis")
    public Result queryHisTaskInstanceByAssignee() {

        List<HistoricTaskInstance> list = processEngine.getHistoryService() // 历史任务Service
                .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                .finished() // 查询已经完成的任务
                .list();

        return Result.success(list);


    }

}
