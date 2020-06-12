package com.formacionbdi.springboot.app.oauth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{ //La clase extendida define las propiedades del servidor
	
	@Autowired
	private Environment env;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;//Controlador de autentificaciones
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {//Indica los permisos que van a tener nuestros endpoints del servidor de autorizacion para generar y validar el token
		security.tokenKeyAccess("permitAll()")//tokenKeyAccess es el endpoint para generar el token. Permite que cualquiera pueda acceder a la ruta para generar un token.
		.checkTokenAccess("isAuthenticated()");//Valida el token
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {//Configura los clientes 
		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id"))//Guardamos la aplicacion cliente obteniendo su id del config server
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))//guardamos la llave secreta de la aplicacion indicada en el config server
		.scopes("read","write") // Permiso de la app cliente, leer y escribir
		.authorizedGrantTypes("password", "refresh_token")//Indica como vamos a obtener el token, en nuestro caso con password, y tambien un token que nos permite obtener otro token
		//de acceso para cuando caduque el anterior.
		.accessTokenValiditySeconds(3600)//Indica que el token tendra una duracion de 3600 segundos antes de caducar.
		.refreshTokenValiditySeconds(3600);//Indica que el refresh token tendra tambien 3600 segundos antes de caducar.

	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {//recive el endpoint de oauth que genera el token
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
		//retorna un token JSON con todos los datos introducidos
		
	}
	@Bean
	public JwtTokenStore tokenStore() {//Aqui se guarda almacena los identificadores del servidor de autentificacion va suministrando.
		
		return new JwtTokenStore(accessTokenConverter());// Para crear y almacenar el token hay que convertir el token en jwt con todos sus datos.
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {//Guarda datos(username, password, roles etc.. en el token)
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));//Asigna la llave de la firma al token para validarlo en el servidor de recursos
		
		return tokenConverter;
	}
	
}
