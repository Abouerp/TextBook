//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

package com.it666.textbook.config;
//
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @author Abouerp
// */
//@EnableWebSecurity
public class SpringSecurityConfig  {//extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //定制请求的授权规则
//        http.authorizeRequests().antMatchers("/").permitAll()
//                .antMatchers("/api/teacher/**").hasRole("teacher")
//                .antMatchers("/api/secretary/**").hasRole("secretary");
//
//        http.formLogin().usernameParameter("userName").passwordParameter("userPassword").loginPage("/api/login");
//    }
//
//    /**
//     * 定义认证规则
//     */
//     @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //
//         auth.jdbcAuthentication().withUser("root").password("root").roles("secretary")
//                 .and()
//                 .withUser("admin").password("admin").roles("teacher");
//    }
}
