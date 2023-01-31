package com.telmomanique.trabalhofinal.TheLanguageFinder.security;

import com.telmomanique.trabalhofinal.TheLanguageFinder.service.ClienteDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    ClienteDetailService clienteDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.inMemoryAuthentication()
                .withUser("admin")
                    .password( getPasswordEncoder().encode("admin"))
                    .roles("ADMIN")
                .and()
                .withUser("cliente")
                    .password(getPasswordEncoder().encode("cliente"))
                    .roles("CLIENTE");

         auth.userDetailsService(clienteDetailService)
                 .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/").permitAll();


        http.authorizeHttpRequests()
                .antMatchers("/cliente").hasAnyRole("ROLE_CLIENTE","ROLE_ADMIN")
                .antMatchers("/admin").hasRole("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .and()
                .logout().permitAll()
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
