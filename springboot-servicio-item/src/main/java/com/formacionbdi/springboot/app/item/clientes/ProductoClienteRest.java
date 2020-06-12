package com.formacionbdi.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;

//Es una interfaz que mapea los metodos para acceder a la clase producto.

@FeignClient(name = "servicio-productos") //Este bean habilita la interfaz como un cliente Feign con el nombre indicado.
public interface ProductoClienteRest {
	
	//@(Post, Get, Delete, put)Mapping("/(nombre de la ruta)"): mapea el metodo, haciendole accesible mediante 
	//la url indicada y se indicara la informacion a intriducir (ya que es un post) en el cuerpo de la pagina con @RequestBody.
	
	@GetMapping("/listar")
	public List<Producto> listar();
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id);
	
	@PostMapping("/crear")
	public Producto crear(@RequestBody Producto producto);
	
	@PutMapping("/editar/{id}")
	public Producto update(@RequestBody Producto producto, @PathVariable Long id);
	
	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable Long id); 
}
