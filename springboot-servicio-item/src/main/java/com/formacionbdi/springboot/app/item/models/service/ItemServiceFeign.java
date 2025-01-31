package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.item.clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.models.Item;

//Esta clase implementa la interfaz ItemService y nos permite mediante un objeto de la
//interaz ProductoClienteRest e ItemService acceder y gestionar los datos de la BDD.
@Service("serviceFeign")//Nos indica que es un servicioFeign.
@Primary
public class ItemServiceFeign implements ItemService {

	@Autowired //Inyecta los datos de la interfaz ProductoClienteRest.
	private ProductoClienteRest clienteFeign;
	@Override
	public List<Item> findAll() {
		
		return clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		
		return new Item(clienteFeign.detalle(id),cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		return clienteFeign.crear(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {
		
		return clienteFeign.update(producto, id);
	}

	@Override
	public void delete(Long id) {
		clienteFeign.eliminar(id);
		
	}

}
