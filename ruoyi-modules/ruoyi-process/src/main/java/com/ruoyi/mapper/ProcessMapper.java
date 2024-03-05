package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.domain.BingBaoBiao;
import com.ruoyi.domain.Process;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author iloveit
* @description 针对表【process(process:加工记录表)】的数据库操作Mapper
* @createDate 2024-02-28 21:49:17
* @Entity com.ruoyi.domain.Process
*/
public interface ProcessMapper extends BaseMapper<Process> {


    List<Process> querymes(Process process);


    List<Process> nolist();

    Boolean proupById(String id);

    List<Process> getxy();

    List<BingBaoBiao> getbz();
}




