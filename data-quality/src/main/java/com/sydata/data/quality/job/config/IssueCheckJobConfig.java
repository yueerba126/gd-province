package com.sydata.data.quality.job.config;

import com.alibaba.fastjson.JSONObject;
import com.xxl.job.core.context.XxlJobHelper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @description 数据质量问题检查定时任务配置
 * @date 2023/4/24 9:04
 */
@ApiModel(description = "数据质量问题检查定时任务配置")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class IssueCheckJobConfig implements Serializable {

    @ApiModelProperty(value = "接口编码列表")
    private List<String> apiCodes;

    @ApiModelProperty(value = "分区大小《单次处理多少数据》")
    private int partitionSize;

    @ApiModelProperty(value = "异步线程数")
    private int threads;


    /**
     * 获取定时任务配置
     *
     * @return 数据质量问题检查定时任务配置
     */
    public static IssueCheckJobConfig jobConfig() {
        String json = XxlJobHelper.getJobParam();
        return JSONObject.parseObject(json, IssueCheckJobConfig.class);
    }
}
