package com.mars;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 */

public class SessionListener implements HttpSessionListener
{

    private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);
    org.apache.logging.log4j.Logger auditLog = LogManager.getLogger("auditLogger");

    @Override
    public void sessionCreated(HttpSessionEvent event)
    {
        event.getSession().setMaxInactiveInterval(15 * 60); // 15 minutes
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event)
    {
        logger.info("Session is destroyed");
        auditLog.info("User logged out successfully");
    }
}
