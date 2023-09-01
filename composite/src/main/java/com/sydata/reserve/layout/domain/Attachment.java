package com.sydata.reserve.layout.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 储备布局地理信息-附件文件
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "储备布局地理信息-附件文件")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("reserve_layout_attachment")
public class Attachment extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（货位代码）廒间代码+2位顺序码")
    @TableId(value = "id", type = INPUT)
    private String id;

    @ApiModelProperty("数据id")
    private String dataId;

    @ApiModelProperty("文件存储id")
    private String fileStorageId;

    @ApiModelProperty("附件名称")
    private BigDecimal attachmentName;


    @ApiModelProperty("单位代码")
    private String dwdm;

}