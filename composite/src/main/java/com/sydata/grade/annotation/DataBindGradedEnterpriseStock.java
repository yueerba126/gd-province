/**
 * @filename:DataBindGradedEnterpriseStock 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.annotation;
import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.handle.value.bind.ValueBindStrategy;
import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Description:TODO(等级粮库评定管理-企业等级库点-数据绑定)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@DataBindFieldConfig
public @interface DataBindGradedEnterpriseStock {
    /**
    * 说明请看 {@link DataBindFieldConfig#sourceField}.
    */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String sourceField() default "#gradedEnterpriseStockId";

    /**
    * 说明请看 {@link DataBindFieldConfig#queryColumn}.
    */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String queryColumn() default "id";

    /**
    * 说明请看 {@link DataBindFieldConfig#dataValue}.
    */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String dataValue() default "#name";

    /**
    * 说明请看 {@link DataBindFieldConfig#valueBindStrategy}.
    */
    @AliasFor(annotation = DataBindFieldConfig.class)
    ValueBindStrategy valueBindStrategy() default ValueBindStrategy.DEFAULT;

    /**
    * 绑定分隔符(默认逗号)
    * 说明请看 {@link DataBindFieldConfig#valueBindStrategy}.
    */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String bindSeparated() default COMMA;
}
