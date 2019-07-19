package io.renren.modules.code.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.code.entity.CodeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 09:58:05
 */
@Mapper
public interface CodeDao extends BaseMapper<CodeEntity> {
	
}
