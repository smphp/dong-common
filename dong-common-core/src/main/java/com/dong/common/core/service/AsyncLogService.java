package com.dong.common.core.service;

import com.dong.common.core.domain.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 *
 * @author ruoyi
 */
@Service
public class AsyncLogService
{

    /**
     * 保存系统日志记录
     * TODO 日志操作交给ELK
     */
    @Async
    public void saveSysLog(SysOperLog sysOperLog)
    {
        System.out.println("日志操作交给ELK，这里是伪代码");
    }
}
