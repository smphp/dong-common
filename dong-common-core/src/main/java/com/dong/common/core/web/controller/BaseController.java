package com.dong.common.core.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dong.common.core.utils.DateUtils;
import com.dong.common.core.utils.StringUtils;
import com.dong.common.core.web.page.PageDomain;
import com.dong.common.core.web.page.TableSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.Optional;

/**
 * web层通用数据处理
 * 
 * @author wdzk
 */
public class BaseController<T>
{
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * 分页设置
     */
    protected Page<T> startPage(){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNo = Optional.ofNullable(pageDomain.getPageNo()).orElse(0);
        Integer pageSize = Optional.ofNullable(pageDomain.getPageSize()).orElse(10);
        return new Page<>(pageNo , pageSize);
    }
    protected Page<T> startPage(int startNum,int endNum){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNo = Optional.ofNullable(pageDomain.getPageNo()).orElse(0);
        Integer pageSize = Optional.ofNullable(pageDomain.getPageSize()).orElse(10);
        return new Page<>(pageNo , pageSize);
    }

    /**
     * 带排序
     * @param orderByColumn
     * @param isDesc
     */
    protected Page<T> orderByPage(String orderByColumn,String isDesc) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNo = Optional.ofNullable(pageDomain.getPageNo()).orElse(0);
        Integer pageSize = Optional.ofNullable(pageDomain.getPageSize()).orElse(10);
        return new Page<>(pageNo , pageSize);
            //PageHelper.startPage(pageNum, pageSize);
            //PageHelper.orderBy(orderByColumn+" "+isDesc);
    }
}
