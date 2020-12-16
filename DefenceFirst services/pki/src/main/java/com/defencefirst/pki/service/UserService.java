package com.defencefirst.pki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.defencefirst.pki.dto.UserUpdateDTO;
import com.defencefirst.pki.exceptions.SavingException;
import com.defencefirst.pki.exceptions.UserNotFoundException;
import com.defencefirst.pki.model.User;
import com.defencefirst.pki.repository.UserRepository;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	

	public User findByUsername(String username)
			throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}


	public User findById(Long id) throws UserNotFoundException {
		User u = userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("User not found!"));

		return u;
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}

	public User save(User user) throws SavingException {
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			throw new SavingException("Couldn't save user. Username or email is taken!");
		}
	}






	public User update(UserUpdateDTO userDetails) throws UserNotFoundException, SavingException {
		User user = findById(userDetails.getId());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setPhoneNumber(userDetails.getPhoneNumber());
		user.setUsername(userDetails.getUsername());
		user.setEmail(userDetails.getEmail());
		User updateUser = save(user); 
		return updateUser;
	}
}
