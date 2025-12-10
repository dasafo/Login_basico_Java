package com.dasafodata.Login_Java.persistencia;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dasafodata.Login_Java.logica.Rol;
import com.dasafodata.Login_Java.logica.Usuario;




public class ControladoraPersistencia {
	
	UsuarioJpaController usuJpa = new UsuarioJpaController();
	RolJpaController rolJpa = new RolJpaController(); 

	public List<Usuario> traerUsuarios() {
		
		List<Usuario> listaUsuario = usuJpa.findUsuarioEntities();
		
		return listaUsuario;
	}

	public List<Rol> traerRoles() {
		
		return rolJpa.findRolEntities(); //trae todo los roles
		
	}

	public void crearUsuario(Usuario usu) {
		usuJpa.create(usu);
		
	}

	public void borrarUsuario(int id_usuario) {
		usuJpa.destroy(id_usuario);
		
	}


	public Usuario traerUsuario(int id_usuario) {
		// TODO Auto-generated method stub
		return usuJpa.findUsuario(id_usuario);
	}

	public void editarUsuario(Usuario usu) {
		try {
			usuJpa.edit(usu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
	
    
}