package com.manukov.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService detailsService;

    private final SuccessUserHandler successUserHandler;

    public SecurityConfig(UserDetailsService detailsService, SuccessUserHandler successUserHandler) {
        this.detailsService = detailsService;
        this.successUserHandler = successUserHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                    .antMatchers("/")       //страница "/" доступна всем и не требует аутенитфикации
                    .permitAll()
                    .and()

                .authorizeRequests()
                    .antMatchers("/user/**").access("hasRole('USER')")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()

                .formLogin()                                //используем форму аутентификации Spring
                    //.loginPage("/login")                    //вместо формы Spring подставляем URL своей страницы
                    //.permitAll()                            //доступна всем
                    //.usernameParameter("root")
                    //.passwordParameter("root")
                    .successHandler(successUserHandler)     //обработка успешного входа
                    .and()

                .logout()
                    //.logoutUrl("/logout")
                    .permitAll();                           //разлогиниться могут все

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(detailsService)         //передаем параметром бин UserDetailServiceImpl
                .passwordEncoder(passwordEncoder());
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN");                //.roles("USER", "ADMIN");
//    }

}
