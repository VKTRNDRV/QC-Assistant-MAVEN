package com.example.qcassistantmaven.config;

import com.example.qcassistantmaven.util.interceptor.AdminAbsencePreventionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final AdminAbsencePreventionInterceptor adminAbsencePreventionInterceptor;

    @Autowired
    public WebConfiguration(AdminAbsencePreventionInterceptor adminAbsencePreventionInterceptor) {
        this.adminAbsencePreventionInterceptor = adminAbsencePreventionInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAbsencePreventionInterceptor);
    }
}
