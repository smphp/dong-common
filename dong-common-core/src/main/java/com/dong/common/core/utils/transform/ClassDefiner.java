package com.dong.common.core.utils.transform;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClassDefiner {
    private Map<String, PropertyDefiner> propertyList = new HashMap();
    private Class fieldClazz;
    private FieldType fieldType;
    private String beanName;

    public String getBeanName() {
        return this.beanName;
    }

    public ClassDefiner(Class clazz) {
        this.fieldClazz = clazz;
        this.initType();
        if (this.fieldType == FieldType.OBJECT) {
            this.definerFields(clazz);
            Class tmp = clazz;

            while(true) {
                Class superclass = tmp.getSuperclass();
                if (Object.class == superclass) {
                    String simpleName = clazz.getSimpleName();
                    this.beanName = NameUtil.toLowStart(simpleName);
                    break;
                }

                this.definerFields(superclass);
                tmp = superclass;
            }
        }

    }

    private void definerFields(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Field[] var3 = fields;
        int var4 = fields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                PropertyDefiner propertyDefiner = new PropertyDefiner(field, clazz);
                this.propertyList.put(propertyDefiner.getFieldName(), propertyDefiner);
            }
        }

    }

    private void initType() {
        this.fieldType = getFieldType(this.fieldClazz);
    }

    public static FieldType getFieldType(Class fieldClazz) {
        if (!fieldClazz.isPrimitive() && !fieldClazz.isEnum() && !fieldClazz.isAssignableFrom(String.class) && !fieldClazz.isAssignableFrom(Date.class)) {
            if (Collection.class.isAssignableFrom(fieldClazz)) {
                return FieldType.COLLECTION;
            } else if (fieldClazz.isArray()) {
                return FieldType.ARRAY;
            } else {
                return Map.class.isAssignableFrom(fieldClazz) ? FieldType.MAP : FieldType.OBJECT;
            }
        } else {
            return FieldType.PRIMITIVE;
        }
    }

    public Map<String, PropertyDefiner> getPropertyList() {
        return this.propertyList;
    }

    public PropertyDefiner getProperty(PropertyDefiner source) {
        String fieldName = source.getFieldName();
        PropertyDefiner propertyDefiner = (PropertyDefiner)this.propertyList.get(fieldName);
        if (propertyDefiner == null) {
            if (fieldName.endsWith("Enum")) {
                fieldName = fieldName.replace("Enum", "");
                return (PropertyDefiner)this.propertyList.get(fieldName);
            } else {
                propertyDefiner = (PropertyDefiner)this.propertyList.get(fieldName + "Enum");
                return propertyDefiner != null ? propertyDefiner : (PropertyDefiner)this.propertyList.get(NameUtil.change(fieldName));
            }
        } else {
            return propertyDefiner;
        }
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }
}
