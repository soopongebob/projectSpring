package com.trafficLight.soo.config;

import com.trafficLight.soo.controller.SignInForm;
import com.trafficLight.soo.entity.User;
import com.trafficLight.soo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity      //웹 보안 활성화
@AllArgsConstructor     //기본생성자
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    /**
     * 임시 인증정보
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("auth.authenticationProvider 실행");
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        PasswordEncoder passwordEncoder = passwordEncoder();
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                System.out.println("provider 실행");
                String username = authentication.getName();
                String password = (String) authentication.getCredentials();

                User user = (User) userService.loadUserByUsername(username);

                if (!passwordEncoder.matches(password, user.getPassword())) {
                    System.out.println("비밀번호 오류로 토큰 생성 실패");
                    throw new BadCredentialsException("BadCredentialsException");
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                System.out.println("토큰생성 : " + authenticationToken);
                return authenticationToken;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                System.out.println("return usernamePassword토큰");
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }
    /**
     * 비밀번호 암호화 메서드
     * Factories로 여러 개의 인코더를 선언하여, 상황에 맞게 선택해서 사용
     * 기본 포멧은 {Bcrypt}
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * security 설정
     * @param web FilterChainProxy 생성 필터
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception{
        //spring security 인증 무시 경로 (static)
        //http에서 permitAll()해도 되지만, 이 방법은 보안검사를 한 뒤 통과해주는 반면
        //ignoring을 통해 하면 보안검사 자체를 안하기 때문에 성능적으로 더 권장됨
        web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                );
    }
    /**
     * security 설정
     * @param http 요청에 대한 보안
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()       //csrf 인증 disable
            .authorizeRequests()    //요청 권한 검사 시작
                //페이지 권한
                .antMatchers("/**").permitAll()
                .antMatchers("/users/myPage").hasRole("USER")
                .and()
                .formLogin()                        //form login 인증
                .usernameParameter("userId")        //아이디 파라미터명 설정
                .passwordParameter("password")      //패스워드 파라미터명 설정
                .loginPage("/users/signIn")         // 사용자 정의 로그인 페이지
                .loginProcessingUrl("/users/signIn")
                .failureUrl("/users/signInFail")    //로그인 실패 시 이동 페이지
                .defaultSuccessUrl("/")             //로그인 성공 후 이동 페이지
//                .successHandler(new AuthenticationSuccessHandler() {
//                                    @Override
//                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                                        System.out.println("authentication::" + authentication.getName());
//                                        response.sendRedirect("/");
//                                    }
//                                }
//                )
//                .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                        System.out.println("exception::" + exception.getMessage());
//                        response.sendRedirect("/users/login");
//                    }
//                })  //로그인 실패 후 핸들러
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)        //세션 초기화
                .deleteCookies("JSESSIONID", "remember-me");        //쿠키 제거
//            .and()        //예외처리 핸들링
//                .exceptionHandling().accessDeniedPage("/denied");
    }

//    /**
//     * Security 인증
//     * AuthenticationManagerBuilder를 이용해 AuthenticationManager 생성
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }
//
//    /**
//     * authentication manager
//     * @return
//     */
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception{
//        return super.authenticationManagerBean();
//    }

}
