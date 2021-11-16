package com.dong.common.core.utils.transform;



import com.dong.common.core.utils.BigDecimalUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class BeanConvert {
    private static final Map<Class, ClassDefiner> classCache = new WeakHashMap();

    public BeanConvert() {
    }

    private static void copy(Object source, Object target) {
        ClassDefiner sourceDefiner = getClassDefiner(source.getClass());
        ClassDefiner targetDefiner = getClassDefiner(target.getClass());
        Collection<PropertyDefiner> targetPropertyList = targetDefiner.getPropertyList().values();
        Iterator var5 = targetPropertyList.iterator();

        while(var5.hasNext()) {
            PropertyDefiner targetProperty = (PropertyDefiner)var5.next();
            String fieldName = targetProperty.getFieldName();
            PropertyDefiner sourceProperty = sourceDefiner.getProperty(targetProperty);
            if (sourceProperty != null) {
                copyProperty(source, target, sourceProperty, targetProperty);
            }
        }

    }

    public static String code(Class source, Class target) {
        ClassDefiner sourceDefiner = getClassDefiner(source);
        ClassDefiner targetDefiner = getClassDefiner(target);
        StringBuilder errorInfo = new StringBuilder("\n");
        StringBuilder methodInfo = new StringBuilder("\n");
        StringBuilder subTransfer = new StringBuilder("\n");
        String sourceBeanName = sourceDefiner.getBeanName();
        String targetBeanName = targetDefiner.getBeanName();
        methodInfo.append("public static " + target.getSimpleName() + " transfer(" + source.getSimpleName() + " " + sourceBeanName + "){\n");
        methodInfo.append("\tif(").append(sourceBeanName).append("==null){\n").append("\t\treturn null;\n").append("\t}\n");
        methodInfo.append("\t" + target.getSimpleName() + " " + targetBeanName + " = news " + target.getSimpleName() + "();\n");
        Collection<PropertyDefiner> targetPropertyList = targetDefiner.getPropertyList().values();
        Iterator var10 = targetPropertyList.iterator();

        while(true) {
            while(var10.hasNext()) {
                PropertyDefiner targetProperty = (PropertyDefiner)var10.next();
                String targetFieldName = targetProperty.getFieldName();
                PropertyDefiner sourceProperty = sourceDefiner.getProperty(targetProperty);
                if (sourceProperty == null) {
                    errorInfo.append(targetFieldName).append("没有对应的属性\n");
                } else {
                    String sourceFieldName = sourceProperty.getFieldName();
                    if (sourceProperty.getFieldType() == FieldType.COLLECTION) {
                        Class sourceType = getActualType(sourceProperty);
                        Class targetType = getActualType(targetProperty);
                        if (List.class.isAssignableFrom(targetProperty.getFieldClazz())) {
                            methodInfo.append("\tList<").append(targetType.getSimpleName()).append("> ").append(targetProperty.getField().getName()).append(" =news ArrayList<").append(targetType.getSimpleName()).append(">();\n");
                        } else {
                            methodInfo.append("\tSet<").append(targetType.getSimpleName()).append("> ").append(targetProperty.getField().getName()).append(" =news HashSet<").append(targetType.getSimpleName()).append(">();\n");
                        }

                        FieldType fieldType = ClassDefiner.getFieldType(sourceType);
                        methodInfo.append("\tif(").append(sourceBeanName).append(".get").append(sourceFieldName).append("() != null){\n");
                        if (fieldType == FieldType.OBJECT) {
                            methodInfo.append("\t\tfor(").append(sourceType.getSimpleName()).append(" " + NameUtil.toLowStart(sourceType.getSimpleName()) + " : ").append(sourceBeanName).append(".get").append(sourceFieldName).append("()){\n");
                            methodInfo.append("\t\t\t" + targetProperty.getField().getName() + ".add(transfer(" + NameUtil.toLowStart(sourceType.getSimpleName()) + "));\n");
                            methodInfo.append("\t\t}\n");
                            subTransfer.append(code(sourceType, targetType));
                        } else if (fieldType == FieldType.PRIMITIVE) {
                            methodInfo.append("\t\tfor(").append(sourceType.getSimpleName()).append(" " + NameUtil.toLowStart(sourceType.getSimpleName()) + " : ").append(sourceBeanName).append(".get").append(sourceFieldName).append("()){\n");
                            methodInfo.append("\t\t\t" + targetProperty.getField().getName() + ".add(" + NameUtil.toLowStart(sourceType.getSimpleName()) + ");\n");
                            methodInfo.append("\t\t}\n");
                        }

                        methodInfo.append("\t}\n");
                        methodInfo.append("\t").append(targetBeanName).append(".set").append(targetFieldName).append("(" + targetProperty.getField().getName() + ");\n");
                    } else if (!sourceProperty.getFieldClazz().equals(targetProperty.getFieldClazz())) {
                        if (sourceProperty.getFieldClazz().isEnum() && targetProperty.getFieldClazz().isEnum()) {
                            subTransfer.append(codeEnum(sourceProperty.getFieldClazz(), targetProperty.getFieldClazz()));
                            methodInfo.append("\t").append(Template.transfCopy(targetBeanName, targetFieldName, sourceBeanName, sourceFieldName)).append("\n");
                        } else if (sourceProperty.getFieldClazz().equals(String.class) && targetProperty.getFieldClazz().isEnum()) {
                            methodInfo.append("\t").append(Template.stringToEnum(targetBeanName, targetFieldName, sourceBeanName, sourceFieldName, targetProperty.getFieldClazz().getSimpleName())).append("\n");
                        } else if (sourceProperty.getFieldClazz().isEnum() && targetProperty.getFieldClazz().equals(String.class)) {
                            methodInfo.append("\t").append(Template.enumToString(targetBeanName, targetFieldName, sourceBeanName, sourceFieldName)).append("\n");
                        } else if (sourceProperty.getFieldType() == FieldType.OBJECT) {
                            subTransfer.append(code(sourceProperty.getFieldClazz(), targetProperty.getFieldClazz()));
                            methodInfo.append("\t").append(Template.transfCopy(targetBeanName, targetFieldName, sourceBeanName, sourceFieldName)).append("\n");
                        } else if (sourceProperty.getFieldType() == FieldType.PRIMITIVE) {
                            errorInfo.append(targetFieldName).append("类型不匹配\n");
                        }
                    } else {
                        methodInfo.append("\t").append(Template.baseCopy(targetBeanName, targetFieldName, sourceBeanName, sourceFieldName)).append("\n");
                    }
                }
            }

            methodInfo.append("\treturn " + targetBeanName + ";\n}\n\n");
            methodInfo.append(subTransfer);
            methodInfo.append(errorInfo);
            return methodInfo.toString();
        }
    }

    private static Class getActualType(PropertyDefiner sourceProperty) {
        ParameterizedType genericType = (ParameterizedType)sourceProperty.getField().getGenericType();
        Type[] actualTypeArguments = genericType.getActualTypeArguments();
        return (Class)actualTypeArguments[0];
    }

    public static String codeEnum(Class source, Class target) {
        StringBuilder methodInfo = new StringBuilder();
        Object[] enumConstants = source.getEnumConstants();
        String sourceBeanName = NameUtil.toLowStart(source.getSimpleName());
        methodInfo.append("public static " + target.getSimpleName() + " transfer(" + source.getSimpleName() + " " + sourceBeanName + "){\n");
        methodInfo.append("\tif (").append(sourceBeanName).append(" == null){\n");
        methodInfo.append("\t\treturn null;\n");
        methodInfo.append("\t}\n");
        methodInfo.append("\tswitch(").append(sourceBeanName).append("){\n");
        Object[] var5 = enumConstants;
        int var6 = enumConstants.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Object enm = var5[var7];
            methodInfo.append("\t\tcase ").append(enm.toString()).append(" : return ").append(target.getSimpleName()).append(".").append(enm.toString()).append(";\n");
        }

        methodInfo.append("\t\tdefault : throw news RuntimeException(\"").append(sourceBeanName).append(" not support\");\n");
        methodInfo.append("\t}\n}");
        return methodInfo.toString();
    }

    private static void copyProperty(Object sobj, Object tobj, PropertyDefiner source, PropertyDefiner target) {
        FieldType fieldType = target.getFieldType();
        switch(fieldType) {
            case PRIMITIVE:
                copyPrimitive(sobj, tobj, source, target);
                break;
            case ARRAY:
                copyArray(sobj, tobj, source, target);
                break;
            case COLLECTION:
                copyCollection(sobj, tobj, source, target);
                break;
            case MAP:
                copyMap(sobj, tobj, source, target);
                break;
            case OBJECT:
                copyObject(sobj, tobj, source, target);
        }

    }

    private static void copyMap(Object sobj, Object tobj, PropertyDefiner source, PropertyDefiner target) {
        throw new BeanCopyException(source.getClazz(), "map not support " + source.getFieldName());
    }

    private static void copyCollection(Object sobj, Object tobj, PropertyDefiner source, PropertyDefiner target) {
        try {
            Collection collection = (Collection)source.getReadMethod().invoke(sobj);
            if (collection != null) {
                Collection instantiate = (Collection)instantiate(collection.getClass());
                Class type = getActualType(target);
                Iterator var7 = collection.iterator();

                while(var7.hasNext()) {
                    Object object = var7.next();
                    Object copy = copy(object, type);
                    instantiate.add(copy);
                }

                target.getWriteMethod().invoke(tobj, instantiate);
            }
        } catch (Exception var10) {
            throw new BeanCopyException(source.getClazz(), "copy value error " + source.getFieldName());
        }
    }

    private static void copyArray(Object sobj, Object tobj, PropertyDefiner source, PropertyDefiner target) {
        throw new BeanCopyException(source.getClazz(), "array not support " + source.getFieldName());
    }

    private static void copyPrimitive(Object sobj, Object tobj, PropertyDefiner source, PropertyDefiner target) {
        FieldType f = target.getFieldType();
        FieldType s = source.getFieldType();
        Method writeMethod = target.getWriteMethod();

        try {
            Object sourceProperty = Transfer(source,target,source.getReadMethod().invoke(sobj));
            if (sourceProperty != null) {
                writeMethod.invoke(tobj, sourceProperty);
            }
        } catch (Exception var6) {
            throw new BeanCopyException(source.getClazz(), "copy value error " + source.getFieldName());
        }
    }

    //String和Integer 转化  Integer->String转化
    private static Object Transfer( PropertyDefiner source, PropertyDefiner target,Object sourceProperty){
        Boolean SS = source.getFieldClazz().getTypeName().equals("java.lang.String");
        Boolean SI = source.getFieldClazz().getTypeName().equals("java.lang.Integer");

        Boolean TS = target.getFieldClazz().getTypeName().equals("java.lang.String");
        Boolean TI = target.getFieldClazz().getTypeName().equals("java.lang.Integer");
        if(SS && TI){
            sourceProperty = BigDecimalUtil.savePrice(String.valueOf(sourceProperty));
        }
        if(SI && TS){
            sourceProperty = BigDecimalUtil.showPriceStr(Integer.valueOf(String.valueOf(sourceProperty)));
        }
        return sourceProperty;
    }

    private static void copyObject(Object sobj, Object tobj, PropertyDefiner source, PropertyDefiner target) {
        try {
            Object value = source.getReadMethod().invoke(sobj);
            if (value != null) {
                Object copy = copy(value, target.getFieldClazz());
                target.getWriteMethod().invoke(tobj, copy);
            }
        } catch (Exception var6) {
            throw new BeanCopyException(source.getClass(), "copy value error " + source.getFieldName());
        }
    }

    public static <T> T copy(Object source, Class<T> target) {
        FieldType type = getType(target);
        switch(type) {
            case PRIMITIVE:
                return (T) source;
            case ARRAY:
            case COLLECTION:
            case MAP:
                return null;
            case OBJECT:
                T targetObj = instantiate(target);
                copy(source, targetObj);
                return targetObj;
            default:
                return null;
        }
    }

    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class)clz.getField("TYPE").get((Object)null)).isPrimitive();
        } catch (Exception var2) {
            return false;
        }
    }

    private static FieldType getType(Class fieldClazz) {
        FieldType fieldType;
        if (!fieldClazz.isPrimitive() && !fieldClazz.isEnum() && !fieldClazz.isAssignableFrom(String.class) && !isWrapClass(fieldClazz) && !fieldClazz.isAssignableFrom(Date.class)) {
            if (Collection.class.isAssignableFrom(fieldClazz)) {
                fieldType = FieldType.COLLECTION;
            } else if (fieldClazz.isArray()) {
                fieldType = FieldType.ARRAY;
            } else if (Map.class.isAssignableFrom(fieldClazz)) {
                fieldType = FieldType.MAP;
            } else {
                fieldType = FieldType.OBJECT;
            }
        } else {
            fieldType = FieldType.PRIMITIVE;
        }

        return fieldType;
    }

    private static <T> T instantiate(Class<T> clazz) {
        if (clazz.isInterface()) {
            throw new BeanCopyException(clazz, "Specified class is an interface");
        } else {
            try {
                return clazz.newInstance();
            } catch (InstantiationException var2) {
                throw new BeanCopyException(clazz, "Is it an abstract class?", var2);
            } catch (IllegalAccessException var3) {
                throw new BeanCopyException(clazz, "Is the constructor accessible?", var3);
            }
        }
    }

    private static ClassDefiner getClassDefiner(Class clazz) {
        ClassDefiner classDefiner = (ClassDefiner)classCache.get(clazz);
        if (classDefiner == null) {
            synchronized(clazz) {
                classDefiner = (ClassDefiner)classCache.get(clazz);
                if (classDefiner != null) {
                    return classDefiner;
                } else {
                    classDefiner = new ClassDefiner(clazz);
                    classCache.put(clazz, classDefiner);
                    return classDefiner;
                }
            }
        } else {
            return classDefiner;
        }
    }
}
