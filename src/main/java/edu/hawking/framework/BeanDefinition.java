package edu.hawking.framework;

/**
 * 杜皓君 created by 2021/3/22
 * BeanDefinition
 **/
public class BeanDefinition {
    private boolean lazy;

    private String scope;

    private Class beanClass;

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
