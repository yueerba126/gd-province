package com.sydata.collect.api.validated.valid;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DOT;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.HASH;

/**
 * @author lzq
 * @description 自定义校验基类
 * @date 2022/10/20 16:43
 */
public abstract class BaseValid<A extends Annotation, T> implements ConstraintValidator<A, T> {

    private static final SpelExpressionParser EL_EXPRESSION_PARSER = new SpelExpressionParser();


    private static final ThreadLocal<EvaluationContext> PARAM_THREAD_LOCAL = new ThreadLocal<>();

    private static final String PARAM = "param";

    /**
     * 设置源参数
     *
     * @param param 源参数
     */
    public static void set(Object param) {
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariable(PARAM, param);
        PARAM_THREAD_LOCAL.set(evaluationContext);
    }

    /**
     * 清理源参数
     */
    public static void remove() {
        PARAM_THREAD_LOCAL.remove();
    }


    /**
     * 根据springEl表达式获取源参数的字段值
     *
     * @param expression 表达式
     */
    protected Object getValByParam(String expression) {
        EvaluationContext evaluationContext = PARAM_THREAD_LOCAL.get();
        Assert.notNull(evaluationContext, "未设置源参数");

        String springEl = HASH + PARAM + DOT + expression.substring(1);
        return EL_EXPRESSION_PARSER.parseExpression(springEl).getValue(evaluationContext);
    }
}
