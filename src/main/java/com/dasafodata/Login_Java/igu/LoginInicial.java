package com.dasafodata.Login_Java.igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dasafodata.Login_Java.logica.Controladora;
import com.dasafodata.Login_Java.logica.Usuario;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JLabel lblPassword;
	private JPasswordField textPass;

	Controladora control;

	/**
	 * Create the frame.
	 */
	public LoginInicial() {
		
		
		control = new Controladora();
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Dialog", Font.BOLD, 34));
		lblLogin.setBounds(165, 32, 103, 54);
		contentPane.add(lblLogin);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Dialog", Font.BOLD, 18));
		lblUsuario.setBounds(59, 135, 86, 54);
		contentPane.add(lblUsuario);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(163, 147, 186, 36);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPassword.setBounds(59, 213, 97, 54);
		contentPane.add(lblPassword);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.DARK_GRAY);
		separator.setForeground(new Color(53, 132, 228));
		separator.setBounds(44, 305, 322, 8);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(53, 132, 228));
		separator_1.setBackground(Color.DARK_GRAY);
		separator_1.setBounds(44, 368, 322, 8);
		contentPane.add(separator_1);
		

		JTextArea textMensaje = new JTextArea();
		textMensaje.setEditable(false);
		textMensaje.setBounds(23, 384, 397, 204);
		contentPane.add(textMensaje);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usuario = textUsuario.getText();
				String pass = textPass.getText();
				
				/*
				String mensaje = control.validarUsuario(usuario, pass);
				
				
				if(!mensaje.equals("")) {
					String rolString = control.validarRol(usuario, pass);
				}
				
				textMensaje.setText(mensaje);
				
				*/
				Usuario usr = control.validarUsuario(usuario, pass);
				
				if(usr!=null) {
					
					String rol = usr.getUnRol().getNombreRol();
					 
					if(rol.equals("admin")){
						//Lo mandamos a la pantalla de Admin y le pasamos la controladora de la logica
						//para seguir usando la misma instancia
						PrincipalAdmin pAdmin = new PrincipalAdmin(control, usr);
						pAdmin.setVisible(true);
						pAdmin.setLocationRelativeTo(null);
						
						LoginInicial.this.dispose(); //cerramos esta pagina
						
					}if(rol.equals("user")){
						//Lo mandamos a la pantalla de Usuario y le pasamos la controladora de la logica
						//para seguir usando la misma instancia
						PrincipalUser pUser = new PrincipalUser(control, usr);
						pUser.setVisible(true);
						pUser.setLocationRelativeTo(null);
						
						LoginInicial.this.dispose(); //cerramos esta pagina

					}
				}else {
					textMensaje.setText("Usuario o Contraseña incorrecto.");
				}

				
				
			}
		});
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 18));
		btnLogin.setBounds(54, 325, 105, 27);
		contentPane.add(btnLogin);
		
		
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				textUsuario.setText("");
				textPass.setText("");
				textMensaje.setText("");
				
			}
		});
		btnLimpiar.setFont(new Font("Dialog", Font.BOLD, 18));
		btnLimpiar.setBounds(221, 325, 105, 27);
		contentPane.add(btnLimpiar);
		
		textPass = new JPasswordField();
		textPass.setBounds(165, 218, 186, 36);
		contentPane.add(textPass);

	}
}
