package com.example.demo.objetos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	// variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@Column(name="nombre_usuario")
	private String nombre;
	@Column (name="contrasena")
	private String contrasenna;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "id_usuario", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_rol", nullable = false))
	private Set<Rol> roles=new HashSet<>();
	
	public Usuario() {
		this(-1L,"","");
	}
	public Usuario(Long id, String nombre, String contrasenna) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.contrasenna = contrasenna;
	}
	
	public Usuario(String nombre, String contrasenna) {
		super();
		this.nombre = nombre;
		this.contrasenna = contrasenna;
	}
	
	
	public Usuario(Long id, String nombre, String contrasenna, Set<Rol> roles) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.contrasenna = contrasenna;
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasenna() {
		return contrasenna;
	}
	public void setContrasenna(String contrasenna) {
		this.contrasenna = contrasenna;
	}
	public Set<Rol> getRoles() {
		return roles;
	}
	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	public boolean anadirRol(Rol rol) {
		rol.getUsuarios().add(this);
		return getRoles().add(rol);
	}

	public void eliminarRol(Rol rol) {
		this.roles.remove(rol);
		rol.getUsuarios().remove(this);
	}

	
}
