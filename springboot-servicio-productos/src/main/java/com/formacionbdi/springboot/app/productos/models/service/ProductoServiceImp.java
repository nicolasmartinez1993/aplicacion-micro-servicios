package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.dao.ProductoDao;

//Esta clase implementa el interfaz IproductoService y usa un objeto con la 
//interfaz de ProductoDao para realizar las acciones inidcadas con la BDD.
@Service
public class ProductoServiceImp implements IProductoService{

	@Autowired //Este bean busca en el contenedor spring un contructor o metodo con el nombre indicado para inyectar los datos.
	private ProductoDao productoDao;
	
	@Override
	@Transactional(readOnly = true)//indica que es una transaccion con la base de datos. En este caso solo permite leer.
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {
		
		return productoDao.save(producto);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productoDao.deleteById(id);
		
	}

}
