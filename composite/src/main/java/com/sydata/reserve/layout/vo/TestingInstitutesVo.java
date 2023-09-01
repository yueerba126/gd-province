package com.sydata.reserve.layout.vo;

import com.sydata.reserve.layout.domain.TestingInstitutes;
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
 * @describe 储备布局地理信息-质检机构VO
 * @date 2022-07-09 16:29
 */
@ApiModel(description = "储备布局地理信息-质检机构VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TestingInstitutesVo extends TestingInstitutes implements Serializable {


    private String dwmc;
}
