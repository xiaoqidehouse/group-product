package com.ruoyi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.domain.BingBaoBiao;
import com.ruoyi.domain.Process;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author iloveit
* @description 针对表【process(process:加工记录表)】的数据库操作Service
* @createDate 2024-02-28 21:49:17
*/
public interface ProcessService extends IService<Process> {


    List<Process> querymes(Process process);

    List<Process> nolist();

    Boolean proupById(String id);

    List<Process> getxy();

    List<BingBaoBiao> getbz();
}
