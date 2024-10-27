package com.mb.assessment.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mb.assessment.application.dao.UserRepository;
import com.mb.assessment.application.entity.Userlogin;

//import com.mb.assessment.application.dao.UserRepository;
//import com.mb.assessment.application.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
   
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Userlogin user = userRepository.findByUsername(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
        return new org.springframework.security.core.userdetails.User(userName, user.getPassword(),
                mapRolesToAuthorities());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities() {
    	List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    	roles.add(new SimpleGrantedAuthority("ADMIN"));
    	return roles;
    }
}
