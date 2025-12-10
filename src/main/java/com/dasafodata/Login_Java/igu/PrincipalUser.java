package com.dasafodata.Login_Java.igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.dasafodata.Login_Java.logica.Controladora;
import com.dasafodata.Login_Java.logica.Usuario;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.PublicKey;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class PrincipalUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNombreUser;
	
	Controladora control;
	Usuario usr;
	private JTable tablaUsuarios;



	/**
	 * Create the frame.
	 */
	public PrincipalUser(Controladora control, Usuario usr) {
		
		
		
		
		this.control = control;
		this.usr = usr;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 703, 546);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Administrador de Usuarios");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		lblNewLabel.setBounds(35, 29, 424, 69);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 128, 449, 384);
		contentPane.add(scrollPane);
		
		tablaUsuarios = new JTable();
		scrollPane.setViewportView(tablaUsuarios);
		
		JButton btnRecargarTabla = new JButton("Recargar Tabla");
		btnRecargarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cargarTabla();
			}

			
		});
		btnRecargarTabla.setFont(new Font("Dialog", Font.BOLD, 18));
		btnRecargarTabla.setBounds(473, 139, 177, 56);
		contentPane.add(btnRecargarTabla);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				PrincipalUser.this.dispose();
			}
		});
		btnSalir.setFont(new Font("Dialog", Font.BOLD, 18));
		btnSalir.setBounds(471, 456, 177, 56);
		contentPane.add(btnSalir);
		
		textNombreUser = new JTextField();
		textNombreUser.setFont(new Font("Dialog", Font.PLAIN, 14));
		textNombreUser.setEditable(false);
		textNombreUser.setColumns(10);
		textNombreUser.setBounds(524, 30, 124, 40);
		contentPane.add(textNombreUser);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
				textNombreUser.setText(usr.getNombreUsuario());
				cargarTabla();
				
				
			}
			
		});
		
	}
	
	
	public void cargarTabla() {
        
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        String titulos[] = {"Id", "Usuario", "Rol"};
        modeloTabla.setColumnIdentifiers(titulos);

        List<Usuario> listaUsuarios = control.traerUsuarios();

        if (listaUsuarios != null) {
            for (Usuario usu : listaUsuarios) {
                Object[] objeto = {
                		usu.getId(),
                		usu.getNombreUsuario(),
                		usu.getUnRol().getNombreRol()
                    
                };
                modeloTabla.addRow(objeto);
            }
        }
        
        tablaUsuarios.setModel(modeloTabla);
	}

}
