package com.sydata.dashboard.constants;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 仓房类型常量
 *
 * @author zhangcy
 * @since 2023/5/5 16:02
 */
public class WarehouseTypeConstants {
    /**
     * 主要展示的仓房类型
     */
    public static final Set<String> MAIN_WAREHOUSE_TYPE_SET = Sets.newHashSet("平房仓", "筒仓", "浅圆仓", "楼房仓", "地下仓", "露天垛", "席茓囤");

    /**
     * 10101	平房仓
     */
    public static final String FLAT_WAREHOUSE_CODE = "10101";
    public static final String FLAT_WAREHOUSE_NAME = "平房仓";

    /**
     * 10102	筒仓
     */
    public static final String SILO_CODE = "10102";
    public static final String SILO_NAME = "筒仓";

    /**
     * 10103	浅圆仓
     */
    public static final String SHALLOW_ROUND_POSITIONS_CODE = "10103";
    public static final String SHALLOW_ROUND_POSITIONS_NAME = "浅圆仓";

    /**
     * 10104	楼房仓
     */
    public static final String BUILDING_WAREHOUSE_CODE = "10104";
    public static final String BUILDING_WAREHOUSE_NAME = "楼房仓";

    /**
     * 10105	地下仓
     */
    public static final String UNDERGROUND_SILOS_CODE = "10105";
    public static final String UNDERGROUND_SILOS_NAME = "地下仓";

    /**
     * 10106	气膜仓
     */
    public static final String AIR_FILM_CHAMBER_CODE = "10106";
    public static final String AIR_FILM_CHAMBER_NAME = "气膜仓";

    /**
     * 10107	球仓
     */
    public static final String BALL_WAREHOUSE_CODE = "10107";
    public static final String BALL_WAREHOUSE_NAME = "球仓";

    /**
     * 10108	土堤仓
     */
    public static final String EARTHENCING_BARN_CODe = "10108";
    public static final String EARTHENCING_BARN_NAME = "土堤仓";

    /**
     * 10109	露天垛
     */
    public static final String OPEN_AIR_STACKS_CODE = "10109";
    public static final String OPEN_AIR_STACKS_NAME = "露天垛";

    /**
     * 10110	席茓囤
     */
    public static final String XI_MINGHO_CODE = "10110";
    public static final String XI_MINGHO_NAME = "席茓囤";

    /**
     * 10111	简易囤
     */
    public static final String EASY_HOARDING_CODE = "10111";
    public static final String EASY_HOARDING_NAME = "简易囤";

    /**
     * 10199	其他粮仓
     */
    public static final String OTHER_CODE = "10199";
    public static final String OTHER_NAME = "其他粮仓";

    /**
     * 主要的仓房类型
     */
    public static final Set<String> MAIN_WAREHOUSE_CODE = Sets.newHashSet(FLAT_WAREHOUSE_CODE,
            SILO_CODE,
            SHALLOW_ROUND_POSITIONS_CODE,
            BUILDING_WAREHOUSE_CODE,
            UNDERGROUND_SILOS_CODE,
            OPEN_AIR_STACKS_CODE,
            XI_MINGHO_CODE,
            OTHER_CODE);

    private WarehouseTypeConstants(){}
}
