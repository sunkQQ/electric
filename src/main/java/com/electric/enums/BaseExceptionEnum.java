package com.electric.enums;

/**
 * 物流后台异常枚举
 * 
 * @author: luochao
 * @since: 2016年7月18日 上午9:52:12
 * @history:
 */
public enum BaseExceptionEnum {

	/** 远程接口失败 */
	REMOTE_ERROR_NOPREFIX("20000", "调用远程接口失败:%s"),

	/** 远程接口失败 */
	REMOTE_WARN_NOPREFIX("20001", "%s"),

	/** 系统异常 */
	SYSTEM_ERROR("10000", "系统开小差,请稍后再试"),

	// 公共业务异常
	NO_PERMISSION("100000000", "您没有权限操作"),

	/** 未登录 */
	NO_LOGIN("10010", "请重新登录"),

	/** 密钥已过期 */
	KEY_EXPIRED("10020", "密钥已过期"),

	/** 登录设备更换了 */
	DEVICE_CHANGED("10030", "设备已更换"),

	/** 鉴权失败 authentication */
	NO_AUTHENTICATION("00010", "鉴权失败,非法操作"),

	/** 页面访问异常 */
	MODEL_AND_VIEW_ERROR("100000001", "页面访问异常"),

	/** 未查询到数据 */
	NOT_QUERY_TO_THE_DATA("200000001", "未查询到数据"),

	/** 创建失败 */
	CREATE_FAILURE("200000002", "创建失败:%s"),

	/** 创建失败 */
	CREATE_FAILURE_NOPREFIX("200000004", "%s"),

	/** 创建黑链接失败 */
	CREATE_BLACKLINE_NOPREFIX("200000005", "创建黑链接失败"),

	/** 创建禁用字/敏感词失败 */
	CREATE_KEYWORD_NOPREFIX("200000006", "创建禁用字/敏感词失败"),

	/** 禁用字/敏感字创建解析type为空 */
	CREATE_KEYWORDEXCEL_NOPREFIX("200000007", "禁用字/敏感字创建解析type为空"),

	/** 修改失败 */
	UPDATE_FAILURE("200000003", "修改失败"),

	/** 修改失败 */
	UPDATE_FAILURE_NOPREFIX("200000005", "%s"),

	/** 修改黑链接失败 */
	UPDATE_BLACKLINE_NOPREFIX("200000006", "修改黑链接失败"),

	/** 修改禁用词/敏感词失败 */
	UPDATE_KEYWORD_NOPREFIX("200000007", "修改禁用词/敏感词失败"),

	/** 修改失败 */
	UPDATE_COMMA_NOPREFIX("200000008", "包含特殊字符不可以保存"),

	/** 参数异常 */
	PARAM_ERROR_NOPREFIX("200000006", "%s"),

	/** 禁用词/敏感词为空 */
	PARAM_KEYWORD_NOPREFIX("200000007", "禁用词/敏感词为空"),

	/** 参数校验异常 */
	VALIDATION_ERROR("200000010", "您可能输入错误信息，请修改并重试"),

	/** 参数异常 */
	PARAM_ERROR("100000003", "参数异常"),

	/** 原密码输入不正确 */
	OLD_PASSWORD_ERROR("100000017", "原密码输入不正确"),

	/** 帐户存在 */
	ACCOUNT_ALREADY("100000018", "当前帐户已存在"),

	/** 当前账户不存在 */
	ACCOUNT_NOT_EXIST("100000118", "当前账户不存在"),

	/** 角色不允许禁用 */
	ROLE_DISABLE("100000019", "当前角色正在被使用，无法禁用"),

	/** 帐户存在 */
	ROLE_ALREADY("100000020", "当前角色已存在"),

	/** 分类ID为空 */
	unifiedcenter_TYPE_ID_NULL("100000021", "您未选择任何分类"),

	/** 未查询到分类数据 */
	unifiedcenter_TYPE_NULL("100000022", "分类数据异常"),

	/** 分类名称已存在 */
	unifiedcenter_TYPE__EXISTING("100000023", "分类名称已存在"),

	/** 分类编号不能为空 */
	unifiedcenter_TYPE_NO_NULL("100000024", "分类编号不能为空"),

	/** 不能删除有资源的分类 */
	unifiedcenter_TYPE_HAVE_RESOURCE("100000025", "不能删除有资源的分类"),

	/** 不能下架有已上架资源的分类 */
	unifiedcenter_TYPE_HAVE_RESOURCE_ON("100000026", "不能下架有已上架资源的分类"),

	/** 分类不能重复 */
	unifiedcenter_TYPE_REPEATED("100000027", "分类不能重复!"),

	/** 资源编号不能为空 */
	unifiedcenter_RESOURCE_NO_NULL("100000027", "资源编号不能为空!"),

	/** 网站编号不能为空 */
	unifiedcenter_NO_NULL("100000028", "必须选择网站!"),

	/** 不能删除超级管理员 */
	NOT_DELETE_SUPERADMIN("100000029", "不能删除superadmin"),
	/** 不能删除超级管理员 */
	unifiedcenter_LOGIN_UNAUTHORIZED("100000030", "您的权限受限，无法访问！"),

	/** 当前状态不允许操作 */
	OPERATE_UNSUPPORTED("100000004", "状态已变更，请刷新页面后重试"),

	/** 禁用词/敏感词已存在 */
	KEYWORD_ALREADY("100000030", "禁用词/敏感词已存在"),

	/** 黑链接已存在 */
	BLACKLINE_ALREADY("100000031", "黑链接已存在"),

	/** 帐户不存在 */
	ACCOUNT_NOTEXIST("100000032", "当前帐户不存在"),

	/** 优惠券不存在 */
	COUPON_NOTEXIST("100000033", "优惠券不存在"),

	/** 优惠券已被使用或已过期 */
	COUPON_ALREADY_USE("100000034", "优惠券已被使用或已过期"),

	/** 该学校已设置 */
	SCHOOL_ALREADY_SET("100000035", "该学校已设置");

	/** 编码 */
	private String code;

	/** 消息内容 */
	private String msg;

	BaseExceptionEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
