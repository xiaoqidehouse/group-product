package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.domain.BingBaoBiao;
import com.ruoyi.domain.Process;
import com.ruoyi.service.ProcessService;
import com.ruoyi.mapper.ProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author iloveit
* @description 针对表【process(process:加工记录表)】的数据库操作Service实现
* @createDate 2024-02-28 21:49:17
*/
@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process>
    implements ProcessService{

    @Autowired
    ProcessMapper processMapper;

    @Override
    public List<Process> querymes(Process process) {
        return processMapper.querymes(process);
    }

    @Override
    public List<Process> nolist() {
        return processMapper.nolist();
    }

    @Override
    public Boolean proupById(String id) {
        return processMapper.proupById(id);
    }

    @Override
    public List<Process> getxy() {
        return processMapper.getxy();
    }

    @Override
    public List<BingBaoBiao> getbz() {
        return processMapper.getbz();
    }
}




