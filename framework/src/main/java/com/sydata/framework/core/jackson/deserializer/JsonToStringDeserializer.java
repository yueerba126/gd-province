package com.sydata.framework.core.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * @author lzq
 * @description json转string反序列化
 * @date 2021-03-11 14:51
 **/
public class JsonToStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser == null) {
            return null;
        }
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node == null) {
            return null;
        }
        return node.toString();
    }
}
