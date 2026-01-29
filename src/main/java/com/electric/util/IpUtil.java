package com.electric.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.electric.model.constant.Numbers;
import com.electric.model.constant.StringConstant;
import com.google.common.base.Splitter;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户ip获取工具类
 *
 * @author sunk
 * @date 2024/09/11
 */
@Slf4j
public class IpUtil {

    /**
     * IP地址的正则表达式.
     */
    public static final String  IP_REGEX   = "((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})";
    private static final String UNKNOWN    = "unknown";
    private static final String LOCALHOST  = "127.0.0.1";
    private static final String LOCALHOST1 = "0:0:0:0:0:0:0:1";

    private static Pattern      IP_PATTERN = Pattern.compile(IP_REGEX);

    /**
     * 获取访问用户ip
     *
     * @param request
     * @return
     * @create  2022年1月26日 下午2:41:09 luochao
     * @history
     */
    public static String getRequestIpAddr(HttpServletRequest request) {
        return getRequestIpAddr(request, false);
    }

    /**
     * 获取访问用户ip 场景：日志打印ip链路
     *
     * @param request
     * @param fullIp 是否返回全部ip  true：全部(包含路由ip)   false:客户真实ip(不包含路由ip)
     * @return
     * @create  2022年1月26日 下午2:41:29 luochao
     * @history
     */
    public static String getRequestIpAddr(HttpServletRequest request, Boolean fullIp) {
        String headerCode = "x-forwarded-for";
        String ipAddress = request.getHeader(headerCode);
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            headerCode = "Proxy-Client-IP";
            ipAddress = request.getHeader(headerCode);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            headerCode = "WL-Proxy-Client-IP";
            ipAddress = request.getHeader(headerCode);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            headerCode = "RemoteAddr";
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST.equals(ipAddress) || LOCALHOST1.equals(ipAddress)) {
                headerCode = "localhost";
                //根据网卡获取本机配置的IP地址
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (inetAddress != null) {
                    ipAddress = inetAddress.getHostAddress();
                }
            }
        }
        //if (log.isDebugEnabled()) {
        //    log.debug("getRequestIpAddr(" + headerCode + ")--->" + ipAddress);
        //}
        //要求ip全部返回
        if (fullIp != null && fullIp) {
            return ipAddress;
        }
        //对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
        List<String> ipList = Splitter.on(",").splitToList(ipAddress);
        if (ipList != null && ipList.size() == 1) {
            return ipList.get(0);
        } else {
            for (int i = ipList.size() - 1; i >= 0; i--) {
                String ip = ipList.get(i).trim();
                if (ip.startsWith(StringConstant.INNER_NETWORK)) {
                    continue;
                }
                ipAddress = ip;
                break;
            }
        }
        return ipAddress;
    }

    /**
     * 获取请求ip,依赖spring上下文
     *
     * @return
     * @create  2022年8月3日 下午3:15:05 luochao
     * @history
     */
    public static String getRequestIpAddr() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return "";
        }
        HttpServletRequest request = attrs.getRequest();
        if (request == null) {
            return "";
        }
        return getRequestIpAddr(request);
    }

    /***
     * 获取本机ip地址
     *注：多网卡有问题
     * @return
     * @create  2018年5月17日 下午1:07:06 luochao
     * @history
     */
    public static String getLocalIp() {
        return IpUtils.getIp();
    }

    /**
     * 获取本机ip地址,请使用getLocalIp
     *
     * @return
     * @create  2019年9月24日 下午8:08:33 luochao
     * @history
     */
    @Deprecated
    public static String getLocalIp2() {
        return IpUtils.getIp();
    }

    /**
     * 根据url地址获取对方ip地址
     *
     * @param url
     * @return
     * @create  2020年12月15日 上午11:31:22 luochao
     * @history
     */
    public static String getRemoteIp(String url) {
        if (StringUtils.isEmpty(url)) {
            return StringUtils.EMPTY;
        }
        List<String> splitToList = Splitter.on("/").omitEmptyStrings().splitToList(url);
        try {
            String domain = splitToList.get(Numbers.INT_1);
            //判断是否包含冒号(:),包含说明就是ip无需再获取，直接返回
            if (domain.split(StringConstant.COLON).length > Numbers.INT_1) {
                return domain;
            }
            return InetAddress.getByName(domain).getHostAddress();
        } catch (UnknownHostException e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 解析字符串中的ip,格式：192.168.68.88-12
     *解释：从88往后解析12个ip，到192.168.68.99
     * @param ipsStr
     * @return
     * @create  2021年2月25日 下午1:31:36 luochao
     * @history
     */
    public static List<String> getIpList(String ipsStr) {
        if (StringUtils.isEmpty(ipsStr)) {
            return null;
        }
        try {
            List<String> splitToList = Splitter.on(StringConstant.LINE).omitEmptyStrings().trimResults().splitToList(ipsStr);
            if (splitToList.size() == Numbers.INT_1) {
                return Arrays.asList(ipsStr);
            } else {
                String ip = splitToList.get(Numbers.INT_0);
                Integer count = Integer.valueOf(splitToList.get(Numbers.INT_1));
                List<String> ipArray = Splitter.on(StringConstant.POINT).splitToList(ip);
                List<String> ipList = new ArrayList<String>();
                for (int i = 0; i < count; i++) {
                    ipList.add(ipArray.get(Numbers.INT_0) + StringConstant.POINT + ipArray.get(Numbers.INT_1) + StringConstant.POINT
                               + ipArray.get(Numbers.INT_2) + StringConstant.POINT + (Integer.valueOf(ipArray.get(Numbers.INT_3)) + i));
                }
                return ipList;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 从字符串中提取ip地址
     *
     * @param input
     * @return
     * @date  2023年10月27日 下午3:27:31 luochao
     *
     */
    public static String extractIPAddress(String input) {
        Matcher matcher = IP_PATTERN.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 把字符串IP转换成long
     *
     * @param ipStr 字符串IP
     * @return IP对应的long值
     */
    public static long ip2Long(String ipStr) {
        String[] ip = ipStr.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 把IP的long值转换成字符串
     *
     * @param ipLong IP的long值
     * @return long值对应的字符串
     */
    public static String long2Ip(long ipLong) {
        StringBuilder ip = new StringBuilder();
        ip.append(ipLong >>> 24).append(".");
        ip.append((ipLong >>> 16) & 0xFF).append(".");
        ip.append((ipLong >>> 8) & 0xFF).append(".");
        ip.append(ipLong & 0xFF);
        return ip.toString();
    }

    public static void main(String[] args) {
        String input = "This is an example string with an IP addressreqIp192.168.5.55511embedded in it";
        String ipAddress = extractIPAddress(input);
        System.out.println(ipAddress);
        //System.out.println(IpUtil.getIpList("192.168.68.88-12"));

        System.out.println(ip2Long("192.168.0.1"));
        System.out.println(long2Ip(3232235521L));
        System.out.println(ip2Long("10.0.0.1"));
    }

}

class IpUtils {

    private static volatile String cachedIpAddress;

    /**
     * Get localhost IP address.
     *
     * <p>
     * It maybe get IP address of router.
     * Return unknown IP if exception occur.
     * </p>
     *
     * @return IP address of localhost
     */
    public static String getIp() {
        if (null != cachedIpAddress) {
            return cachedIpAddress;
        }
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (final SocketException ex) {
            return "UnknownIP";
        }
        String localIpAddress = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = netInterfaces.nextElement();
            Enumeration<InetAddress> ipAddresses = netInterface.getInetAddresses();
            while (ipAddresses.hasMoreElements()) {
                InetAddress ipAddress = ipAddresses.nextElement();
                if (isPublicIpAddress(ipAddress)) {
                    String publicIpAddress = ipAddress.getHostAddress();
                    cachedIpAddress = publicIpAddress;
                    return publicIpAddress;
                }
                if (isLocalIpAddress(ipAddress)) {
                    localIpAddress = ipAddress.getHostAddress();
                }
            }
        }
        cachedIpAddress = localIpAddress;
        return localIpAddress;
    }

    private static boolean isPublicIpAddress(final InetAddress ipAddress) {
        return !ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private static boolean isLocalIpAddress(final InetAddress ipAddress) {
        return ipAddress.isSiteLocalAddress() && !ipAddress.isLoopbackAddress() && !isV6IpAddress(ipAddress);
    }

    private static boolean isV6IpAddress(final InetAddress ipAddress) {
        return ipAddress.getHostAddress().contains(":");
    }
}