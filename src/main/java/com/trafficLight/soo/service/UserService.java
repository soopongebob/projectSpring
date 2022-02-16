package com.trafficLight.soo.service;

import com.trafficLight.soo.entity.User;
import com.trafficLight.soo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signUp(User user){
        userRepository.save(user);
    }

}
