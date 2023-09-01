package com.sydata.basis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础信息-文件信息对象 basis_file
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "基础信息-文件信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_file")
public class File extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id(文件名称-库区代码-文件类型)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("文件名称")
    private String wjmc;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("文件类型")
    private String wjlx;

    @ApiModelProperty("文件存储ID")
    private String fileStorageId;

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;
}