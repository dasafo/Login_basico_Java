package com.dasafodata.Login_Java.logica;

import java.util.List;

import com.dasafodata.Login_Java.persistencia.ControladoraPersistencia;

public class Controladora {

	ControladoraPersistencia controlPersis;
	
	
	public Controladora() {
		controlPersis = new ControladoraPersistencia();
	}

	public Usuario validarUsuario(String usuario, String pass) {
		
		//String mensaje = "";
		Usuario usr = null; //para pasar el ususario
		
		List<Usuario> listaUsuarios = controlPersis.traerUsuarios();
		
		for(Usuario usu: listaUsuarios) {
			
			//si el usuario de la lista es el mismo que el que viene del argumento del metodo...
			if(usu.getNombreUsuario().equals(usuario)) {
				if(usu.getPassword().equals(pass)) {
					
					//mensaje = "Usuario y contraseña correctos. Bienvenido/a!!";
					//return mensaje; //si encuentra el user corta aqui
					
					usr = usu;
					return usr;
					
				}else {
					//mensaje = "Contraseña incorrecta.";
					//return mensaje;
					usr = null;
					return usr;
				}
			}else {
				//mensaje = "Usuario no encontrado.";
				usr = null;
				
			}
		}
		
		
		//return mensaje; //sino muestra el mensaje del ultimo else
		return usr;
	}

	public List<Usuario> traerUsuarios() {
		// TODO Auto-generated method stub
		return controlPersis.traerUsuarios();
	}

	public List<Rol> traerRoles() {
		// TODO Auto-generated method stub
		return controlPersis.traerRoles();
	}

	public void crearRol(String usuario, String contra, String rolRecibido) {
		
		Usuario usu = new Usuario();
		usu.setNombreUsuario(usuario);
		usu.setPassword(contra);
		
		//Para obtener el Rol:
		Rol rolEncontrado = new Rol();
		rolEncontrado = this.traerRol(rolRecibido);
		
		if(rolEncontrado != null) {
			usu.setUnRol(rolEncontrado);
		}
		
		int id = this.buscarUltimaIdUsuarios();
		
		usu.setId(id+1); //+1 para asignar la posicion del ultimo id + la siguiente posicion
		
		//guardamos en la bbdd
		controlPersis.crearUsuario(usu);
		
	}
	
	
	

	private int buscarUltimaIdUsuarios() {
		List<Usuario> listaUsuarios = this.traerUsuarios();
		
		Usuario usu = listaUsuarios.get(listaUsuarios.size()-1); //pillamos el ultimo id hasta ahora(el indice)
		
		return usu.getId();
		
	}

	private Rol traerRol(String rolRecibido) {
		
		List<Rol> listaRoles= controlPersis.traerRoles();
		
		for(Rol rol:listaRoles) {
			if(rol.getNombreRol().equals(rolRecibido)) {
				return rol;
			}
		}
		
		return null;
	}

	public void borrarUsuario(int id_usuario) {
		controlPersis.borrarUsuario(id_usuario);
		
	}


	public Usuario traerUsuario(int id_usuario) {
		// TODO Auto-generated method stub
		return controlPersis.traerUsuario(id_usuario);
	}

	public void editarUsuario(Usuario usu, String usuario, String contra, String rolRecibido) {
		usu.setNombreUsuario(usuario);
		usu.setPassword(contra);
		
		//Para obtener el Rol:
		Rol rolEncontrado = new Rol();
		rolEncontrado = this.traerRol(rolRecibido);
				
		if(rolEncontrado != null) {
			usu.setUnRol(rolEncontrado);
		}
		
		controlPersis.editarUsuario(usu);
		
	}

	
}
