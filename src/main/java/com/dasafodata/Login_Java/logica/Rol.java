package com.dasafodata.Login_Java.logica;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
/*import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Rol implements Serializable{
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nombreRol;
	private String descripcion;
	
	//Un usuario solo tiene un Rol, pero un Rol puede tener varios usuarios
	@OneToMany(mappedBy = "unRol") //le indicamos con quien mapeamos en Usuario
	private List<Usuario> listaUsuarios;
	
	
	public Rol() {
		super();
	}


	public Rol(int id, String nombreRol, String descripcion, List<Usuario> listaUsuarios) {
		super();
		this.id = id;
		this.nombreRol = nombreRol;
		this.descripcion = descripcion;
		this.listaUsuarios = listaUsuarios;
	}



	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}


	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombreRol() {
		return nombreRol;
	}


	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	

}
