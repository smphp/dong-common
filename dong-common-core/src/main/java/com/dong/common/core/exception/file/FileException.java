package com.dong.common.core.exception.file;


import com.dong.common.core.exception.BaseException;

/**
 * 文件信息异常类
 * 
 * @author dong
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
