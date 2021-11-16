package com.dong.common.core.utils.transform;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class CU {
    public CU() {
    }

    public static void d(Class sourceBeanClass, Class targetBeanClass) {
        System.out.println(BeanConvert.code(sourceBeanClass, targetBeanClass));
    }

    public static void c(Class sourceServiceClass, Class targetServiceClass) {
        Method[] sourceMethod = sourceServiceClass.getMethods();
        Set<String> source = new HashSet();
        Method[] targetMethod = sourceMethod;
        int var5 = sourceMethod.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method method = targetMethod[var6];
            source.add(method.getName());
        }

        targetMethod = targetServiceClass.getMethods();
        Set<String> target = new HashSet();
        Method[] var11 = targetMethod;
        int var13 = targetMethod.length;

        for(int var8 = 0; var8 < var13; ++var8) {
            Method method = var11[var8];
            target.add(method.getName());
        }

        HashSet<String> sourceAdd = new HashSet();
        sourceAdd.addAll(source);
        sourceAdd.removeAll(target);
        sourceAdd.forEach((sou) -> {
            System.out.println(sourceServiceClass.getSimpleName() + "增加方法：" + sou);
        });
        HashSet<String> targetAdd = new HashSet();
        targetAdd.addAll(target);
        targetAdd.removeAll(source);
        targetAdd.forEach((sou) -> {
            System.out.println(targetServiceClass.getSimpleName() + "增加方法：" + sou);
        });
    }
}
