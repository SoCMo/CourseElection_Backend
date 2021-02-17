package com.spring.CourseElection.tools;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * program: TimeTool
 * description: 时间转换
 * author: SoCMo
 * create: 2019/12/6 14:28
 */
public class TimeTool {
    private static final String[] Week_CN = {"周一", "周二", "周三", "周四", "周五", "周六", "周七"};

    private static final List<String> Num_CN = Arrays.asList("一", "二", "三", "四", "五", "六", "七");

    /**
     * @Description: date转换为最小为分的格式
     * @Param: [date]
     * @Return: java.lang.String
     * @Author: SoCMo
     * @Date: 2019/12/6
     */
    public static String timeToMinute(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        //设置时区为Asia/Shanghai
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat.format(date);
    }

    /**
     * @Description: date转换为最小为秒的格式
     * @Param: [date]
     * @Return: java.lang.String
     * @Author: SoCMo
     * @Date: 2021/1/4
     */
    public static String timeToSec(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //设置时区为Asia/Shanghai
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat.format(date);
    }

    /**
     * @Description: date转换为最小为天的格式
     * @Param: [date]
     * @Return: java.lang.String
     * @Author: SoCMo
     * @Date: 2019/12/6
     */
    public static String timeToDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        //设置时区为Asia/Shanghai
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat.format(date);
    }

    /**
     * @Description: date转换为最小为天的格式2
     * @Param: [date]
     * @Return: java.lang.String
     * @Author: SoCMo
     * @Date: 2019/12/11
     */
    public static String timeToDay2(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置时区为Asia/Shanghai
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat.format(date);
    }

    /**
     * @Description: 字符串转化为Date类型
     * @Param: [time]
     * @Return: java.util.Date
     * @Author: SoCMo
     * @Date: 2019/12/11
     */
    public static Date stringToDay(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
    * @Description: 转成存储在数据库中的时间
    * @Param: [time]
    * @Return: java.lang.String
    * @Author: SoCMo
    * @Date: 2021/1/21
    */
    public static String saveTime(String time) {
        int [][] timeRange = new int[7][13];
        time = time.replace("周", "");

        for(String day: time.split(";")){
            String[] split = day.split(":");
            if(StringUtils.isBlank(split[0])) continue;
            int dayIndex = Num_CN.indexOf(split[0]);

            if(!split[1].contains("-")){
                timeRange[dayIndex][Integer.parseInt(split[1]) - 1] = 1;
            }else {
                String[] fromTO = split[1].split("-");
                for(int i = Integer.parseInt(fromTO[0]); i <= Integer.parseInt(fromTO[1]); i++)
                timeRange[dayIndex][i - 1] = 1;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 7; i++){
            for (int j = 0; j < 13; j++) {
                stringBuilder.append(timeRange[i][j]);
            }
        }
        return stringBuilder.toString();
    }

    /**
    * @Description: 读取时间
    * @Param: [time]
    * @Return: java.lang.String
    * @Author: SoCMo
    * @Date: 2021/1/21
    */
    public static String loadTime(String time) {
        StringBuilder result = new StringBuilder("");
        for(int i = 0; i < 7; i++){
            int min = 99;
            int max = 0;
            for(int j = 0; j < 13; j++){
                if(time.charAt(i * 13 + j) == '1'){
                    if(min > j) min = j;
                    if(max < j) max = j;
                }
            }
            if(min != 99){
                result.append(Week_CN[i]).append(":").append(min + 1)
                        .append("-").append(max + 1).append(";");
            }
        }
        return result.toString();
    }
}
