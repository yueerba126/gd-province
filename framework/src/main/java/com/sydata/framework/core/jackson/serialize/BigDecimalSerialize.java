package com.sydata.framework.core.jackson.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sydata.framework.core.jackson.format.BigDecimalFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;

/**
 * @author lzq
 * @description BigDecimal序列化
 * @date 2023-06-19 11:22
 **/
@AllArgsConstructor
@NoArgsConstructor
@JacksonStdImpl
public class BigDecimalSerialize extends JsonSerializer<BigDecimal> implements ContextualSerializer {

    private BigDecimalFormat bigDecimalFormat;

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        BigDecimalFormat bigDecimalFormat = property.getAnnotation(BigDecimalFormat.class);
        return new BigDecimalSerialize(bigDecimalFormat);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) {
        if (bigDecimalFormat != null && value != null) {
            BigDecimal bigDecimal = value.setScale(bigDecimalFormat.scale(), bigDecimalFormat.mode());
            String string = ToStringSerializer.instance.valueToString(bigDecimal);
            gen.writeString(string);
        }
    }
}