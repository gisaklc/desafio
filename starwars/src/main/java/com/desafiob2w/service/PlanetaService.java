package com.desafiob2w.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.desafiob2w.model.Planeta;
import com.desafiob2w.repository.PlanetaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanetaService {


	private final PlanetaRepository repositorio;

	public List<Planeta> buscarTodos() {
		return repositorio.findAll();
	}

	public Optional<Planeta> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	public Planeta salvar(Planeta planeta) {
		return repositorio.save(planeta);
	}

	public void deletarPorId(Long id) {
		repositorio.deleteById(id);
	}
	
	public Optional<Planeta> buscarPorNome(String name) {
		return repositorio.findByNome(name);
	}
}
