package com.sydata.filing.annotation;

import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.handle.value.bind.ValueBindStrategy;
import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**   
 * @Description:TODO(仓储备案-仓储库点-数据绑定)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@DataBindFieldConfig
public @interface DataBindWarehousingFilingStock {

    /**
    * 说明请看 {@link DataBindFieldConfig#sourceField}.
    */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String sourceField() default "#stockId";

    /**
    * 说明请看 {@link DataBindFieldConfig#queryColumn}.
    */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String queryColumn() default "id";

    /**
    * 说明请看 {@link DataBindFieldConfig#dataValue}.
    */
    @AliasFor(annotation = DataBindFieldConfig.class)
    String dataValue() default "#kdmc";

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
