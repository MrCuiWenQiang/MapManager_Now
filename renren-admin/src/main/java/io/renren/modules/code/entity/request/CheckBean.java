package io.renren.modules.code.entity.request;


import lombok.Data;

//APP校验信息
@Data
public class CheckBean {
    private String androidUUID;//android的唯一标识

    private String code;//注册码

}
