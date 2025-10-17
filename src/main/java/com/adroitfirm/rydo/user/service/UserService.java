package com.adroitfirm.rydo.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.adroitfirm.rydo.user.dto.UserDto;
import com.adroitfirm.rydo.user.entity.User;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import com.adroitfirm.rydo.user.mapper.UserMapper;
import com.adroitfirm.rydo.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserDto create(UserDto userDto) {
    	User persist = userRepository.save(mapper.toEntity(userDto)); 
    	return mapper.toDto(persist); 
    }

    public List<UserDto> findAll() {
    	List<User> users = userRepository.findAll();
    	return users.stream().map(mapper::toDto).collect(Collectors.toList()); 
    }

    public UserDto findById(Long id) { 
    	User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    	return mapper.toDto(user);  
    }

    public UserDto findByEmail(String email) {
    	User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
    	return mapper.toDto(user);
    }
    
    public UserDto findByPhone(String phone) { 
    	User user = userRepository.findByPhone(phone).orElseThrow(() -> new ResourceNotFoundException("User not found: " + phone));
    	return mapper.toDto(user);
    }

    public void delete(Long id) { 
    	userRepository.deleteById(id); 
    }
}