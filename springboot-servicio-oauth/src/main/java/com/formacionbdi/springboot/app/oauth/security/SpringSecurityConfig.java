package com.formacionbdi.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {//Permite el acceso a nuestra pagina

	@Autowired
	private AuthenticationEventPublisher eventPublisher;
	
	@Autowired
	private UserDetailsService usuarioService; //Inyecta nuestro UserDetailsService para obtener su username etc...
	//que tenga la etiqueta @Service y coincida este.

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {//Registramos el usuarioService en el objeto auth
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder())//Registramos el usuarioService en el objeto auth, y encriptamos la contraseña cuando se logee el cliente applicacion.
		.and().authenticationEventPublisher(eventPublisher);//Gestiona el eventPublisher, si a fallado, devolvera una falla de autentificacion, si  a tenido exito devolvera un objeto eventpublisher el cual podremos personalizar.
		
		
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {//Autentifica la peticion bajo los parametro configurados en auth del AuthenticationManagerBuilder.
		
		return super.authenticationManager(); //Devuelve un objeto totalmente autenticado con las credenciales, y duelve un AuthenticationException si falla.
	}
	
	
	
	
}
