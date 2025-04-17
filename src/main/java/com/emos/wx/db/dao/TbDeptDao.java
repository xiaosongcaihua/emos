package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbDept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface TbDeptDao {
    int deleteByPrimaryKey(Integer id);
    public ArrayList<HashMap> searchDeptMembers(String keyword);

    int insert(TbDept record);

    int insertSelective(TbDept record);

    TbDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbDept record);

    int updateByPrimaryKey(TbDept record);
    public List<TbDept> searchAllDept();
    public int insertDept(String deptName);
    public int deleteDeptById(int id);
    public int updateDeptById(TbDept entity);

}