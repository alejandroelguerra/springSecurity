package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.objetos.Rol;
import com.example.demo.objetos.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;


@Transactional
@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

	@Autowired
	private UsuarioRepository dao;

	@Autowired
	private RolRepository rolDao;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Usuario guardar(Usuario usuario) {
		usuario.anadirRol(rolDao.findById(Byte.parseByte("2")).get());
		usuario.setContrasenna(bCryptPasswordEncoder.encode(usuario.getContrasenna()));
			
		return dao.save(usuario);
	}
	
	@Override
	public Usuario actualizar(Usuario usuario) {
		return dao.save(usuario);
	}
	
	@Override
	public Usuario borrarPorId(long id) {
		Optional<Usuario> optional =dao.findById(id);
		
		if (optional.isPresent()) {
			
			dao.delete(optional.get());

		}

		return optional.orElse(new Usuario());
	}

	@Override
	public ArrayList<Usuario> listarTodos() {
		return (ArrayList<Usuario>) dao.findAll();
	}
	@Override
	public Usuario buscarPorId(long id) {
		Optional<Usuario> optional = dao.findById(id);

		return optional.orElse(new Usuario());
	}
	@Override
	public Usuario buscarPorNombre(String nombre) {
		Optional<Usuario> optional = dao.findByNombre(nombre);

		return optional.orElse(new Usuario());
	}

	@Override
	public void asignarRolCliente(String usuario, byte rol) {
		Usuario completa = dao.findByNombre(usuario).get();

		Rol elRol = rolDao.findById((Byte) rol).get();

		completa.anadirRol(elRol);

		elRol.getUsuarios().add(completa);

		dao.save(completa);
		rolDao.save(elRol);
		
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario u = dao.findByNombre(username).get();
		Set<Rol> l = u.getRoles();
		Set<Rol> roles = new HashSet<>();

		l.stream().forEach(roles::add);

		u.setRoles(roles);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		for (Rol rol : u.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(rol.getNombre()));
		}

		return new org.springframework.security.core.userdetails.User(u.getNombre(), u.getContrasenna(),
				grantedAuthorities);
	}


	

}
