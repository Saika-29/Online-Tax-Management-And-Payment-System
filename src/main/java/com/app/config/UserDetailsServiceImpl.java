package com.app.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.dao.UserRepository;
import com.app.pojos.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public UserDetailsServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		// fetching user from database

		Optional<User> optionalUser = userRepository.findByUserName(userName);

		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Not Found : " + userName));

		System.out.println(optionalUser);

		return optionalUser.map(MyUserDetails::new).get();
	}

}
