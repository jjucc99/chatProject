package com.chat.socket.security;

import com.chat.socket.jwt.FormLoginFilter;
import com.chat.socket.jwt.JwtAuthorizationFilter;
import com.chat.socket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Bean   // 비밀번호 암호화
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override // Bean 에 등록
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 정적 자원에 대해서는 Security 설정을 적용하지 않음.
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                // api 요청 접근허용
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("**").permitAll()
                .antMatchers("/").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/contents").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/reply/**").permitAll()

                // 그 외 모든 요청허용
                .anyRequest().permitAll()
                .and()
//                 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.

//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()

                .addFilterBefore(new FormLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(), userRepository), UsernamePasswordAuthenticationFilter.class)
                ;
    }
   @Bean
    public CorsConfigurationSource corsConfigurationSource() {
//       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//       CorsConfiguration config = new CorsConfiguration();
//
//       config.setAllowCredentials(true);
//       config.addAllowedOriginPattern("*"); // addAllowedOriginPattern("*") 대신 사용
//       config.addAllowedHeader("*");
//       config.addAllowedMethod("*");
//       source.registerCorsConfiguration("/**", config);
//       return source;

       // 수정 필요
       //  configuration.addAllowedOriginPattern("");
       // configuration.addAllowedOrigin("프론트 주소"); // 배포 시

       CorsConfiguration configuration = new CorsConfiguration();
       configuration.addAllowedOrigin("http://localhost:3000");
       configuration.setAllowedMethods(Arrays.asList("*"));
       configuration.setAllowedHeaders(Arrays.asList("*"));
       configuration.addExposedHeader("Authorization");
       configuration.setAllowCredentials(true) ;

       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", configuration);
       return source;
    }
}