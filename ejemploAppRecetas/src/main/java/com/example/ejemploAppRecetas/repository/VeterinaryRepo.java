package com.example.ejemploAppRecetas.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.VeterinarySystem.entities.*;

public interface VeterinaryRepo extends CrudRepository <Cliente,Long>
{
	List<Cliente> findByNombre(String nombre);
	
}
