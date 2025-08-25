package com.esfe.Asistencia.Seguridad;

import javax.sql.DataSource;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.provisioning.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;


@Configuration
@EnableWebSecurity

public class DatabaseWebSecurity {

    @Bean
public UserDetailsManager customUser(DataSource dataSource) {
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    users.setUsersByUsernameQuery("SELECT login, clave,status as enabled FROM usuario WHERE login = ?");
    users.setAuthoritiesByUsernameQuery("SELECT u.login, r.nombre as authority FROM usuario_rol ur " +
            "INNER JOIN usuario u ON u.id = ur.usuario_id " +
            "INNER JOIN rol r ON r.id = ur.rol_id " +
            "WHERE u.login = ?");
    
    return users;
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests
        (authorize -> authorize
        .requestMatchers("/assets/**" , "/css/**", "/js/**","/images/**","/fonts/**","/error/**","/","/privacy/**","/terms/**").permitAll()
        .requestMatchers("/grupos/**").hasAuthority("admin")
        .requestMatchers("/usuarios/**").hasAuthority("admin")
        .requestMatchers("/docentes/**").hasAuthority("admin")
        .requestMatchers("/asignaciones/**").hasAuthority("admin")
        .requestMatchers("/alumnos/**").hasAnyAuthority("admin","docente")
            .anyRequest().authenticated()
        );

     http.formLogin(form -> form
        .loginPage("/login").permitAll()
        .defaultSuccessUrl("/", true)
    );
    return http.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

}