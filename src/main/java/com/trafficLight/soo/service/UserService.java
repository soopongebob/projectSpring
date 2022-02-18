package com.trafficLight.soo.service;

import com.trafficLight.soo.controller.SignInForm;
import com.trafficLight.soo.entity.User;
import com.trafficLight.soo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String signUp(User user){
        String result = checkIdDuplicate(user.getUserId());
        if(result == null) {        //결과가 null이면 성공
            userRepository.save(user);
        }
        return result;
    }

    //중복확인
    public String checkIdDuplicate(String userId){
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isEmpty()){     //검색결과가 empty이면 중복되지 않음(성공)
            return null;
        }else{
            return userId;
        }
    }

    //로그인 확인
    public String checkIdAndPwd(SignInForm signInForm){
        Optional<User> user = userRepository.findByUserIdAndPassword(signInForm.getUserId(), signInForm.getPassword());
        if(user.isEmpty()){ //결과가 없으면, 로그인 실패
            return null;
        }else{
            return signInForm.getUserId();
        }
    }

}
