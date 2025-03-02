package com.emos.wx.db.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import lombok.Data;

/**
 * 用户表
 * tb_user
 */
@Data
public class TbUser implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TbUser)) return false;
        TbUser tbUser = (TbUser) o;
        return Objects.equals(getId(), tbUser.getId()) && Objects.equals(getOpenId(), tbUser.getOpenId()) && Objects.equals(getNickname(), tbUser.getNickname()) && Objects.equals(getPhoto(), tbUser.getPhoto()) && Objects.equals(getName(), tbUser.getName()) && Objects.equals(getSex(), tbUser.getSex()) && Objects.equals(getTel(), tbUser.getTel()) && Objects.equals(getEmail(), tbUser.getEmail()) && Objects.equals(getHiredate(), tbUser.getHiredate()) && Objects.equals(getRole(), tbUser.getRole()) && Objects.equals(getRoot(), tbUser.getRoot()) && Objects.equals(getDeptId(), tbUser.getDeptId()) && Objects.equals(getStatus(), tbUser.getStatus()) && Objects.equals(getCreateTime(), tbUser.getCreateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOpenId(), getNickname(), getPhoto(), getName(), getSex(), getTel(), getEmail(), getHiredate(), getRole(), getRoot(), getDeptId(), getStatus(), getCreateTime());
    }

    /**
     * 长期授权字符串
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像网址
     */
    private String photo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Object sex;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 入职日期
     */
    private Date hiredate;

    /**
     * 角色
     */
    private Object role;

    /**
     * 是否是超级管理员
     */
    private Boolean root;

    /**
     * 部门编号
     */
    private Integer deptId;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}