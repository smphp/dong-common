package com.dong.common.core.web.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 表格分页数据对象
 * 
 * @author suihan
 */
public class TableDataInfo<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long totalPage;

    private Long pageNo;

    private long pageSize;

    private long totalCount;

    /** 带有状态数据 */
    private HashMap<String,Integer[]> num;

    /** 列表数据 */
    private List<?> data;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private String msg;

    /**
     * 表格数据对象
     * @param
     */
    public TableDataInfo(IPage<T> page)
    {
        this.data = page.getRecords();
        this.totalPage = page.getPages();
        this.pageNo = page.getCurrent();
        this.totalCount = page.getTotal();
        this.pageSize = page.getSize();
    }

    /**
     * 表格数据对象
     * @param
     */
    public TableDataInfo(Page<T> page)
    {
        this.data = page.getRecords();
        this.totalPage = page.getPages();
        this.pageNo = page.getCurrent();
        this.totalCount = page.getTotal();
        this.pageSize = page.getSize();
    }

    /**
     * 分页
     * 
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.data = list;
        this.totalPage = total;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public HashMap<String, Integer[]> getNum() {
        return num;
    }

    public void setNum(HashMap<String, Integer[]> num) {
        this.num = num;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
