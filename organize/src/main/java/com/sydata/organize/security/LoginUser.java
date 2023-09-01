package com.sydata.organize.security;

import com.sydata.organize.domain.Dept;
import com.sydata.organize.domain.Menu;
import com.sydata.organize.domain.Role;
import com.sydata.organize.vo.MenuTreeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @describe 登录用户
 * @date 2022-06-29 17:55
 */
@ApiModel(description = "登录用户")
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class LoginUser implements Serializable {

    @ApiModelProperty(value = "登录id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "登录账号")
    private String account;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @ApiModelProperty(value = "组织父ID")
    private String organizeParentId;

    @ApiModelProperty(value = "组织父组织ID集合,分隔")
    private String organizeParentIds;

    @ApiModelProperty(value = "组织类型：行政组织、企业组织")
    private String organizeKind;

    @ApiModelProperty(value = "组织业务类型：粮食监管单位")
    private String organizeBusType;

    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @ApiModelProperty(value = "国ID")
    private String countryId;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;

    @ApiModelProperty(value = "行政区域类型")
    private String regionType;

    @ApiModelProperty(value = "菜单列表")
    private List<Menu> menus;

    @ApiModelProperty(value = "菜单树列表")
    private List<MenuTreeVo> menuTrees;

    @ApiModelProperty(value = "角色列表")
    private List<Role> roles;

    @ApiModelProperty(value = "部门")
    private Dept dept;

    @ApiModelProperty(value = "菜单权限标识")
    private List<String> menuPermissions;

    @ApiModelProperty(value = "角色权限标识")
    private List<String> rolePermissions;

    @ApiModelProperty(value = "应用秘钥")
    private String appSecret;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "登录设备")
    private String loginDevice;
}
