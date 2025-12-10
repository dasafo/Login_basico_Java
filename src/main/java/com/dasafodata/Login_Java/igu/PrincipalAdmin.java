package com.dasafodata.Login_Java.igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.dasafodata.Login_Java.logica.Controladora;
import com.dasafodata.Login_Java.logica.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrincipalAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaUsuarios;
	private JTextField textNombreUser;

	Controladora control;
	Usuario usr;
	

	/**
	 * Create the frame.
	 */
	public PrincipalAdmin(Controladora control, Usuario usr) {
		
		this.control = control;
		this.usr = usr;

		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Administrador de Usuarios");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		lblNewLabel.setBounds(37, 36, 424, 69);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 134, 449, 384);
		contentPane.add(scrollPane);
		
		tablaUsuarios = new JTable();
		scrollPane.setViewportView(tablaUsuarios);
		
		JButton btnNuevoUsuario = new JButton("Crear Usuario");
		btnNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AltasUsuarios altaUsu = new AltasUsuarios(control);
				altaUsu.setVisible(true);
				altaUsu.setLocationRelativeTo(null);
				
				//PrincipalAdmin.this.dispose();
				
			}
		});
		btnNuevoUsuario.setFont(new Font("Dialog", Font.BOLD, 18));
		btnNuevoUsuario.setBounds(473, 134, 177, 56);
		contentPane.add(btnNuevoUsuario);
		
		JButton btnEditarUsuario = new JButton("Editar Usuario");
		btnEditarUsuario.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        // Comprobamos que la tabla no esta vacia
		        if (tablaUsuarios.getRowCount() > 0) {
		            // Controlamos que tengamos algo seleccionado
		            if (tablaUsuarios.getSelectedRow() != -1) {
		                
		                // Obtenemos la id
		                int id_usuario = Integer.parseInt(String.valueOf(tablaUsuarios.getValueAt(tablaUsuarios.getSelectedRow(), 0)));

		                
		                EdicionUsuarios pantallaEdic = new EdicionUsuarios(control, id_usuario);
		                pantallaEdic.setVisible(true);
		                pantallaEdic.setLocationRelativeTo(null);
		                
		                
		            } else { // Este else ahora sí funciona porque cerramos el if arriba
		                mostrarMensaje("No se seleccionó nada", "Error", "Error al editar");
		            }
		        } else {
		            mostrarMensaje("Tabla vacia", "Error", "Error al editar");
		        }
		    }
		});
		btnEditarUsuario.setFont(new Font("Dialog", Font.BOLD, 18));
		btnEditarUsuario.setBounds(473, 202, 177, 56);
		contentPane.add(btnEditarUsuario);
		
		JButton btnBorrarUsuario = new JButton("Borrar Usuario");
		btnBorrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//comporbamos que la tabla no esta vacia
				if(tablaUsuarios.getRowCount() >0) {
					//controlamos que tengamos algo seleccionado
					if(tablaUsuarios.getSelectedRow() != -1) {
						
						//obtenemos la id del elemento a eliminar. El id viene como Object que pasamos a string
						//y luego finalmente a entero
						int id_usuario = Integer.parseInt(String.valueOf(tablaUsuarios.getValueAt( tablaUsuarios.getSelectedRow(), 0))); //columna 0(es el id)
					
						//llamo al metodo borrar
						control.borrarUsuario(id_usuario);
						
						//ventana de aviso de que todo se hizo correctametne
						mostrarMensaje("Se borró el usuario correctamente", "Info", "Usuario borrado");
						
						//recargamos la tabla actualizada
						cargarTabla();
					}else {
						mostrarMensaje("No se seleccionó nada", "Error", "Error al borrar");
					}
				}else {
					mostrarMensaje("Tabla vacia", "Error", "Error al borrar");
				}
			}
		});
		btnBorrarUsuario.setFont(new Font("Dialog", Font.BOLD, 18));
		btnBorrarUsuario.setBounds(473, 270, 177, 56);
		contentPane.add(btnBorrarUsuario);
		
		JButton btnRecargarTabla = new JButton("Recargar Tabla");
		btnRecargarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarTabla();

				
			}
		});
		btnRecargarTabla.setFont(new Font("Dialog", Font.BOLD, 18));
		btnRecargarTabla.setBounds(473, 338, 177, 56);
		contentPane.add(btnRecargarTabla);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PrincipalAdmin.this.dispose();
			}
		});
		btnSalir.setFont(new Font("Dialog", Font.BOLD, 18));
		btnSalir.setBounds(473, 462, 177, 56);
		contentPane.add(btnSalir);
		
		textNombreUser = new JTextField();
		textNombreUser.setFont(new Font("Dialog", Font.PLAIN, 14));
		textNombreUser.setEditable(false);
		textNombreUser.setBounds(526, 36, 124, 40);
		contentPane.add(textNombreUser);
		textNombreUser.setColumns(10);
		
		
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
