package com.sydata.framework.cache.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author lzq
 * @describe redisson Jackson2 Json 序列化
 * @date 2022-06-21 11:27
 */
@Component
public class GenericJackson2JsonCodec extends BaseCodec {

    @Resource
    private RedisSerializer jackson2JsonRedisSerializer;

    @Override
    public Decoder<Object> getValueDecoder() {
        return (buf, state) -> {
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            return jackson2JsonRedisSerializer.deserialize(bytes);
        };
    }

    @Override
    public Encoder getValueEncoder() {
        return in -> {
            ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
            try {
                ByteBufOutputStream os = new ByteBufOutputStream(out);
                os.write(jackson2JsonRedisSerializer.serialize(in));
                return os.buffer();
            } catch (IOException e) {
                out.release();
                throw e;
            } catch (Exception e) {
                out.release();
                throw new IOException(e);
            }
        };
    }
}
