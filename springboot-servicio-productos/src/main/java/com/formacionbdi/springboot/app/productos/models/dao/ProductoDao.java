package com.formacionbdi.springboot.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;



//Esta clase es una interfaz, por lo que solo puede ser usada por otras clases que la implementen.
//Extiende de CrudRepository<Producto, Long>, por lo que obtiene sus metodos etc…
public interface ProductoDao extends CrudRepository<Producto, Long>{

}
