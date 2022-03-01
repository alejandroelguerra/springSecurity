package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.objetos.Usuario;


public interface IUsuarioService {
	
	public Usuario guardar(Usuario usuario);
	
	public Usuario actualizar(Usuario usuario);
	
	public Usuario borrarPorId(long id);
	
	public ArrayList<Usuario> listarTodos();
	
	public Usuario buscarPorId(long id);
	
	public Usuario buscarPorNombre(String nombre);
	
	
	
	public void asignarRolCliente(String usuario, byte rol);
}

