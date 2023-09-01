package com.sydata.generate.core.framework.utils;

import com.sydata.generate.core.framework.domain.EnumInfo;
import com.sydata.generate.core.framework.domain.BasisInfo;
import com.sydata.generate.core.framework.domain.ResultJson;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author Asus
 * @Description
 * @Date 12:37 2023/5/10
 * @Param
 * @return
 **/
public class FreemarkerUtil {

    public static ResultJson createFile(BasisInfo dataModel, String templateName, String filePath, String ftlUrl) {
        ResultJson result = new ResultJson();
        FileWriter out = null;
        String fileName = dataModel.getEntityName() + messageStr(templateName);

        try {
            // 项目路径
            String projectPath = System.getProperty("user.dir");
            // 生成文件存放位置
            String templateFileUrl = ftlUrl;
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setDirectoryForTemplateLoading(new File(templateFileUrl));
            configuration.setDefaultEncoding("utf-8");
            Template template = configuration.getTemplate(templateName);
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            ResultJson var9;
            file.createNewFile();
            out = new FileWriter(file);
            template.process(dataModel, out);
            result.setCode(1);
            result.setMessage("创建" + fileName + "成功");
            var9 = result;
            return var9;
        } catch (Exception var20) {
            var20.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

        }

        result.setCode(-1);
        result.setMessage("创建" + fileName + "失败");
        return result;
    }

    public static ResultJson createCustomFile(EnumInfo enumInfo, String templateName, String filePath, String ftlUrl) {
        ResultJson result = new ResultJson();
        FileWriter out = null;
        String fileName = enumInfo.getEnumName() + messageStr(templateName);

        try {
            // 项目路径
            String projectPath = System.getProperty("user.dir");
            // 生成文件存放位置
            String templateFileUrl = ftlUrl;
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setDirectoryForTemplateLoading(new File(templateFileUrl));
            configuration.setDefaultEncoding("utf-8");
            Template template = configuration.getTemplate(templateName);
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            ResultJson var9;
            file.createNewFile();
            out = new FileWriter(file);
            template.process(enumInfo, out);
            result.setCode(1);
            result.setMessage("创建" + fileName + "成功");
            var9 = result;
            return var9;
        } catch (Exception var20) {
            var20.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

        }

        result.setCode(-1);
        result.setMessage("创建" + fileName + "失败");
        return result;
    }

    public static String messageStr(String name) {
        if (name.equals("entity.ftl")) {
            name = ".java";
        } else if (name.equals("pageParam.ftl")) {
            name = "PageParam.java";
        } else if (name.equals("selectParam.ftl")) {
            name = "SelectParam.java";
        } else if (name.equals("saveParam.ftl")) {
            name = "SaveParam.java";
        } else if (name.equals("updateParam.ftl")) {
            name = "UpdateParam.java";
        } else if (name.equals("vo.ftl")) {
            name = "Vo.java";
        } else if (name.equals("excelVo.ftl")) {
            name = "ExcelVo.java";
        } else if (name.equals("treeVo.ftl")) {
            name = "TreeVo.java";
        } else if (name.equals("fileVo.ftl")) {
            name = "FileVo.java";
        } else if (name.equals("dataBind.ftl")) {
            name = ".java";
        } else if (name.equals("dao.ftl")) {
            name = "Mapper.java";
        } else if (name.equals("mapper.ftl")) {
            name = "Mapper.xml";
        } else if (name.equals("service.ftl")) {
            name = "Service.java";
        } else if (name.equals("serviceImpl.ftl")) {
            name = "ServiceImpl.java";
        } else if (name.equals("busService.ftl")) {
            name = "Service.java";
        } else if (name.equals("busServiceImpl.ftl")) {
            name = "ServiceImpl.java";
        }else if (name.equals("controller.ftl")) {
            name = "Controller.java";
        }

        return name;
    }
}
