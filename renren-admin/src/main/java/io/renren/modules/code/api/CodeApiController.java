package io.renren.modules.code.api;

import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.code.entity.CodeEntity;
import io.renren.modules.code.entity.CodeRelationEntity;
import io.renren.modules.code.entity.request.CheckBean;
import io.renren.modules.code.entity.request.RegisterInfoBean;
import io.renren.modules.code.entity.result.CheckResultBean;
import io.renren.modules.code.service.CodeRelationService;
import io.renren.modules.code.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/code")
public class CodeApiController {
    @Autowired
    private CodeService codeService;

    @Autowired
    private CodeRelationService relationService;


    /**
     * 审核
     *
     * @param data
     * @return
     */
    // TODO: 2019/7/16 重复注册问题需注意计入
    @PostMapping(value = "/apply")
    public R applyForCode(@RequestBody RegisterInfoBean data) {
        return codeService.add(data);
    }


    /**
     * 校验
     *
     * @param data
     * @return
     */
    @PostMapping(value = "/check")
    public R checkForUUID(@RequestBody CheckBean data) {
        CodeRelationEntity dbData = relationService.queryForAndroiduuid(data.getAndroidUUID());
        CheckResultBean bean = null;
        R r = R.ok_api();
        if (dbData != null && dbData.getStatus() == 0) {
            String uuid = dbData.getCode();
            CodeEntity codeEntity = codeService.selectByCode(uuid);
            if (codeEntity != null && codeEntity.getStatus() == 0) {
                Date nowDate = new Date();
                int value = nowDate.compareTo(codeEntity.getExpTimer());
                if (value == 1) {
                    bean = new CheckResultBean(5, "注册码已到期");
                }else{
                    bean = CheckResultBean.success();
                }
            } else if (codeEntity != null && codeEntity.getStatus() == 1) {
                bean = new CheckResultBean(1, "账户已被停用");
            } else if (codeEntity != null && codeEntity.getStatus() == 2) {
                bean = new CheckResultBean(2, "账户在审核中");
            } else if (codeEntity != null && codeEntity.getStatus() == 3) {
                bean = new CheckResultBean(3, "审核不通过");
            } else {
                bean = new CheckResultBean(4, "未提交信息");
            }
        } else if (dbData != null && dbData.getStatus() == 1) {
            bean = new CheckResultBean(1, "账户已被停用");
        } else {
            bean = new CheckResultBean(4, "未提交信息");
        }

        if (bean == null) {
            bean = CheckResultBean.fail();
        }
        r.put("data", bean);
        return r;
    }
}
