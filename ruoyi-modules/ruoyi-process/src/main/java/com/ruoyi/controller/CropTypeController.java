package com.ruoyi.controller;

import com.ruoyi.domain.CropType;
import com.ruoyi.service.CropTypeService;
import com.ruoyi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/croptype")
public class CropTypeController {

    @Autowired
    CropTypeService cropTypeService;

    @RequestMapping("typeList")
    public Result typeList(){

        List<CropType> list = cropTypeService.list();

        return Result.success(list);
    }



}
