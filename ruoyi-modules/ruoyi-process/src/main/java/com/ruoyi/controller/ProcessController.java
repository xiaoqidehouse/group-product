package com.ruoyi.controller;
import com.ruoyi.domain.Process;
import com.ruoyi.service.ProcessService;
import com.ruoyi.utils.Result;
import com.ruoyi.utils.SmsUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.spring.kun.common.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("process")
public class ProcessController {


    @Autowired
    ProcessService processService;

    @RequestMapping("list")
    public Result listall(@RequestBody Process process) {

        List <Process> list = processService.querymes(process);

        return Result.success(list);
    }

    //未加工的数据
    @RequestMapping("nolist")
    public Result nolist() {

        List <Process> list = processService.nolist();

        return Result.success(list);
    }

    @Autowired
    RedisTemplate redisTemplate;

    //添加(防重复)
    @RequestMapping("saveprocess")
    public Result saveprocess(@RequestBody Process process) {

        Boolean pro = redisTemplate.opsForValue().setIfAbsent(process.toString(), "pro", 10, TimeUnit.SECONDS);

        if (pro) {

            processService.save(process);

            return Result.success("提交成功");

        } else {
            return Result.fail("提交失败，请勿重复提交");
        }

    }

    //图片上传
    @RequestMapping("picload")
    @CrossOrigin
    public Result picload(MultipartFile file) throws Exception{

        String filename = file.getOriginalFilename();
        filename = UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf("."));

        MinioClient.builder()
                .endpoint("http://192.168.137.1:9000")
                .build()
                .putObject(
                        PutObjectArgs.builder()
                                .bucket("shop")
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .object(filename)
                                .build()
                );

            return Result.success(200, "上传成功", true, "http://192.168.137.1:9000/shop/" + filename);
    }

    //回显
    @RequestMapping("updateprocess")
    public Result updateprocess(String id){

        Process byId = processService.getById(id);

        return Result.success(byId);
    }

    //修改数据
    @RequestMapping("proupByid")
    public Result proupByid(String id){

        Boolean pro = processService.proupById(id);

        if (pro){
            return Result.success("修改成功");
        }else {
            return Result.fail("修改失败");
        }
    }

    //修改数据
    @RequestMapping("updatepro")
    public Result updatepro(@RequestBody Process process){

        processService.updateById(process);

        return Result.success(200, "修改成功", true, null);
    }

    //删除数据(彻底删除)
    @RequestMapping("delprocess")
    public Result delprocess(String id){

        boolean b = processService.removeById(id);
        if (b){
            return Result.success("删除成功");
        }

        return Result.success("删除失败");
    }

    //excel导出数据
    @RequestMapping("goexcel")
    public void excel(HttpServletResponse response){

        List<Process> list = processService.list();

        ExcelUtils.exportExcel(list,"农产品加工信息","sheet",response);

    }

    //一键发货
    @RequestMapping("sendpro")
    public void sendpro(HttpServletResponse response) throws IOException {

        SmsUtils.sendSms("17793518786", "平台已经主动为您接单，请及时登录平台查看,祝您一路平安", "5");

        System.out.println("+4651548651发送成功");

        response.sendRedirect("http://localhost/process/fufu");
    }

}