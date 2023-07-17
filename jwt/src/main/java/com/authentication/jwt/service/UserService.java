package com.authentication.jwt.service;

import com.authentication.jwt.models.dto.UserCreationDto;
import com.authentication.jwt.repository.UserRepository;
import com.authentication.jwt.models.entities.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createFromDto(UserCreationDto userCreationDto) {
        User user = new User();
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.map(userCreationDto, user);
        user.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));

        return userRepository.save(user);

    }

    public User findUserByEmail(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("User not exist"));
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByID(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public String deleteUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));

        userRepository.delete(user);

        return "User with ID : " + id + " deleted";
    }

    public User updatePassword(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user){
        User user1 = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setNic(user.getNic());
        user1.setAddressLine1(user.getAddressLine1());
        user1.setAddressLine2(user.getAddressLine2());
        user1.setCity(user.getCity());
        user1.setCountry(user.getCountry());

        return userRepository.save(user1);
    }

}
