package com.mars.service;

import com.mars.models.Login;

public interface LoginService {
	Login doLogin(Login login);
	void sendEmail(String userName, String contextPathUrl);
}
