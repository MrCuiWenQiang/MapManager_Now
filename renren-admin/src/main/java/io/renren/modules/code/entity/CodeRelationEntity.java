package io.renren.modules.code.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 注册码关系表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 13:26:30
 */
@Data
@TableName("tb_code_relation")
public class CodeRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * android标识
	 */
	private String androiduuid;
	/**
	 * 0正常  1停用
	 */
	private Integer status;
	/**
	 * 注册码
	 */
	private String code;
	/**
	 * 上次登录时间
	 */
	private Date logintimer;
	/**
	 * 创建时间
	 */
	private Date createtimer;

}
