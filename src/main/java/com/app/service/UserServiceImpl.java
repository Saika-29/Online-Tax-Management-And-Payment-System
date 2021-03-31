package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.UserRepository;
import com.app.pojos.User;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User getUserByUserName(String userName) {

		Optional<User> optionalUser = userRepo.findByUserName(userName);

		User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Invalid Username"));

		return user;
	}

	@Override
	public User saveCredentials(User user) {

		String un = user.getUserName();
		String pwd = passwordEncoder.encode(user.getPassword());
		String rl = user.getRole();

		return userRepo.save(new User(un, pwd, rl));
	}

	@Override
	public void removeCredentials(int id) {
		
		userRepo.deleteById(id);
		
	}


}
