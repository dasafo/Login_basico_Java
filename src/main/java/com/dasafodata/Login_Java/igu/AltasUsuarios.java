package com.dasafodata.Login_Java.igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dasafodata.Login_Java.logica.Controladora;
import com.dasafodata.Login_Java.logica.Rol;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle.Control;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AltasUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textPass;

	Controladora control;

	/**
	 * Create the frame.
	 */
	public AltasUsuarios(Controladora control) {
		
		this.control = control;
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 383, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAltaDeUsuarios = new JLabel("Alta de Usuarios");
		lblAltaDeUsuarios.setFont(new Font("Dialog", Font.BOLD, 30));
		lblAltaDeUsuarios.setBounds(56, 12, 256, 69);
		contentPane.add(lblAltaDeUsuarios);
		
		JLabel lblNewLabel = new JLabel("Nombre Usuario:");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNewLabel.setBounds(29, 108, 162, 36);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 18));
		lblPassword.setBounds(29, 169, 96, 36);
		contentPane.add(lblPassword);
		
		JLabel lblRol = new JLabel("Rol:");
		lblRol.setFont(new Font("Dialog", Font.BOLD, 18));
		lblRol.setBounds(29, 236, 46, 36);
		contentPane.add(lblRol);
		
		textUsuario = new JTextField();
		textUsuario.setFont(new Font("Dialog", Font.PLAIN, 18));
		textUsuario.setBounds(209, 109, 134, 26);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		textPass = new JPasswordField();
		textPass.setFont(new Font("Dialog", Font.PLAIN, 18));
		textPass.setBounds(209, 174, 134, 26);
		contentPane.add(textPass);
		
		JComboBox cmbRol = new JComboBox();
		cmbRol.setFont(new Font("Dialog", Font.BOLD, 18));
		cmbRol.setBounds(209, 241, 134, 26);
		contentPane.add(cmbRol);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usuario = textUsuario.getText();
				String contra = textPass.getText();
				String rol = (String)cmbRol.getSelectedItem();
				
				control.crearRol(usuario, contra, rol);
				
				mostrarMensaje("Usuario creado correctamente", "Info", "Usuario creado");
			}
		});
		btnGuardar.setFont(new Font("Dialog", Font.BOLD, 18));
		btnGuardar.setBounds(254, 321, 117, 43);
		contentPane.add(btnGuardar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textUsuario.setText("");
				textPass.setText("");
				
				
			}
		});
		btnLimpiar.setFont(new Font("Dialog", Font.BOLD, 18));
		btnLimpiar.setBounds(29, 321, 105, 43);
		contentPane.add(btnLimpiar);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
				List<Rol> listaRoles = control.traerRoles();
				
				if(listaRoles != null) {
					for (Rol rol:listaRoles) {
						cmbRol.addItem(rol.getNombreRol());
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
