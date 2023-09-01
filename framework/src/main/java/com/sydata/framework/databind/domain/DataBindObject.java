package com.sydata.framework.databind.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * 数据绑定存储类型
 *
 * @author zhoucl
 * @date 2021/4/22 20:43
 */
@Getter
@Setter
@Accessors(chain = true)
public class DataBindObject {

    /**
     * 需要转换的数据对象
     */
    private Collection<DataBindSourceData> datas = new ArrayList<>();

    /**
     * 转换数据方案
     */
    private List<DataBindItem> items = new ArrayList<>();

    public void addData(Object item) {
        datas.add(new DataBindSourceData(item, UUID.randomUUID().toString()));
    }

    public void addDataBindItem(DataBindItem dataBindItem) {
        items.add(dataBindItem);
    }

    public void addDataBindItems(List<DataBindItem> dataBindItems) {
        items.addAll(dataBindItems);
    }
}
