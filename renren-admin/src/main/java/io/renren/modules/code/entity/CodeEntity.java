package io.renren.modules.code.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 13:26:30
 */
@Data
@TableName("tb_code")
public class CodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 单位名称
	 */
	private String coltd;
	/**
	 * 联系人
	 */
	private String linman;
	/**
	 * 联系电话
	 */
	private String tel;
	/**
	 * 联系邮箱
	 */
	private String email;
	/**
	 * 注册码
	 */
	private String code;
	/**
	 * 状态状态0正常  1停用 2审核中 3审核不通过
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 上次修改时间
	 */
	private Date updatetimer;
	/**
	 * 创建时间
	 */
	private Date createtimer;
	/**
	 * 有效期
	 */
	private Date expTimer;

}
