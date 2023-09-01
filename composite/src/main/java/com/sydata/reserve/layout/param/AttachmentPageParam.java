package com.sydata.reserve.layout.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzq
 * @describe 储备布局地理信息-附件文件分页参数
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "储备布局地理信息-附件文件分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AttachmentPageParam extends PageQueryParam implements Serializable {


    @ApiModelProperty("数据id")
    private String dataId;

    @ApiModelProperty("文件存储id")
    private String fileStorageId;

    @ApiModelProperty("附件名称")
    private BigDecimal attachmentName;


    @ApiModelProperty("单位代码")
    private String dwdm;


}
