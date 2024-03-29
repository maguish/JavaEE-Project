package services;

import dto.UserRegistrationDto;
import models.UserEntity;

public interface UserService {
	
	void save(UserRegistrationDto userRegist, String roleName);
	
	UserEntity findByEmail(String email);
	
	UserEntity findByUsername(String username);

}
