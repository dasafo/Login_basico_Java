package com.dasafodata.Login_Java.igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dasafodata.Login_Java.logica.Controladora;
import com.dasafodata.Login_Java.logica.Rol;
import com.dasafodata.Login_Java.logica.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.ItemSelectable;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle.Control;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EdicionUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textPass;
	
	int id_usuario;

	Usuario usu;
	Controladora control;
	
	/**
	 * Create the frame.
	 */
	public EdicionUsuarios(Controladora control, int id_usuario) {
		
		this.control = control;
		
		this.id_usuario = id_usuario;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 455, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEditarUsuarios = new JLabel("Editar Usuarios");
		lblEditarUsuarios.setFont(new Font("Dialog", Font.BOLD, 30));
		lblEditarUsuarios.setBounds(64, 12, 256, 69);
		contentPane.add(lblEditarUsuarios);
		
		JLabel lblNewLabel = new JLabel("Nombre Usuario:");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNewLabel.setBounds(37, 108, 162, 36);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPassword.setBounds(37, 169, 96, 36);
		contentPane.add(lblPassword);
		
		JLabel lblRol = new JLabel("Rol:");
		lblRol.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRol.setBounds(37, 236, 46, 36);
		contentPane.add(lblRol);
		
		textUsuario = new JTextField();
		textUsuario.setFont(new Font("Dialog", Font.PLAIN, 18));
		textUsuario.setColumns(10);
		textUsuario.setBounds(217, 109, 134, 26);
		contentPane.add(textUsuario);
		
		textPass = new JPasswordField();
		textPass.setFont(new Font("Dialog", Font.PLAIN, 18));
		textPass.setBounds(217, 174, 134, 26);
		contentPane.add(textPass);
		
		JComboBox cmbRol = new JComboBox();
		cmbRol.setFont(new Font("Dialog", Font.BOLD, 18));
		cmbRol.setBounds(217, 241, 134, 26);
		contentPane.add(cmbRol);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usuario = textUsuario.getText();
				String contra = textPass.getText();
				String rol = (String)cmbRol.getSelectedItem();
				
				control.editarUsuario(usu, usuario, contra, rol);
				
				mostrarMensaje("Usuario editado correctamente", "Info", "Usuario creado");
				
				EdicionUsuarios.this.dispose();
				
			}
		});
		btnGuardar.setFont(new Font("Dialog", Font.BOLD, 18));
		btnGuardar.setBounds(262, 321, 117, 43);
		contentPane.add(btnGuardar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setFont(new Font("Dialog", Font.BOLD, 18));
		btnLimpiar.setBounds(37, 321, 105, 43);
		contentPane.add(btnLimpiar);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
			    usu = control.traerUsuario(id_usuario);
				
				textUsuario.setText(usu.getNombreUsuario());
				textPass.setText(usu.getPassword());
				
				//cargamos la lista con los roles
				List<Rol> listaRoles = control.traerRoles();
				
				if(listaRoles != null) {
					for (Rol rol:listaRoles) {
						cmbRol.addItem(rol.getNombreRol());
					}
				}
				
				//marcamos para selecciona el rol del user
				String rol = usu.getUnRol().getNombreRol();
				
				int cantidadItems = cmbRol.getItemCount(); //para q nos traiga la cantidad de items q hay en el combobox
				
				//Recorremos todo el combo y traemos los roles
				for(int i = 0; i<cantidadItems; i++) {
					
					if(String.valueOf(cmbRol.getItemAt(i)).equals(rol)) { //pasamos un objeto a string
						cmbRol.setSelectedIndex(i); //lo seleccionamos
						
					}
				}
				
			}
		});

	}
	
	// Método para mostrar mensajes (FUERA del constructor)
    public void mostrarMensaje(String mensaje, String tipo, String titulo) {
        JOptionPane optionPane = new JOptionPane(mensaje);
        
        if(tipo.equals("Info")){
            optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        } else if(tipo.equals("Error")) {
            optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
        }
        
        JDialog dialog = optionPane.createDialog(titulo);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

}
