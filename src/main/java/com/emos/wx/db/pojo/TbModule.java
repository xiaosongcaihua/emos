package com.emos.wx.db.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 模块资源表
 * tb_module
 */
@Data
public class TbModule implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 模块编号
     */
    private String moduleCode;

    /**
     * 模块名称
     */
    private String moduleName;

    private static final long serialVersionUID = 1L;
}