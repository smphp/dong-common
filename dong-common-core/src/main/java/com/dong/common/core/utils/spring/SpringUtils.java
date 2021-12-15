package com.dong.common.core.utils.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dong.common.core.constant.Constants;
import com.dong.common.core.domain.UsersDetailDTO;
import com.dong.common.core.exception.CustomException;
import com.dong.common.core.exception.UtilException;
import com.dong.common.core.service.RedisCache;
import com.dong.common.core.service.TokenService;
import com.dong.common.core.utils.JwtTokenUtil;
import com.dong.common.core.utils.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 * 
 * @author wdzk
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware 
{
    /** Spring应用上下文环境 */
    private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;



    /**
     * 判断当前是否是登录用户
     * @return
     */
    public static boolean isLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        TokenService tokenService = applicationContext.getBean(TokenService.class);
        RedisCache redisCache = applicationContext.getBean(RedisCache.class);
        String bearers = tokenService.getToken(request);
        String token = redisCache.getCacheObject(Constants.FRONTEND_LOGIN_TOKEN_KEY+bearers);
        if (!StringUtils.isEmpty(token) && !StringUtils.isBlank(token)) {
            String subject = null;
            try {
                subject = JwtTokenUtil.parseToken(token);
            } catch (UtilException e) {
                throw new UtilException(e);
            }
            if(subject==null){
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static UsersDetailDTO getUserDetail() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        TokenService tokenService = applicationContext.getBean(TokenService.class);
        RedisCache redisCache = applicationContext.getBean(RedisCache.class);
        String bearers = tokenService.getToken(request);
        String token = redisCache.getCacheObject(Constants.FRONTEND_LOGIN_TOKEN_KEY+bearers);
        if (!StringUtils.isEmpty(token) && !StringUtils.isBlank(token)) {
            String subject = null;
            try {
                subject = JwtTokenUtil.parseToken(token);
            } catch (UtilException e) {
                throw new UtilException(e);
            }
            if(subject==null){
                throw new CustomException("用户未登录");
            }
            UsersDetailDTO usersDetailDTO = transfer(subject);
            return usersDetailDTO;
        }
        throw new CustomException("用户未登录");
    }

    public static UsersDetailDTO transfer(String subject) {
        if (subject == null)
            return null;
        UsersDetailDTO usersDetailDTO = new UsersDetailDTO();
        JSONObject jsonObject = JSON.parseObject(subject);
        if (jsonObject.get("email") != null)
            usersDetailDTO.setEmail(jsonObject.get("email").toString());
        if (jsonObject.get("expireTime") != null)
            usersDetailDTO.setExpireTime(jsonObject.get("expireTime").toString());
        if (jsonObject.get("userId") != null)
            usersDetailDTO.setUserId(Long.valueOf(jsonObject.get("userId").toString()));
        if (jsonObject.get("mobile") != null)
            usersDetailDTO.setMobile(String.valueOf(jsonObject.get("mobile")));
        if (jsonObject.get("openId") != null)
            usersDetailDTO.setOpenId(String.valueOf(jsonObject.get("openId")));
        if (jsonObject.get("username") != null)
            usersDetailDTO.setUserName(String.valueOf(jsonObject.get("username")));
        return usersDetailDTO;
    }

    public static String getPostParams(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring("Bearer ".length());
            if(!StringUtils.isBlank(authToken) && authToken.length() >= 32){
                return authToken;
            }
        }
        return null;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException 
    {
        SpringUtils.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException 
    {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取对象
     *
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException
    {
        return (T) beanFactory.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static String[]  getBeans() throws BeansException
    {
        return applicationContext.getBeanDefinitionNames();
    }
    @SuppressWarnings("unchecked")
    public static <T> T getBeanByName(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }
    /**
     * 获取类型为requiredType的对象
     *
     * @param clz
     * @return
     * @throws BeansException
     *
     */
    public static <T> T getBean(Class<T> clz) throws BeansException
    {
        T result = (T) beanFactory.getBean(clz);
        return result;
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name)
    {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name
     * @return boolean
     * @throws NoSuchBeanDefinitionException
     *
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactory.isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws NoSuchBeanDefinitionException
     *
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactory.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     *
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取aop代理对象
     * 
     * @param invoker
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker)
    {
        return (T) AopContext.currentProxy();
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles()
    {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     */
    public static String getActiveProfile()
    {
        final String[] activeProfiles = getActiveProfiles();
        return StringUtils.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
    }


}
