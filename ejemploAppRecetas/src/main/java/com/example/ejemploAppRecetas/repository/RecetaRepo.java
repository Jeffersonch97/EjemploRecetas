package com.example.ejemploAppRecetas.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.ejemploAppRecetas.entities.*;
public interface RecetaRepo extends CrudRepository <Cliente,Long>{

	List<Cliente> findByNombre(String nombre);
}
