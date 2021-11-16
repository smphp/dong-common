package com.dong.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.dong.common.core.utils.spring.SpringUtil;
import com.dong.common.core.utils.transform.BeanConvert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseTransfer<T> {
    private T data;
    private Class<T> clazz;



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public BaseTransfer(){
        Map<String, String> params = SpringUtil.parseFrom();
        String jsonObject = JSONObject.toJSONString(params);
        Type type = this.getClass().getGenericSuperclass(); // generic 泛型
        //获得运行期的泛型类型
        // 强制转化“参数化类型”
        ParameterizedType parameterizedType = (ParameterizedType) type;
        // 参数化类型中可能有多个泛型参数
        Type[] types = parameterizedType.getActualTypeArguments();
        // 获取数据的第一个元素(User.class)
        clazz = (Class<T>) types[0]; // com.oa.shore.entity.User.class
        this.setData(JSONObject.parseObject(jsonObject,clazz));
    }

    @SuppressWarnings("unchecked")
    public static <S,T> T TtoS(S s,Class<T> t) {
        if(s==null){
            return null;
        }
        return BeanConvert.copy(s,t);
    }

    @SuppressWarnings("unchecked")
    public static<S,T> List<T> TtoSList(List<S> list, Class<T> clazz) {
        Function<S,T> f1 = f->TtoS(f,clazz);
        return transfer(list,f1);
    }

    public static<S,T> List<T> transfer(List<S> source, Function<S,T> function){
        if(source==null){
            return null;
        }
        return source.stream().map(function).collect(Collectors.toList());
    }

}
