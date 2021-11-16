package com.dong.common.core.utils.transform;

public class Template {
    private static final String BASE_COPY = "%s.set%s(%s.get%s());";
    private static final String TRANSF_COPY = "%s.set%s(transfer(%s.get%s()));";
    private static final String ENUM_TO_STRING = "%s.set%s(%s.get%s()==null?null:%s.get%s().name());";
    private static final String STRING_TO_ENUM = "%s.set%s(%s.get%s()==null?null:%s.valueOf(%s.get%s()));";

    public Template() {
    }

    public static String baseCopy(String targetBeanName, String targetFieldName, String sourceBeanName, String sourceFieldName) {
        return String.format("%s.set%s(%s.get%s());", targetBeanName, targetFieldName, sourceBeanName, sourceFieldName);
    }

    public static String transfCopy(String targetBeanName, String targetFieldName, String sourceBeanName, String sourceFieldName) {
        return String.format("%s.set%s(transfer(%s.get%s()));", targetBeanName, targetFieldName, sourceBeanName, sourceFieldName);
    }

    public static String stringToEnum(String targetBeanName, String targetFieldName, String sourceBeanName, String sourceFieldName, String enumName) {
        return String.format("%s.set%s(%s.get%s()==null?null:%s.valueOf(%s.get%s()));", targetBeanName, targetFieldName, sourceBeanName, sourceFieldName, enumName, sourceBeanName, sourceFieldName);
    }

    public static String enumToString(String targetBeanName, String targetFieldName, String sourceBeanName, String sourceFieldName) {
        return String.format("%s.set%s(%s.get%s()==null?null:%s.get%s().name());", targetBeanName, targetFieldName, sourceBeanName, sourceFieldName, sourceBeanName, sourceFieldName);
    }
}
