package com.sydata.generate.core.framework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @program: multiple
 * @description: 自定义枚举的信息
 * @author: lzq
 * @create: 2023-05-12 16:11
 */
@Data
@ToString
@NoArgsConstructor
public class EnumInfo implements Serializable {
    private String project;
    private String module;
    private String author;
    private String version;
    private String createDate;
    /**
     * @Description 枚举类名
     **/
    private String enumName;
    /**
     * @Description 枚举类名
     **/
    private String enumUrl;
    /**
     * @Description 枚举描述
     **/
    private String enumComment;
    /**
     * @Description 枚举明细
     **/
    private List<EnumItemInfo> enumItemInfos;

    public EnumInfo(String project, String module, String author, String version, String enumName, String enumUrl, String enumComment, List<EnumItemInfo> enumItemInfos) {
        this.project = project;
        this.module = module;
        this.author = author;
        this.version = version;
        this.enumName = enumName;
        this.enumUrl = enumUrl;
        this.enumComment = enumComment;
        this.enumItemInfos = enumItemInfos;
    }
}
