package io.renren.modules.code.entity.result;

import lombok.Data;

@Data
public class CheckResultBean {
    private int type;//0正常  1停用 2审核中 3审核不通过 4未提交信息 5注册码到期
    private String msg;

    public CheckResultBean() {
    }

    public CheckResultBean(int type) {
        this.type = type;
    }

    public CheckResultBean(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static CheckResultBean success(){
        return new CheckResultBean(0);
    }

    public static CheckResultBean fail(){
        return new CheckResultBean(1);
    }

    public static CheckResultBean fail(String msg){
        return new CheckResultBean(1,msg);
    }
}
