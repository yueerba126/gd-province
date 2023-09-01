package com.sydata.report.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.core.global.annotation.ExcludeGlobalResponse;
import com.sydata.report.domain.CommandRecord;
import com.sydata.report.enums.ReportCodeEnum;
import com.sydata.report.param.CommandRecordPageParam;
import com.sydata.report.service.ICommandRecordService;
import com.sydata.report.vo.ReportResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 数据上报-国家平台指令接收记录API
 *
 * @author lzq
 * @describe 数据上报-国家平台指令接收记录API
 * @date 2022-07-25 10:10
 */
@Api(tags = "数据上报-国家平台指令接收记录API")
@Validated
@RestController
@RequestMapping("/report/command")
@Slf4j
public class CommandRecordController {

    private static ReportResultVo SUCCESS = new ReportResultVo(ReportCodeEnum.SUCCESS.getCode(), "指令解析成功");
    private static ReportResultVo FAIL = new ReportResultVo(ReportCodeEnum.FAIL.getCode(), "指令解析失败");

    @Resource
    private ICommandRecordService commandRecordService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<CommandRecord> page(@RequestBody CommandRecordPageParam pageParam) {
        return commandRecordService.pages(pageParam);
    }


    @ApiOperation("接收指令")
    @PostMapping("/receive")
    @ExcludeGlobalResponse
    public ReportResultVo receive(@RequestBody CommandRecord commandRecord) {
        try {
            commandRecordService.receive(commandRecord);
            return SUCCESS;
        } catch (Throwable e) {
            log.error("指令接收失败", e);
            return FAIL;
        }
    }
}
