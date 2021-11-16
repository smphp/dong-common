package com.dong.common.core.utils.transform;

public class BeanCopyException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Class beanClass;

    public <T> BeanCopyException(Class<T> beanClass, String msg, Exception cause) {
        super("Could not instantiate bean class [" + beanClass.getName() + "]: " + msg, cause);
        this.beanClass = beanClass;
    }

    public <T> BeanCopyException(Class<T> beanClass, String msg) {
        super("Could not instantiate bean class [" + beanClass.getName() + "]: " + msg);
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return this.beanClass;
    }
}
