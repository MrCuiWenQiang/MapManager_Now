package io.renren.modules.code.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.code.entity.CodeRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 注册码关系表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 09:58:05
 */
@Mapper
public interface CodeRelationDao extends BaseMapper<CodeRelationEntity> {
	
}
