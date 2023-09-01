package com.sydata.framework.core.global;

/**
 * @author lzq
 * @describe 响应码
 * @date 2022-07-05 09:09
 */
public enum CodeEnum {

    /**
     * 安全验签失败
     */
    NOT_SECURITY_SIGN(400, "安全签名失败"),

    /**
     * 未登录
     */
    NOT_LOGIN(401, "未登录"),

    /**
     * token无效
     */
    TOKEN_INVALID(402, "token无效"),

    /**
     * 无权限
     */
    NOT_PERMISSION(403, "无权限"),

    /**
     * API资源禁用
     */
    API_DISABLED(405, "API资源禁用"),

    /**
     * 无角色
     */
    NOT_ROLE(406, "无角色"),

    /**
     * 其他token异常
     */
    OTHER_TOKEN(407, "其他token异常"),

    /**
     * 数据库唯一键重复
     */
    DB_UN_KEY_REPEAT(408, "数据库唯一键重复"),

    /**
     * 异步请求处理超时
     */
    ASYNC_REQUEST_TIMEOUT(409, "异步请求处理超时"),

    /**
     * 参数校验异常
     */
    PARAM_VALID_EXCEPTION(410, "参数校验异常"),

    /**
     * 业务不合法异常
     */
    ILLEGAL_STATE_EXCEPTION(411, "业务不合法异常"),

    /**
     * 此账号已被封禁
     */
    DISABLE_LOGIN(408, "此账号已被封禁,剩余封禁时间还有%s秒"),

    /**
     * 未知异常
     */
    THROWABLE(500, "未知异常"),

    ;

    private int code;
    private String msg;


    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
