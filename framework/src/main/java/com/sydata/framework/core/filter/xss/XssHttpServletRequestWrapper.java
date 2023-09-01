package com.sydata.framework.core.filter.xss;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.HTML_QUOTE;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.QUOTE;

/**
 * xss过滤处理
 *
 * @author system
 * @date 2023-03-10
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (!StrUtil.hasEmpty(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (!StrUtil.hasEmpty(value)) {
                    value = HtmlUtil.filter(value);
                }
                values[i] = value;
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameters = super.getParameterMap();
        Map<String, String[]> map = new LinkedHashMap<>();
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                String[] values = parameters.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    if (!StrUtil.hasEmpty(value)) {
                        value = HtmlUtil.filter(value);
                    }
                    values[i] = value;
                }
                map.put(key, values);
            }
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (!StrUtil.hasEmpty(value)) {
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        InputStream in = null;
        InputStreamReader reader = null;
        BufferedReader buffer = null;

        StringBuilder body = new StringBuilder();
        try {
            in = super.getInputStream();
            reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);
            String line = buffer.readLine();
            while (line != null) {
                body.append(line);
                line = buffer.readLine();
            }
        } finally {
            IoUtil.close(buffer);
            IoUtil.close(reader);
            IoUtil.close(in);
        }

        String json = HtmlUtil.filter(body.toString()).replaceAll(HTML_QUOTE, QUOTE);
        final ByteArrayInputStream bain = new ByteArrayInputStream(json.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() {
                return bain.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }
        };
    }
}