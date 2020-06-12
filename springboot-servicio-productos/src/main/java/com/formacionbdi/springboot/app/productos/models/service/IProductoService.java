package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;

//Esta clase es una interfaz que implementa los siguientes metodos
//findAll, buscar todos los porductos, findById que es buscar el producto por id, 
//save guarda el producto y deleteByid borra el objeto indicando su id.
public interface IProductoService {
	
	public List<Producto>findAll();
	public Producto findById(Long id);
	
	public Producto save(Producto producto);
	
	public void deleteById(Long id);

}
