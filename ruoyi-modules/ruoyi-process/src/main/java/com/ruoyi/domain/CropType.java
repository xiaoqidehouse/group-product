package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName crop_type
 */
@TableName(value ="crop_type")
@Data
public class CropType implements Serializable {
    /**
     * 类型编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 总类型
     */
    private String typeName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}