package com.esfe.Asistencia.Seguridad;

import javax.sql.DataSource;
import javax.swing.text.PasswordView;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebMvc
public class DatabaseWebSecurity { 

    @Bean
    public UserDetailsManager customUsers(DataSource dataSource){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select login, clave, status as enabled from usuario where login = ?");
        users.setAuthoritiesByUsernameQuery("select u.login, r.nombre as authority from usuario_rol ur " +
                "inner join usuario u on u.id = ur.usuario_id " +
                "inner join rol r on r.id = ur.rol_id " +
                "where u.login = ?");


        return users;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/assets/**", "/css/**", "/js/**","/images/**","/fonts/**","/error", "/", "/privacy", "/terms").permitAll()
                .requestMatchers("/grupos/**").hasAuthority("admin")
                .requestMatchers("/usuarios/**").hasAuthority("admin")
                .requestMatchers("/docentes/**").hasAuthority("admin")
                .requestMatchers("/asignaciones/**").hasAuthority("admin")
                .requestMatchers("/alumnos/**").hasAnyAuthority("admin", "docente")
                .anyRequest().authenticated()
        );
        http.formLogin(form -> form
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/", true) 
        );
        return http.build();
    }
        return http.build();
    }


    @Bean
    public PasswordView passwordEncoder() {
        return new PasswordView(null);
    }
}
