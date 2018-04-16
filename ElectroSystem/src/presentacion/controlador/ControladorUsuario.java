package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.RolDTO;
import dto.UsuarioDTO;
import helpers.Validador;
import modelo.Modelo;
import presentacion.ventanas.usuario.VentanaAltaModifUsuario;

public class ControladorUsuario implements ActionListener {
	
	private VentanaAltaModifUsuario ventana;
	private Modelo modelo;
	private List<UsuarioDTO> usuarios;
	private List<RolDTO> roles;
	private UsuarioDTO usuario;

	public ControladorUsuario(VentanaAltaModifUsuario ventana, Modelo modelo, List<UsuarioDTO> usuarios) {
		this.ventana = ventana;
		this.modelo = modelo;
		this.usuarios = usuarios;
		iniciarCrear();
	}

	public ControladorUsuario(VentanaAltaModifUsuario ventana, Modelo modelo,
			List<UsuarioDTO> usuarios, UsuarioDTO usuario) {
		this.ventana = ventana;
		this.modelo = modelo;
		this.usuarios = usuarios;
		this.usuario = usuario;
		iniciarEditar();
	}

	private void iniciarEditar() {
		this.ventana.setTitle("Modificar " + usuario);
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnCrear().setText("Actualizar");
		this.ventana.getTfApellido().setText(usuario.getApellido());
		this.ventana.getTfNombre().setText(usuario.getNombre());
		this.ventana.getTfUsuario().setText(usuario.getUsuario());
		this.ventana.getTfApellido().setEditable(false);
		this.ventana.getTfNombre().setEditable(false);
		this.ventana.getBtnGenerarUsuario().addActionListener(this);
		this.ventana.getLblRol().setVisible(false);
		this.ventana.getRbAdm().setVisible(false);
		this.ventana.getRbTec().setVisible(false);
		this.ventana.getBtnGenerarUsuario().setVisible(false);
		
		this.ventana.setVisible(true);		
	}

	private void iniciarCrear() {
		this.ventana.setTitle("Crear usuario");
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnGenerarUsuario().addActionListener(this);
		this.ventana.getPfPass().setEditable(false);
		this.ventana.getPfRepitaPass().setEditable(false);
		this.ventana.getLblIngresePass().setVisible(false);
		this.ventana.getLblRepitaPass().setVisible(false);
		
		this.ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ventana.getBtnCrear()) {
			ventana.getLblNombreObligatorio().setVisible(false);
			ventana.getLblApellidoObligatorio().setVisible(false);
			ventana.getLblPass().setVisible(false);
			ventana.getLblRepita().setVisible(false);
			if (usuario == null) {
				ventana.getPfPass().setText("qwerasdf");
				ventana.getPfRepitaPass().setText("qwerasdf");
			}
			if (camposDatosLlenos()) {
				if (!ventana.getTfUsuario().getText().isEmpty()) {
					if (ventana.getPfPass().getPassword().length != 0) {
						if (ventana.getPfRepitaPass().getPassword().length != 0) {
							if (coinciden(ventana.getPfPass().getPassword(), ventana.getPfRepitaPass().getPassword())) {
								if (!usuarioExiste(ventana.getTfUsuario().getText())) {
									if (usuario == null) {
										crearModificarUsuario(0);
										JOptionPane.showMessageDialog(null, "El usuario se ha creado satisfactoriamente. La contraseña por defecto es \"qwerasdf\"", "Nuevo usuario creado", JOptionPane.INFORMATION_MESSAGE);
									} else {
										crearModificarUsuario(usuario.getIdPersonal());
										JOptionPane.showMessageDialog(null, "El usuario se ha modificado satisfactoriamente.", "Usuario modificado", JOptionPane.INFORMATION_MESSAGE);
									}
									this.ventana.dispose();
								} else {
									JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre. Ingrese otro nombre o genere uno desde el botón.", "Usuario ya existe", JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								ventana.getLblRepita().setVisible(true);
							}
						} else {
							ventana.getLblRepita().setVisible(true);
						}
					} else {
						ventana.getLblPass().setVisible(true);
					}
				} else {
					this.ventana.getTfUsuario().setText(generarUsuario());
				}
			} else {
				ventana.getLblNombreObligatorio().setVisible(true);
				ventana.getLblApellidoObligatorio().setVisible(true);
			}
		} else if (e.getSource() == this.ventana.getBtnCancelar()) {
			this.ventana.dispose();
	
		} else if (e.getSource() == this.ventana.getBtnGenerarUsuario()) {
			this.ventana.getTfUsuario().setText(generarUsuario());
		}
	
	}

	private String generarUsuario() {
		int n = 1;
		String usuario = "user";
		String tipo;
		if (this.ventana.getRbAdm().isSelected())
			tipo="-adm";
		else
			tipo="-tec";
		if (!this.ventana.getTfNombre().getText().isEmpty() &&
				!this.ventana.getTfApellido().getText().isEmpty() &&
				this.ventana.getTfNombre().getText().length()>1) {
			char[] nombre = this.ventana.getTfNombre().getText().toLowerCase().toCharArray();
			if (!usuarioExiste(nombre[0]+this.ventana.getTfApellido().getText().toLowerCase()+tipo))
				return nombre[0]+this.ventana.getTfApellido().getText().toLowerCase()+tipo;
			else if (!usuarioExiste(String.valueOf(nombre[0])+String.valueOf(nombre[1])+this.ventana.getTfApellido().getText().toLowerCase()+tipo))
				return String.valueOf(nombre[0])+String.valueOf(nombre[1])+this.ventana.getTfApellido().getText().toLowerCase()+tipo;
			else 
				while (usuarioExiste(String.valueOf(nombre[0])+String.valueOf(nombre[1])+n+this.ventana.getTfApellido().getText().toLowerCase()+tipo))
					n++;
			return String.valueOf(nombre[0])+String.valueOf(nombre[1])+n+this.ventana.getTfApellido().getText().toLowerCase()+tipo;
		} else {
			while (usuarioExiste(usuario+n+tipo))
				n++;
		}
		return usuario+n+tipo;
	}

	private void crearModificarUsuario(int id) {
		UsuarioDTO nuevo = new UsuarioDTO(id,
				ventana.getRbAdm().isSelected()?2:3,						//Hardcodeado.
				ventana.getRbAdm().isSelected()?"Administrativo":"Tecnico",	//Hardcodeado.
				ventana.getTfNombre().getText(),
				ventana.getTfApellido().getText(), 
				ventana.getTfUsuario().getText(),
				String.valueOf(ventana.getPfPass().getPassword()));
		if (id == 0)
			try {
				this.modelo.agregarUsuario(nuevo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		else
			try {
				if (usuario.getRol().getIdRol() == 1)
					nuevo.setRol(new RolDTO(1, "Superusuario"));			//Hardcodeado por ahora.
				this.modelo.actualizarUsuario(nuevo);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private boolean usuarioExiste(String s) {
		boolean b = false;
		try {
			usuarios = modelo.obtenerUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (UsuarioDTO u : usuarios) {
			if (u.getUsuario().equals(s))
				b = true;
			if (usuario!=null)
				if (u.getIdPersonal()==usuario.getIdPersonal())
					b = false;
		}
		return b;
	}

	private boolean coinciden(char[] p1, char[] p2) {
		boolean bandera = true;
		if (p1.length==p2.length) {
			for (int i=0; i<p1.length; i++)
				if (p1[i]!=p2[i])
					bandera = false;
		} else {
			bandera = false;
		}
		return bandera;
	}

	private boolean camposDatosLlenos() {
		if (!this.ventana.getTfNombre().getText().isEmpty() && !this.ventana.getTfApellido().getText().isEmpty()) {
			ventana.getTfNombre().setText(Validador.validarCampo(ventana.getTfNombre().getText()));
			ventana.getTfApellido().setText(Validador.validarCampo(ventana.getTfApellido().getText()));
		}
		return !this.ventana.getTfNombre().getText().isEmpty() && !this.ventana.getTfApellido().getText().isEmpty();
	}
}
