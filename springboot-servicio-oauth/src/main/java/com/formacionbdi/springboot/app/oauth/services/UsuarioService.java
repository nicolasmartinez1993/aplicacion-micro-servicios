package com.formacionbdi.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.oauth.clients.UsuarioFeignClient;
import com.formacionbdi.springboot.app.usuarios.commons.models.entity.Usuario;

import brave.Tracer;
import feign.FeignException;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {// UserDetailsService es de spring secirity y tiene el metodo loadUserByUsername
	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeignClient client; //Nos permite comunicarnos con el servicio usuarios.
	
	@Autowired
	private Tracer tracer;//Nos permitira agregar informacion sobre el seguimiento.

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//obtiene al usuario por el username mediante un cliente http, basicamente recoge el username y password que emos introducido en postman.
		Usuario usuario = client.findByUsername(username);

		try {//Comprobara si los parametros usuario y contraseña son correctos, si lo son, devolvera una objeto usuario con los datos de acceso.

			List<GrantedAuthority> authorities = usuario.getRoles().stream()//convertimos nuestros tipos roles de usuario a tipo GrantedAuthority, Stream convierte el flujo de datos
					.map(role -> new SimpleGrantedAuthority(role.getNombre()))// mapeamos el flujo en roles de tipo SimpleGrantedAuthority
					.peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());
			log.info("Usuario autentificado: " + username);
			
			return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
					authorities);
		} catch (FeignException e) {//Sino existe devuelve un mensaje de error.
			String error="Error en el login, no existe el usuario'" + username + "' en el sistema";
			log.error(error);
			
			tracer.currentSpan().tag("error.mensaje", error + ": "+e.getMessage());
			throw new UsernameNotFoundException(
					"Error en el login, no existe el usuario'" + username + "' en el sistema");
		}
	}

	@Override
	public Usuario findByUsername(String username) {

		return client.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {

		return client.update(usuario, id);
	}

}
