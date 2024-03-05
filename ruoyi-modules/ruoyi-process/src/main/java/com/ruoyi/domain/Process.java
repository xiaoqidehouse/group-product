package com.ruoyi.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * process:加工记录表
 * @TableName process
 */
@TableName(value ="process")
@Data
public class Process implements Serializable {
    /**
     * 加工跟踪表id
     */
    @TableId(type = IdType.AUTO)
    @ExcelProperty("加工跟踪表id")
    private Integer id;

    /**
     * 加工时间
     */

    @ExcelProperty("加工时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date processTime;

    /**
     * 记录人员名称
     */
    @ExcelProperty("记录人员名称")
    private String processRecord;

    /**
     * 加工商品名称(外键关联农作物表)
     */
    @ExcelIgnore
    private Integer cropId;

    /**
     * 加工环节
     */
    @ExcelProperty("加工环节")
    private String processLink;

    /**
     * 加工介绍
     */
    @ExcelProperty("加工介绍")
    private String processTest;

    /**
     * 状态(0:加工完成,1:未加工)
     */
    @ExcelIgnore
    private String processStatus;

    /**
     * 
     */

    @ExcelIgnore
    private String processImg;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @TableField(exist = false)
    private String xdata;

    @ExcelIgnore
    @TableField(exist = false)
    private Integer ydata;

    @ExcelIgnore
    @TableField(exist = false)
    private String typeName;
}