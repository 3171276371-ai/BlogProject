package cxt.cn.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author:chenxiaotian
 * @Description:
 * @Date: CREATED IN 10:39 上午 2020/5/18
 */
public class StudioUtils {
    /**
     * 去头去尾
     *
     * @param content   字符串
     * @param startChar 开头字符串
     * @param endChar   结束字符串
     * @return 去掉后的字符串
     */
    public static String removeStartEnd(String content, String startChar, String endChar) {
        if (content == null) {
            return null;
        }
        if (startChar == null) {
            startChar = "";
        }
        if (endChar == null) {
            endChar = "";
        }
        return StrUtil.removePrefix(StrUtil.removeSuffix(content, endChar), startChar);
    }

    /**
     * 根据当前日期和操作天数生成操作后的日期
     *
     * @param currentDate 当前日期
     * @param optionDays  操作天数
     * @return 操作后的日期
     */
    public static Date getDate(Date currentDate, Integer optionDays) {
        Calendar calendar = Calendar.getInstance();
        //设置calender时间
        calendar.setTime(currentDate);
        //添加时间
        calendar.add(Calendar.DATE, optionDays);
        return calendar.getTime();
    }

    /**
     * 获取IP地址
     *
     * @param request 请求服务
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }



    /**
     * 日期去掉时间
     *
     * @param date
     * @return
     */
    public static Date removeTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date afterDate = null;
        try {
            afterDate = format.parse(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return afterDate;
    }

    /**
     * 日期转字符
     *
     * @param date
     * @return
     */
    public static String toDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * name与number连接
     *
     * @param name
     * @param number
     * @return
     */
    public static String addNameAndNumber(String name, String number) {
        return name + "@" + number;
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static List<String> toStrList(List<Long> longList) {
        List<String> strList = longList.stream().map(item -> String.valueOf(item)).collect(Collectors.toList());
        return strList;
    }

    public static List<Long> toLongList(List<String> stringList) {
        List<Long> longList = stringList.stream().map(item -> Long.valueOf(item)).collect(Collectors.toList());
        return longList;
    }
}
