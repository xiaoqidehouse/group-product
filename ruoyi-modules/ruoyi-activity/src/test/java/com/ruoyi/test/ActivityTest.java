package com.ruoyi.test;


import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityTest {

    @Resource
    RepositoryService repositoryService;

    /**
     * 生成数据库
     */
    @Test
    public void credel() {

        repositoryService.createDeployment()
                .name("lipei")
                .addClasspathResource("process/diagram.png")
                .addClasspathResource("process/lipei.bpmn20.xml")
                .deploy();

    }

    @Autowired
    HistoryService historyService;

    @Autowired
    ProcessEngine processEngine;

    @Test
    public void queryHisTaskInstanceByAssignee() {

        List<HistoricTaskInstance> list = processEngine.getHistoryService() // 历史任务Service
                .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                .finished() // 查询已经完成的任务
                .list();

        for (HistoricTaskInstance hti : list) {

            System.err.println("任务ID:" + hti.getId());
            System.err.println("流程实例ID:" + hti.getProcessInstanceId());
            System.err.println("审批人：" + hti.getAssignee());
            System.err.println("创建时间：" + hti.getCreateTime());
            System.err.println("结束时间：" + hti.getEndTime());
            System.err.println("===========================");

        }

    }
}

