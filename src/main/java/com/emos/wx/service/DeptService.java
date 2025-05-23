package com.emos.wx.service;

import com.emos.wx.db.pojo.TbDept;

import java.util.List;

public interface DeptService {
    public List<TbDept> searchAllDept();

    public int insertDept(String deptName);

    public void deleteDeptById(int id);

    public void updateDeptById(TbDept entity);
}