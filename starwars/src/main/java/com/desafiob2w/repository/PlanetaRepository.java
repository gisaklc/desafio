package com.desafiob2w.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafiob2w.model.Planeta;

@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> { 
	
	Optional<Planeta> findByNome(String nome);
}
