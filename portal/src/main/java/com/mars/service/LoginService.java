package com.mars.service;

import com.mars.models.Login;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 */

public interface LoginService {
	Login doLogin(Login login);
	void sendEmail(String userName, String contextPathUrl);
}
