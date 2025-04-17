package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface TbRoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbRole record);

    int insertSelective(TbRole record);

    TbRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbRole record);

    int updateByPrimaryKey(TbRole record);
    public ArrayList<HashMap> searchRoleOwnPermission(int id);
    public int insertRole(TbRole role);
    public int updateRolePermissions(TbRole role);
    public ArrayList<HashMap> searchAllPermission();
    public List<TbRole> searchAllRole();
    public long searchRoleUsersCount(int id);
    public int deleteRoleById(int id);

}