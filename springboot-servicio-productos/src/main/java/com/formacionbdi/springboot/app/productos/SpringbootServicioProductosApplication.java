package com.formacionbdi.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//Nucleo del servicio productos, tiene las anotaciones
@EnableEurekaClient //Se habilita como cliente de eureka, esto le premitira ser visible al servidor eureka
@SpringBootApplication //Indica que es un aplicacion de Springboot y se autoconfigurara con lo parametros por defecto.
@EntityScan({"com.formacionbdi.springboot.app.commons.models.entity"})//Importa de la libreria commons, el paquete entity, en el que se encuentra la clase producto.
public class SpringbootServicioProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}

}
