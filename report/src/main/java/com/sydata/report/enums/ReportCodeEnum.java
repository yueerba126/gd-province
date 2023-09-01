package com.sydata.report.enums;

/**
 * @author lzq
 * @describe 国家平台状态码枚举
 * @date 2022/10/31 14:33
 */
public enum ReportCodeEnum {

    /**
     * 请求成功
     */
    SUCCESS("200", "请求成功"),

    /**
     * 请求失败
     */
    FAIL("500", "请求失败"),

    /**
     * 粮库信息系统需要上传数据
     */
    REPORT("888", "粮库信息系统需要上传数据"),

    /**
     * 接口地址请求错误
     */
    NOT_ADDRESS("40101", "接口地址请求错误"),

    /**
     * 数据验签失败
     */
    SIGNATURE_FAIL("40102", "数据验签失败"),

    /**
     * 数据解密失败
     */
    DECRYPT_FAIL("40103", "数据解密失败"),

    /**
     * 数据解密失败
     */
    DATA_TYPE_CONVERT_ERR("40104", "数据解密失败"),

    /**
     * 数据条数超过最大限制
     */
    DATA_MAX_LIMIT("40105", "数据条数超过最大限制"),

    /**
     * 数据摘要比对失败
     */
    SIGNATURE_CONTRAST("40106", "数据摘要比对失败"),

    /**
     * 请求参数错误
     */
    PARAM_ERR("40201", "请求参数错误"),

    /**
     * 上传的数据字段与实际接口的字段个数不一致
     */
    DATA_FIELD_SIZE_NOT("40202", "上传的数据字段与实际接口的字段个数不一致"),

    /**
     * 请求数据缺少字段
     */
    PARAM_DATA_LACK("40203", "请求数据缺少字段"),

    /**
     * 数据校验错误
     */
    DATA_CHECK_ERR("40204", "数据校验错误"),

    /**
     * 指令下发失败
     */
    COMMAND_ISSUED_FAIL("40301", "指令下发失败"),

    /**
     * 暂无此接口
     */
    NO_SUCH_API("40401", "暂无此接口"),

    /**
     * 无权限
     */
    NO_PERMISSION("40402", "无权限"),

    /**
     * 信息不存在
     */
    INFO_NOT_EXIST("40403", "信息不存在"),

    /**
     * 其他未知错误
     */
    OTHER_UNKNOWN_ERRORS("40500", "其他未知错误"),

    /**
     * 无权限访问该接口
     */
    NO_API_PERMISSION("40501", "无权限访问该接口"),

    /**
     * 数据库信息不存在
     */
    DATABASE_NOT_EXIST("40502", "数据库信息不存在"),

    /**
     * 服务接口已下线
     */
    API_OFFLINE("40503", "服务接口已下线"),

    /**
     * 访问次数超过最大限制
     */
    ACCESS_COUNT_MAX_LIMIT("40504", "访问次数超过最大限制"),

    ;
    private final String code;
    private final String msg;

    ReportCodeEnum(String code, String msg) {
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
