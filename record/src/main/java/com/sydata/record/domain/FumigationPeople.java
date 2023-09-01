package com.sydata.record.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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

/**
 * 备案管理-熏蒸人员对象 record_fumigation_people
 *
 * @author system
 * @date 2022-12-10
 */
@ApiModel(description = "备案管理-熏蒸人员")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("record_fumigation_people")
public class FumigationPeople implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "熏蒸备案ID")
    private String fumigationId;

    @ApiModelProperty(value = "姓名")
    private String xm;

    @ApiModelProperty(value = "职务")
    private String zw;

    @ApiModelProperty(value = "职业资格：初级、中级、高级")
    private String zyzg;

    @ApiModelProperty(value = "身体状况")
    private String stzk;

    @ApiModelProperty(value = "熏蒸任务分工")
    private String xzrwfg;

    @ApiModelProperty(value = "是否外包：是或否")
    private String sfwb;
}