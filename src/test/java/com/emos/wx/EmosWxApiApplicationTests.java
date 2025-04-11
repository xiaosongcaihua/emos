package com.emos.wx;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.emos.wx.db.pojo.MessageEntity;
import com.emos.wx.db.pojo.MessageRefEntity;
import com.emos.wx.db.pojo.TbMeeting;
import com.emos.wx.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class EmosWxApiApplicationTests {
    @Autowired
    private MessageService messageService;

}
