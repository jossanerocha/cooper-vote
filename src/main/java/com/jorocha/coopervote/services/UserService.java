package com.jorocha.coopervote.services;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jorocha.coopervote.domain.User;
import com.jorocha.coopervote.dto.UserDTO;
import com.jorocha.coopervote.repository.UserRepository;
import com.jorocha.coopervote.services.exception.LoginException;

@Service
public class UserService implements UserDetailsService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	/**
	 * Retorna uma lista de usuarios
	 *
	 * @param 
	 * @return List<User>
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	/**
	 * Retorna um usuario logado
	 *
	 * @param username
	 */
	public UserDTO logar(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, password);
		if (user == null) {
			throw new LoginException("usuario nao encontrado");
		}		
		return new UserDTO(user);
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Collections.emptyList());
	}
}