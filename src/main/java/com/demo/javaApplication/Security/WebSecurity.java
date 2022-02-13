package com.demo.javaApplication.Security;

import com.demo.javaApplication.Service.UserService;
import com.demo.javaApplication.Shared.SecurityConstants;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    /*This is the main class for security, specify which algo used to encrpyt password in DB
    * and a UserService class of spring.
    * All http requests and responses goes through the filters specified in configure method.
    * All authentication and authorization filters are imported and used here.
    *  */
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_URL).permitAll()
            .antMatchers("/").permitAll()
            .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
            .antMatchers(HttpMethod.POST, SecurityConstants.FORGOT_PASSWORD_URL).permitAll()
            .antMatchers(HttpMethod.POST, SecurityConstants.RESET_PASSWORD_URL).permitAll()
            .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL).permitAll()
            .antMatchers(HttpMethod.GET, SecurityConstants.VERIFY_EMAIL_HTML_URL).permitAll()
            .antMatchers(HttpMethod.GET, SecurityConstants.RESET_PASSWORD_HTML_URL).permitAll()
            .anyRequest()
            .authenticated().and()
            .addFilter(getAuthenticationFilter())
            .addFilter(getAuthorizationFilter())
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override //need to provide our user service class so spring can load user by username, along with the password encode algo.
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

    public AuthorizationFilter getAuthorizationFilter() throws Exception {
        final AuthorizationFilter filter = new AuthorizationFilter(authenticationManager());
        return filter;
    }


}
