package com.manukov.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {
                WebConfig.class,
                JPAConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};                  //стартовый url
    }
}
