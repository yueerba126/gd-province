package com.sydata.collect.api.enums;

import one.util.streamex.StreamEx;

import java.util.Map;

import static java.util.function.Function.identity;

/**
 * @author lzq
 * @description 数据API枚举
 * @date 2022/10/19 10:10
 */
public enum DataApiEnum {

    /**
     * 熏蒸备案
     */
    FUMIGATION("1001", "熏蒸备案"),

    /**
     * 等级粮库评定信息
     */
    GRADE_REVIEW("1002", "等级粮库评定信息"),

    /**
     * 储备轮换计划审核
     */
    PLAN_MANAGE("1003", "储备轮换计划审核"),

    /**
     * 仓储备案审核
     */
    WAREHOUSING_FILING("1004", "仓储备案审核"),

    /**
     * 单位信息
     */
    COMPANY("1101", "单位信息"),

    /**
     * 库点信息
     */
    STOCK_HOUSE("1102", "库区信息"),

    /**
     * 仓库信息
     */
    WAREHOUSE("1103", "仓房信息"),

    /**
     * 廒间信息
     */
    GRANARY("1104", "廒间信息"),

    /**
     * 货位信息
     */
    CARGO("1105", "货位信息"),

    /**
     * 油罐信息
     */
    TANK("1106", "油罐信息"),

    /**
     * 设备信息
     */
    DEVICE("1107", "设备信息"),

    /**
     * 药剂信息
     */
    MEDICINE("1108", "药剂信息"),

    /**
     * 文件信息
     */
    FILE("1109", "文件信息"),

    /**
     * 库区图仓房点位标注数据
     */
    CARGO_LABEL("1110", "库区图仓房点位标注数据"),

    /**
     * 库区图视频监控设备点位标注数据
     */
    WEBCAM_LABEL("1111", "库区图视频监控设备点位标注数据"),

    /**
     * 人员信息
     */
    COMPANY_STAFF("1112", "人员信息"),

    /**
     * 企业信用信息
     */
    COMPANY_CREDIT("1113", "企业信用信息"),

    /**
     * 财务报表信息
     */
    FINANCE("1114", "财务报表信息"),

    /**
     * 合同信息
     */
    CONTRACT("1201", "合同信息"),

    /**
     * 入库信息
     */
    IN_STOCK("1202", "入库信息"),

    /**
     * 入库检验信息
     */
    IN_STOCK_CHECK("1203", "入库检验信息"),

    /**
     * 入库结算信息
     */
    IN_STOCK_SETTLEMENT("1204", "入库结算信息"),

    /**
     * 出库信息
     */
    OUT_STOCK("1205", "出库信息"),

    /**
     * 出库结算信息
     */
    OUT_STOCK_SETTLEMENT("1206", "出库结算信息"),

    /**
     * 倒仓信息
     */
    TRANSFER_BARN("1207", "倒仓信息"),

    /**
     * 库存信息
     */
    STOCK_GRAIN("1208", "库存信息"),

    /**
     * 损溢单
     */
    LOSS("1209", "损溢单"),

    /**
     * 粮食性质转变单
     */
    TRANSFER_NATURE("1210", "粮食性质转变单"),

    /**
     * 账面库存信息
     */
    MONTH_STOCK("1211", "账面库存信息"),

    /**
     * 客户信息
     */
    CUSTOMER("1212", "客户信息"),

    /**
     * 安全管理
     */
    SAFETY_CHECK("1301", "安全管理"),

    /**
     * 温湿度检测
     */
    GRAIN_MONITOR("1302", "温湿度检测"),

    /**
     * 害虫检测
     */
    PEST_INFORMATION("1303", "害虫检测"),

    /**
     * 气体检测
     */
    GAS_INFORMATION("1304", "气体检测"),

    /**
     * 通风作业
     */
    VENTILATION("1305", "通风作业"),

    /**
     * 熏蒸作业
     */
    STEAM_TASK_INFORMATION("1306", "熏蒸作业"),

    /**
     * 仓内视频图像
     */
    GRANARY_VIDEO_IMAGE("1307", "仓内视频图像"),

    /**
     * 库区视频监控异常事件告警
     */
    EXCEPTION_MONITORING_EVENT("1308", "库区视频监控异常事件告警"),

    /**
     * 违规预警信息
     */
    VIOLATION_WARNING("1309", "违规预警信息"),

    /**
     * 质检数据
     */
    QUALITY_INSPECTION("1310", "质检数据"),

    /**
     * 储备规模
     */
    RESERVE_SCALE("1401", "储备规模"),

    /**
     * 储备计划信息
     */
    RESERVE_PLAN("1402", "储备计划信息"),

    /**
     * 轮换计划信息
     */
    ROTATION_PLAN("1403", "轮换计划信息"),

    /**
     * 轮换计划明细信息
     */
    ROTATION_PLAN_DTL("1404", "轮换计划明细信息"),

    /**
     * 项目信息
     */
    PROJECT("1405", "项目信息"),


    ;

    private static Map<String, DataApiEnum> API_MAP = StreamEx.of(values()).toMap(DataApiEnum::getApiCode, identity());

    private String apiCode;

    private String msg;

    DataApiEnum(String apiCode, String msg) {
        this.apiCode = apiCode;
        this.msg = msg;
    }

    public String getApiCode() {
        return apiCode;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 根据API编码获取枚举
     *
     * @param apiCode API编码
     * @return 数据API枚举
     */
    public static DataApiEnum getByApiCode(String apiCode) {
        return API_MAP.get(apiCode);
    }
}
