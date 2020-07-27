package com.example.ejemploAppRecetas.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Promocion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String Nombre;
	private String Imagen;
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getImagen() {
		return Imagen;
	}
	public void setImagen(String imagen) {
		Imagen = imagen;
	}
	

}
