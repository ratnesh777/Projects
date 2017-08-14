/* Copyright (C) 2016 IPC Systems, Inc. All rights reserved. */

package com.mars;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    @Override
    protected WebApplicationContext createRootApplicationContext()
    {
        WebApplicationContext context = (WebApplicationContext) super.createRootApplicationContext();
        return context;
    }

    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[] { AppConfig.class, WebMvcConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return null;
    }

    @Override
    protected String[] getServletMappings()
    {
        return new String[] { "/" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        super.onStartup(servletContext);
        servletContext.addListener(new SessionListener());
    }
}
