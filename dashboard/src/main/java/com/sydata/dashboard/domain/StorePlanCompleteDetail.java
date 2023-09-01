package com.sydata.dashboard.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.math.BigDecimal;

/**
 * @author zhangcy
 * @since 2023/5/6 19:14
 */
@ApiModel("储备计划完成情况")
@Data
public class StorePlanCompleteDetail {
    @ApiModelProperty("地市ID")
    private String cityId;

    @ApiModelProperty("企业ID")
    private String enterpriseId;

    @ApiModelProperty("库点ID")
    private String stockHouseId;

    @ApiModelProperty("粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty("粮食品种代码")
    private String lspzdm;

    @ApiModelProperty("库存数量")
    private BigDecimal stockQty;

    @ApiModelProperty("计划数量")
    private BigDecimal planQty;

    @ApiModelProperty("计划完成率")
    private BigDecimal completeRatio;

    public PositionKey positionKey() {
        return new PositionKey(cityId, enterpriseId, stockHouseId);
    }

    /**
     * 用于定位地市下、某个企业的库点
     *
     * @return 地市ID + 企业ID + 库点ID
     */
    @Data
    @ApiModel("用于定位的key")
    public static class PositionKey{
        @ApiModelProperty("地市ID")
        private String cityId;

        @ApiModelProperty("企业ID")
        private String enterpriseId;

        @ApiModelProperty("库点ID")
        private String stockHouseId;

        public PositionKey(String cityId, String enterpriseId, String stockHouseId) {
            this.cityId = cityId;
            this.enterpriseId = enterpriseId;
            this.stockHouseId = stockHouseId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            PositionKey that = (PositionKey) o;

            return new EqualsBuilder().append(cityId, that.cityId).append(enterpriseId, that.enterpriseId).append(stockHouseId, that.stockHouseId).isEquals();
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

}
