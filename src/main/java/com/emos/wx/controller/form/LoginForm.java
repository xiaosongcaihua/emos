package com.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class LoginForm {

    @NotBlank(message = "临时授权不能为空")
    private String code;
}
