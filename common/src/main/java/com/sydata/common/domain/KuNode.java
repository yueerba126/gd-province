package com.sydata.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.common.api.enums.YesNo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;

/**
 * 树形节点包装对象
 *
 * @author zhoucl
 * @date 2021/1/30 15:07
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KuNode implements Serializable {

    @ApiModelProperty("节点key,类似id的概念")
    private String key;

    @ApiModelProperty("父节点key")
    private String parentKey;

    @ApiModelProperty("查询key也是整个节点唯一标识")
    private String queryKey;

    @ApiModelProperty("节点类型")
    private String type;

    @ApiModelProperty("节点标题")
    private String title;

    @ApiModelProperty("所有子节点数量")
    private int cnt;

    @ApiModelProperty("是否禁用")
    private Boolean disabled;

    @ApiModelProperty("是否选中")
    private Boolean check;

    @ApiModelProperty("扩展存储数据字段1")
    private String extend1;

    @ApiModelProperty("子节点")
    private List<KuNode> children = new ArrayList<>();

    @ApiModelProperty("扩展MAP")
    private Map<String, Object> extra = new HashMap<>();

    public KuNode(String queryKey) {
        this.queryKey = queryKey;
    }

    public KuNode(String key, String parentKey, String queryKey, String title, String type, String isEnable) {
        this.key = key;
        this.parentKey = parentKey;
        this.queryKey = queryKey;
        this.type = type;
        this.title = title;
        this.disabled = Objects.equals(YesNo.NO.getCode(), isEnable);
    }
}
