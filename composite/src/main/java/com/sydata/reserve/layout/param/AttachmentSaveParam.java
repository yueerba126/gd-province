package com.sydata.reserve.layout.param;

import com.sydata.reserve.layout.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 储备布局地理信息-附件文件备案
 * </p>
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "储备布局地理信息-附件文件新增参数")
public class AttachmentSaveParam extends BaseEntity implements Serializable {


    @ApiModelProperty("数据id")
    private String dataId;

    @ApiModelProperty("文件存储id")
    private String fileStorageId;

    @ApiModelProperty("附件名称")
    private BigDecimal attachmentName;


    @ApiModelProperty("单位代码")
    private String dwdm;


}
