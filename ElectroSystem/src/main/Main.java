package main;

import modelo.Modelo;
import presentacion.controlador.ControladorLogueo;
import presentacion.ventanas.logueo.VentanaLogueo;

public class Main {

	public static void main(String[] args) {
		Modelo modelo = new Modelo();
		VentanaLogueo ventanaLogueo = new VentanaLogueo();
		ControladorLogueo controladorLogueo = new ControladorLogueo(ventanaLogueo, modelo);
		controladorLogueo.iniciar();	
	}
}