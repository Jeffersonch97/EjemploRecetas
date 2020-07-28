package com.example.ejemploAppRecetas.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.ejemploAppRecetas.entities.*;
public interface RecetaRepo extends CrudRepository <Receta,Long>{

	List<Receta> findByNombre(String nombre);
	List<Receta> findByPreparacion(String preparacion);
}
