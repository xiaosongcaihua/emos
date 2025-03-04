package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbUser;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mapper
public interface TbUserDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);

    boolean haveRootUser();

    int insertByMap(HashMap user);
    Integer searchIdByOpenId(String openId);
    Set<String> searchUserPermissions(int userId);
}