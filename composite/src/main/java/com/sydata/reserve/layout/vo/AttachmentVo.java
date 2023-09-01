package com.sydata.reserve.layout.vo;

import com.sydata.reserve.layout.domain.Attachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @describe 储备布局地理信息-附件文件VO
 * @date 2022-07-09 16:29
 */
@ApiModel(description = "储备布局地理信息-附件文件VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AttachmentVo extends Attachment implements Serializable {


    @ApiModelProperty(value = "企业名称")
    private String dwdmNmae;
}
