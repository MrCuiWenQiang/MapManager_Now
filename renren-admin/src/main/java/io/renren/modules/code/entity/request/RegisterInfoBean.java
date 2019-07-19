package io.renren.modules.code.entity.request;


import lombok.Data;

import java.util.Date;
//APP注册信息
@Data
public class RegisterInfoBean {
    private int type;//注册类型 1注册码注册  2验证信息注册
    private String androidUUID;//android的唯一标识

    private String code;//注册码

    private String coltd;//公司名称
    private String linman;//联系人名称
    private String tel;//联系电话
    private String email;//联系邮箱
    private String remarks;//备注信息


}
