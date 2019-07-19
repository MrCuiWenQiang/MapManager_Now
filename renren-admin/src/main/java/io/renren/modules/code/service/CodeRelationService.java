package io.renren.modules.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.code.entity.CodeRelationEntity;

import java.util.Map;

/**
 * 注册码关系表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 09:58:05
 */
public interface CodeRelationService extends IService<CodeRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int add(CodeRelationEntity codeRelationEntity);

    CodeRelationEntity queryForAndroiduuid(String androidUUID);

    CodeRelationEntity queryForCode(String code);
}

