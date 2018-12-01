package com.sensetime.iva.ipet.config.filter;

import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.MDCConstants;
import com.sensetime.iva.ipet.util.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author  gongchao
 * 在logback日志输出中增加MDC参数选项
 *
 * 默认情况下，将会把“requestId”、“requestSeq”、“localIp”、“timestamp”、“uri”添加到MDC上下文中。 
 * 1）其中requestId，requestSeq为调用链跟踪使用，开发者不需要手动修改他们。 
 * 2）localIp为当前web容器的宿主机器的本地IP，为内网IP。 
 * 3）timestamp为请求开始被servlet处理的时间戳，设计上为被此Filter执行的开始时间，可以使用此值来判断内部程序执行的效率。 
 * 4）uri为当前request的uri参数值。 
 *
 * 我们可以在logback.xml文件的layout部分，通过%X{key}的方式使用MDC中的变量 
 */
@Configuration
public class HttpRequestMDCFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestMDCFilter.class);

    /**
     *  是否开启cookies映射，如果开启，那么将可以在logback中使用 
     *  %X{_C_:<name>}来打印此cookie，比如：%X{_C_:user}; 
     *  如果开启此选项，还可以使用如下格式打印所有cookies列表: 
     *  格式为：key:value,key:value 
     *  %X{requestCookies}
     */

    private boolean mappedCookies=false;
    /**
     * 是否开启headers映射，如果开启，将可以在logback中使用 
     * %X{_H_:<header>}来打印此header,比如：%X{_H_:X-Forwarded-For}
     * 如果开启此参数，还可以使用如下格式打印所有的headers列表: 
     * 格式为：key:value,key:value 
     * %X{requestHeaders}
     */
    private boolean mappedHeaders=false;

    /**
     * 是否开启parameters映射，此parameters可以为Get的查询字符串，可以为post的Form Entries 
     * %X{_P_:<parameter>}来答应此参数值，比如：%X{_P_:page}
     * 如果开启此参数，还可以使用如下格式打印所有的headers列表: 
     * 格式为：key:value,key:value 
     * %X{requestParameters}
     */
    private boolean mappedParameters=false;
    /**本机IP */
    private String localIp;

    /**all headers,content as key:value,key:value  */
    private static final String HEADERS_CONTENT = "requestHeaders";

     /**all cookies  */
    private static final String COOKIES_CONTENT = "requestCookies";

    /**all parameters  */
    private static final String PARAMETERS_CONTENT = "requestParameters";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mappedCookies = Boolean.valueOf(filterConfig.getInitParameter("mappedCookies"));
        mappedHeaders = Boolean.valueOf(filterConfig.getInitParameter("mappedHeaders"));
        mappedParameters = Boolean.valueOf(filterConfig.getInitParameter("mappedParameters"));
        //getLocalIp  
        localIp = getLocalIp();
    }
    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !MDCConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(MDCConstants.SPLIT)!=-1 ){
                ip = ip.split(MDCConstants.SPLIT)[0];
            }
        }
        if (ip == null || ip.length() == 0 || MDCConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || MDCConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || MDCConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || MDCConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || MDCConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || MDCConstants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    private String getLocalIp() {
        try {
            //一个主机有多个网络接口  
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = netInterfaces.nextElement();
                //每个网络接口,都会有多个"网络地址",比如一定会有loopback地址,会有siteLocal地址等.以及IPV4或者IPV6    .  
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    //get only :172.*,192.*,10.*  
                    if (address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
                        return address.getHostAddress();
                    }
                }
            }
        }catch (Exception e) {
            logger.warn("获取ip失败",e.getMessage());
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsr = (HttpServletRequest)request;
        try {
            mdc(hsr);
        } catch (Exception e) {
            logger.error("设置MDC失败",e.getMessage());
        }
        try {
            chain.doFilter(request,response);
        } finally {
            MDC.clear();//must be,threadLocal  
        }

    }

    private void mdc(HttpServletRequest hsr) {
        MDC.put(MDCConstants.CLIENT_IP_KEY,this.getClientIp(hsr));
        MDC.put(MDCConstants.LOCAL_IP_MDC_KEY,localIp);
        MDC.put(MDCConstants.REQUEST_ID_MDC_KEY,hsr.getHeader(MDCConstants.REQUEST_ID_HEADER));
        String requestSeq = hsr.getHeader(MDCConstants.REQUEST_SEQ_HEADER);
        if(requestSeq != null) {
            String nextSeq = requestSeq + "0";
            //seq will be like:000,real seq is the number of "0"
            MDC.put(MDCConstants.NEXT_REQUEST_SEQ_MDC_KEY,nextSeq);
        }else {
            MDC.put(MDCConstants.NEXT_REQUEST_SEQ_MDC_KEY,"0");
        }
        MDC.put(MDCConstants.REQUEST_SEQ_MDC_KEY,requestSeq);
        MDC.put(MDCConstants.TIMESTAMP,"" + System.currentTimeMillis());
        MDC.put(MDCConstants.URI_MDC_KEY,hsr.getRequestURI());

        if(mappedHeaders) {
            Enumeration<String> e = hsr.getHeaderNames();
            if(e != null) {
                while (e.hasMoreElements()) {
                    String header = e.nextElement();
                    String value = hsr.getHeader(header);
                    MDC.put(MDCConstants.HEADER_KEY_PREFIX + header, value);
                }

            }
        }

        if(mappedCookies) {
            Cookie[] cookies = hsr.getCookies();
            if(cookies != null && cookies.length > 0) {
                for(Cookie  cookie : cookies) {
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    MDC.put(MDCConstants.COOKIE_KEY_PREFIX + name,value);
                }
            }
        }

        if(mappedParameters) {
            Enumeration<String> e = hsr.getParameterNames();
            if(e != null) {
                while (e.hasMoreElements()) {
                    String key = e.nextElement();
                    String value = hsr.getParameter(key);
                    MDC.put(MDCConstants.PARAMETER_KEY_PREFIX + key,value);
                }
            }
        }
        try {
            AccountEntity accountEntity = AccountUtils.getCurrentHr();
            MDC.put(MDCConstants.USERNAME_ID,accountEntity.getUsername()+"--"+accountEntity.getId());
        }catch (Exception e){
            logger.warn("NOT_LOGIN--NULL",e.getMessage());
            MDC.put(MDCConstants.USERNAME_ID,"NOT_LOGIN--NULL");
        }

    }
    @Override
    public void destroy() {
    }

    public HttpRequestMDCFilter() {
    }

    public boolean isMappedCookies() {
        return mappedCookies;
    }

    public void setMappedCookies(boolean mappedCookies) {
        this.mappedCookies = mappedCookies;
    }

    public boolean isMappedHeaders() {
        return mappedHeaders;
    }

    public void setMappedHeaders(boolean mappedHeaders) {
        this.mappedHeaders = mappedHeaders;
    }

    public boolean isMappedParameters() {
        return mappedParameters;
    }

    public void setMappedParameters(boolean mappedParameters) {
        this.mappedParameters = mappedParameters;
    }
}