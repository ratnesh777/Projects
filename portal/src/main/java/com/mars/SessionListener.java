package com.mars;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {

	private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(15 * 60); // 15 minutes
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		logger.info("Session is destroyed");
	}
}
