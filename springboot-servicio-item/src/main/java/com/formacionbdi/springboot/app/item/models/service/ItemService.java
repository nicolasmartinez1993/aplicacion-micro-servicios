package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.item.models.Item;

//Esta interfaz nos provee de los metodos necesarios para buscar actualizar o borrar un producto.
public interface ItemService {
	
	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
	
	
	
	public Producto save(Producto producto);
	
	public Producto update(Producto producto, Long id);
	
	void delete(Long id);
		
}
