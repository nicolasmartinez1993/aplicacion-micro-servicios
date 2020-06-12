package com.formacionbdi.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

//s un servicio Rest. Esta clase es un controlador de los productos
//Lo que permite manejar los productos que tenemos mediante el mapeo de los metodos con las peticiones http(GET, POST, DELETE, PUT), por lo que funciona por url.
//RestController hace que cada método de manejo de solicitudes de la clase de controlador serializa automáticamente los objetos devueltos en HttpResponse.
@RestController //Indica que es un controlador REST, queire decir que incluye  los bean @Controler y @ResponseBody. Controller indica que se que se crea un bean en 
//el contenedor de spring y @responsebody indica que devuelve directamente un objeto serializado en JSON por una httpResponse.
public class ProductoController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/listar")// Mapeo del metodo mediante GET, indica que solo quiere solicitar datos.
	public List<Producto> listar(){
		return productoService.findAll().stream().map(producto ->{
			producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			return producto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")//Mapeo mediante GET, indica mediante @PathVariable el id 
	//en la url los datos del producto especificado
	public Producto detalle(@PathVariable Long id) {
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		
		return productoService.findById(id);
		
		
		
	}
	@PostMapping("/crear") //Mediante POST crea un producto con los datos especificados en @RequestBody
	//que corresponde al cuerpo de la app.
	@ResponseStatus(HttpStatus.CREATED) //Es el codigo de respuesta por parte del servidor para indicar el estado de la peticion.
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);
		
	}
	
	@PutMapping("/editar/{id}") //PUT actualiza un dato ya exitente, en este caso especificando la variable id
	//con PathVariable, y los datos a cambiar en el cuerpo con @RequestBody
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto,@PathVariable Long id) {
		Producto productoDb = productoService.findById(id);
		
		productoDb.setNombre(producto.getNombre());
		productoDb.setPrecio(producto.getPrecio());
		
		return productoService.save(productoDb);
	}
	@DeleteMapping("/eliminar/{id}") // Elimina con DELETE con el id especificdo en la url
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.deleteById(id);
		
	}
}
