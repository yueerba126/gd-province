package com.sydata.monitoring.events;

import com.sydata.monitoring.entity.GoodCompany;
import org.springframework.context.ApplicationEvent;

/**
 * 好粮油企业审核通过的事件
 *
 * @author zhangcy
 * @since 2023/4/26 17:46
 */
public class GoodCompanyAuditPassEvent extends ApplicationEvent {
    private GoodCompany goodCompany;

    public GoodCompanyAuditPassEvent(GoodCompany source) {
        super(source);
        this.goodCompany = source;
    }

    public GoodCompany getGoodCompany() {
        return goodCompany;
    }
}
