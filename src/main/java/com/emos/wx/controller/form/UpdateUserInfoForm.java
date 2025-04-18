package com.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel
public class UpdateUserInfoForm {
    @NotNull
    @Min(1)
    private Integer userId;
    @NotBlank
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,15}$")
    private String name;
    @NotBlank
    @Pattern(regexp = "^男$|^女$")
    private String sex;
    @NotBlank
    private String deptName;
    @NotBlank
    @Pattern(regexp = "^1[0-9]{10}$")
    private String tel;
    @NotBlank
    @Pattern(regexp = "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$")
    private String email;
    @NotBlank
    private String hiredate;
    @NotBlank
    private String role;
    @Range(min = 1, max = 2)
    private int status;
}