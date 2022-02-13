package com.mercadolibre.mercadolibrecountry.domain.service.util;

import lombok.extern.log4j.Log4j2;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Log4j2
public class IpValidation {

    private IpValidation() {
    }

    public static boolean isPublicIp (String ipAddress){
        boolean isLocalAddress = false;
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            isLocalAddress = inetAddress.isSiteLocalAddress() || ipAddress.startsWith("127.0");
        } catch (UnknownHostException e) {
            log.error("IP", e);
            isLocalAddress = true;
        }

        return !isLocalAddress;
    }
}
