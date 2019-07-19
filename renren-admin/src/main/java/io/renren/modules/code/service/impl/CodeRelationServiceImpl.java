package io.renren.modules.code.service.impl;

import io.renren.modules.code.dao.CodeRelationDao;
import io.renren.modules.code.entity.CodeRelationEntity;
import io.renren.modules.code.service.CodeRelationService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;



@Service("codeRelationService")
public class CodeRelationServiceImpl extends ServiceImpl<CodeRelationDao, CodeRelationEntity> implements CodeRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CodeRelationEntity> page = this.page(
                new Query<CodeRelationEntity>().getPage(params),
                new QueryWrapper<CodeRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int add(CodeRelationEntity codeRelationEntity) {
        return baseMapper.insert(codeRelationEntity);
    }

    @Override
    public CodeRelationEntity queryForAndroiduuid(String androidUUID) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("androiduuid",androidUUID);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public CodeRelationEntity queryForCode(String code) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("code",code);
        return baseMapper.selectOne(wrapper);
    }
/**
 * ### Cause: java.sql.SQLSyntaxErrorException: Unknown column '65a21dba7cb2487ba584aa8c79f4a490' in 'field list'
 * ; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column '65a21dba7cb2487ba584aa8c79f4a490' in 'field list'
 */
}
