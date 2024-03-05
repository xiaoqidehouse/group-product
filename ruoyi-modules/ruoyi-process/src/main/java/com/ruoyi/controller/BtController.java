package com.ruoyi.controller;

import com.bw.springboot.starter.upload.model.Chunk;
import com.bw.springboot.starter.upload.service.FileService;
import com.bw.springboot.starter.upload.util.FileResult;
import com.ruoyi.domain.BingBaoBiao;
import com.ruoyi.domain.Process;
import com.ruoyi.service.BtService;
import com.ruoyi.service.ProcessService;
import com.ruoyi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("bt")
public class BtController {

    @Autowired
    BtService btService;

    @Autowired
    ProcessService processService;

    @RequestMapping("sumallmes")
    public Map<String,Object> savemes(){

        ArrayList<Object> x = new ArrayList<>();
        ArrayList<Object> y = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();

        List<Process> list =  processService.getxy();

        for (Process process : list) {

            if (process.getXdata().equals("0")){

                x.add("未加工");

            }else{

                x.add("已完成");

            }
            y.add(process.getYdata());
        }

        map.put("x",x);
        map.put("y",y);

        return map;

    }

    /**
     * 饼状报表
     */
    @RequestMapping("BingBaoBiao")
    public List<BingBaoBiao> bbb(){
        List<BingBaoBiao> list =  processService.getbz();

        for (BingBaoBiao bingBaoBiao : list) {

            if (bingBaoBiao.getName().equals("0")){

                bingBaoBiao.setName("未加工");

            }else {

                bingBaoBiao.setName("已加工");

            }

        }

        return list;
    }

    //大文件上传
    @Autowired
    FileService fileService;

    @CrossOrigin
    @RequestMapping("bigupload")
    public Result bigupload(@ModelAttribute Chunk chunk) throws InterruptedException {

        fileService.postFileUpload(chunk);

        if (chunk.getTotalChunks().equals(chunk.getChunkNumber())){

            TimeUnit.SECONDS.sleep(2);

            FileResult fileResult = fileService.mergeFile(chunk);

            return Result.success("文件合并");
        }

        return Result.success("大文件上传成功");

    }


}
