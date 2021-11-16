package com.dong.common.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class BigDecimalUtil {

    /**
     * Integer转BigDecial
     * @param price
     * @return
     */
    public static Integer savePrice(BigDecimal price){
        String s = (new BigDecimal(String.valueOf(price))).multiply(new BigDecimal(100L)).stripTrailingZeros().toPlainString();
        return Integer.parseInt(s);
    }

    /**
     * string转BigDecial
     * @param price
     * @return
     */
    public static Integer savePrice(String price){
        if(price!="null" &&  StringUtils.isNotBlank(price)){
            Double priceDouble = Double.parseDouble(price)*100D;
            return priceDouble.intValue();
        }
        return 0;
    }

    /**
     * string转BigDecial
     * @param price
     * @return
     */
    public static Long savePriceLong(String price){
        if(price!="null" &&  StringUtils.isNotBlank(price)){
            Double priceDouble = Double.parseDouble(price)*100D;
            return new Double(priceDouble.intValue()).longValue();
        }
        return 0L;
    }


    /**
     * string转BigDecial
     * @param price
     * @return
     */
    public static BigDecimal showPrice(Integer price){
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(price));
        return bigDecimal.divide(BigDecimal.valueOf(100L));
    }

    /**
     * string转BigDecial
     * @param price
     * @return
     */
    public static BigDecimal showPrice(Long price){
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(price));
        return bigDecimal.divide(BigDecimal.valueOf(100L));
    }

    public static String showPriceStr(Long price){
        if(price==null){
            return "0.00";
        }
        if(price>0){
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(price));
            String resultCompute  = String.valueOf(bigDecimal.divide(BigDecimal.valueOf(100L)));
            return new java.text.DecimalFormat("0.00").format(Double.parseDouble(resultCompute));
        }else{
            return "0.00";
        }
    }
    public static String showPriceStr(Integer price){
        if(price==null){
            return "0.00";
        }
        if(price>0){
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(price));
            String resultCompute  = String.valueOf(bigDecimal.divide(BigDecimal.valueOf(100L)));
            return new java.text.DecimalFormat("0.00").format(Double.parseDouble(resultCompute));
        }else{
            return "0.00";
        }
    }


    public static Object savePrice(Object actualPrice) {
        String price = String.valueOf(actualPrice);
        if(price!="null" &&  StringUtils.isNotBlank(price)){
            Double priceDouble = Double.parseDouble(price)*100D;
            return priceDouble.intValue();
        }
        return 0;
    }

}
