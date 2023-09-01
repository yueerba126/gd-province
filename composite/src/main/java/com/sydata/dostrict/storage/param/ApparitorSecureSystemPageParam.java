package com.sydata.dostrict.storage.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 安全仓储-安全生产-生产制度-分页参数
 * @version: 1.0
 */
@ApiModel(description = "安全仓储-安全生产-生产制度-分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorSecureSystemPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(name = "dwdm" , value = "发布单位")
    private String dwdm;

    @ApiModelProperty(name = "typeId" , value = "制度管理id")
    private String typeId;

    @ApiModelProperty(name = "fileName" , value = "文件标题")
    private String fileName;

    @ApiModelProperty(value = "开始发布时间")
    private LocalDateTime beginFbsj;

    @ApiModelProperty(value = "结束发布时间")
    private LocalDateTime endFbsj;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

}
