package com.amondel.checklist

import grails.gorm.transactions.Transactional

@Transactional
class RequestService {

    String getCurrentUrl(request) {
        boolean includePort = true;
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = (new org.springframework.security.web.PortResolverImpl()).getServerPort(request)
        String contextPath = request.getContextPath();
        boolean inHttp = "http".equals(scheme.toLowerCase());
        boolean inHttps = "https".equals(scheme.toLowerCase());

        if (inHttp && (serverPort == 80)) {
            includePort = false;
        } else if (inHttps && (serverPort == 443)) {
            includePort = false;
        }
        scheme + "://" + serverName + ((includePort) ? (":" + serverPort) : "") + contextPath;

    }
}
