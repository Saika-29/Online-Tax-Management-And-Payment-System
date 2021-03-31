package com.app.service;

import com.app.pojos.User;

public interface IUserService {
	
	User getUserByUserName(String userName);
	
	User saveCredentials(User user);
	
	void removeCredentials(int id);

}
