package com.trafficLight.soo.service;

import com.trafficLight.soo.controller.SignInForm;
import com.trafficLight.soo.controller.SignUpForm;
import com.trafficLight.soo.entity.Role;
import com.trafficLight.soo.entity.User;
import com.trafficLight.soo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

//    private final AuthenticationManager authenticationManager;

//    public String signUp(User user){
//        String result = checkIdDuplicate(user.getUserId());
//        if(result == null) {        //결과가 null이면 성공
//            userRepository.save(user);
//        }
//        return result;
//    }
//
//    //중복확인
//    public String checkIdDuplicate(String userId){
//        Optional<User> user = userRepository.findByUserId(userId);
//        if(user.isEmpty()){     //검색결과가 empty이면 중복되지 않음(성공)
//            return null;
//        }else{
//            return userId;
//        }
//    }

    public String signUp(SignUpForm signUpForm){

        System.out.println("회원가입 저장" +  signUpForm.getEmail());
        System.out.println("회원가입 저장" + signUpForm.getAuth());
        String uuid = UUID.randomUUID().toString();
        User saveUser = userRepository.save(
                User.builder()
                        .uuid(uuid)
                        .userId(signUpForm.getUserId())
                        .username(signUpForm.getUserName())
                        .password(signUpForm.getPassword())
                        .email(signUpForm.getEmail())
                        .auth(signUpForm.getAuth())
                        .build());
        return saveUser.getUserId();
    }

//    //로그인 확인
//    public String signIn(SignInForm signInForm){
//
//        System.out.println("로그인 확인");
//        System.out.println("signInForm = " + signInForm.getUserId());
//        System.out.println("signInForm = " + signInForm.getPassword());
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        signInForm.setPassword(encoder.encode(signInForm.getPassword()));
//
//        System.out.println("encoding password = " + signInForm.getPassword());
//
//        //client에서 로그인 요청이 오면, UsernamePasswordAuthenticationFilter에서
//        //ID+password를 담은 인증객체 Authentication을 생성함
//        //이 객체를 AuthenticationManager에게 넘겨 인증처리를 위임함
//        //provider를 담은 List
//
//        //인증관리자(authenticationManager는 적절한 provider(authenticationProvider)에 인증 위임
//        //해당 provider는 input정보(id, password)를 가지고 실제 인증 처리 역할을 함
//        /*
//        1. loadUserByUsername(username)메서드를 호출해 유저객체를 요청
//        2. UserDetailService인터페이스에게 loadUserByUsername(username)요청
//           2-1. repository에 findById()메서드로 유저 객체 조회
//           2-2. 만약 존재하지 않으면, usernameNotFoundException 발생, usernamePasswordAuthenticationFilter에서 예외처리
//           -> failHandler()에서 후속처리
//           2-2. 존재하면 UserDetails 타입으로 반환됨
//         */
//        //인증관리자(AuthenticationManager)는 이제 password 검증을 시작함
//        // - 일치하지 않을 경우 badcredentialException 발생 후 인증 실패
//        //성공한 인증객체 Userdetails와 authorities를 담은 인증 후 토큰 객체 authentication를
//        //UsernamePasswordAuthenticationFilter에 전달
//        //SecurityContext에 저장
//        // 이후 전역적으로 securityContextHolder에서 인증 객체를 사용가능하게 됨
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        signInForm.getUserId(),
//                        signInForm.getPassword()
//                )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        User principal = (User) authentication.getPrincipal();
//
//        System.out.println("principal = " + principal);
//        System.out.println("principal.getUserId() = " + principal.getUserId());
//
//        return principal.getUsername();
//    }

    /**
     * 상세정보 security 필수 메서드
     * @param userId
     * @return 사용자의 계정 정보와 권한을 갖는 UserDetails 인터페이스 반환
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //DB에서 객체 조회
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }
        //권한 정보 등록
        Role role = Role.USER;
        System.out.println("role = " + role);

        //accountContext 생성자로 userDetails 타입 생성
        User createUser = User.builder()
                .userId(user.get().getUserId())
                .password(user.get().getPassword())
                .auth(role.toString())
                .build();

        System.out.println("객체 생성 완료 : " + createUser.getUserId());

        return createUser;

    }

    public Optional<User> getUser(String userId){
        Optional<User> user = userRepository.findByUserId(userId);
        return user;
    }
}
