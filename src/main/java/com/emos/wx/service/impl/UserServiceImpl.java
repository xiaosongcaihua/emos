package com.emos.wx.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.emos.wx.db.dao.TbUserDao;
import com.emos.wx.db.pojo.TbUser;
import com.emos.wx.exception.EmosException;
import com.emos.wx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Slf4j
@Scope("prototype")
@Service
public class UserServiceImpl implements UserService {
    @Value("${wx.app-id}")
    private String appId;
    @Value("${wx.app-secret}")
    private String appSecret;
    @Autowired
    private TbUserDao userDao;
    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        String openId = json.getStr("openid");
        if (openId == null || openId.length() == 0) {
            throw new RuntimeException("临时登陆凭证错误");
        }
        return openId;
    }

    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        //此时说明是超级管理员
        if (registerCode.equals("000000")){
            boolean bool = userDao.haveRootUser();
            if (!bool){
                //此时如果不存在超级管理员
                String openId = getOpenId(code);
                HashMap param = new HashMap();
                param.put("openId", openId);
                param.put("nickname", nickname);
                param.put("photo", photo);
                param.put("role", "[0]");
                param.put("status", 1);
                param.put("createTime", new Date());
                param.put("root", true);
                userDao.insertByMap(param);
                int id = userDao.searchIdByOpenId(openId);
                return id;
            } else {
                throw new EmosException("无法绑定超级管理员账号");
            }
        } else {
            //TODO 走普通员工注册逻辑
            return 0;
        }
    }

    @Override
    public Integer login(String code) {
        String openId=getOpenId(code);
        Integer id=userDao.searchIdByOpenId(openId);
        if(id==null){
            throw new EmosException("帐户不存在");
        }

        return id;
    }

    @Override
    public TbUser searchById(int userId) {
        return userDao.searchById(userId);
    }


    @Override
    public Set<String> searchUserPermissions(int userId) {
        return userDao.searchUserPermissions(userId);
    }
}