package com.dong.common.core.annotation;

import java.lang.annotation.*;

import com.dong.common.core.enums.BusinessType;
import com.dong.common.core.enums.BusinessType;
import com.dong.common.core.enums.OperatorType;
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}