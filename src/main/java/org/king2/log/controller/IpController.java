package org.king2.log.controller;

import org.king2.log.annotation.AccessShow;
import org.king2.log.utils.AddressUtils;
import org.king2.log.utils.LocalSystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 描述:
 * @author 刘梓江
 * @Date 2021/3/22 13:22
 */
@RestController
@RequestMapping("/ip")
public class IpController {

    private static final Logger log= LoggerFactory.getLogger(IpController.class);

    @Autowired
    private HttpServletRequest request;


    @AccessShow
    @GetMapping("/info")
    public Object test() throws IOException {
        log.info("本机外网IP:"+ LocalSystemUtil.INTERNET_IP);
        log.info("本机内网IP:"+ LocalSystemUtil.INTRANET_IP);
        log.info("本机区域信息:"+ AddressUtils.areaInfoByIp(LocalSystemUtil.INTERNET_IP));

        String requestIp = AddressUtils.getHostIpAddress(request);
        String requestInternetIp=(AddressUtils.isInternalIp(requestIp)?LocalSystemUtil.INTERNET_IP:requestIp);
        log.info("请求IP:"+ requestIp);
        log.info("请求外网IP:"+ requestInternetIp);
        log.info("请求区域信息:"+ AddressUtils.areaInfoByIp(requestIp));
        return "请求IP:"+ requestIp+"==========请求外网IP:"+requestInternetIp+"==========请求区域信息:"+AddressUtils.areaInfoByIp(requestIp);
    }
}
