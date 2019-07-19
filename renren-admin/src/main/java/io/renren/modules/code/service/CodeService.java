package io.renren.modules.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.code.entity.ApplyEntity;
import io.renren.modules.code.entity.CodeEntity;
import io.renren.modules.code.entity.request.RegisterInfoBean;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 09:58:05
 */
public interface CodeService extends IService<CodeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R add(RegisterInfoBean data);

    CodeEntity selectByCode(String code);

    R apply(ApplyEntity applyEntity);
}

