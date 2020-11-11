package com.chiang.spring.scope;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

//@Scope(value = "request" , proxyMode = ScopedProxyMode.INTERFACES)
@Component
public class SessionTestBean {
    private Integer count = 0;


    public Integer getCount() {
        count++;
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

//    @Scope(value = "request" , proxyMode = ScopedProxyMode.INTERFACES)
//    @Bean
//    public SessionTestBean getSessionBean(){
//        return new SessionTestBean();
//    }
}