package com.desafiob2w.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafiob2w.model.Planeta;
import com.desafiob2w.model.PlanetaApi;
import com.desafiob2w.model.ResultApi;
import com.desafiob2w.service.PlanetaService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.Constantes;

@RestController
@RequestMapping("planetas")
@Slf4j
@Data
@RequiredArgsConstructor
public class PlanetaController {

	private final PlanetaService planetaService;
	private final ResultApi apiExterna;

	private List<Planeta> planetaList = new ArrayList<Planeta>();
	
	
	@PostMapping
	public ResponseEntity<Planeta> salvar(@RequestBody Planeta planeta) {

		 if (verificarExistencia(planeta, Constantes.INCLUIR)) {
				return new ResponseEntity(Constantes.PLANETA_JA_CADASTRADO, HttpStatus.FORBIDDEN);
			}
		 
		Planeta p = planetaService.salvar(planeta);

		buscarQtdDeAparicoes(p);

		return new ResponseEntity(p, HttpStatus.CREATED);

	}
	

	@GetMapping("/{id}")
	public ResponseEntity buscarPorId(@PathVariable Long id) {
		Optional<Planeta> p = planetaService.buscarPorId(id);

		if (p.isPresent()) {

			buscarQtdDeAparicoes(p.get());

			return ResponseEntity.ok().body(p);
		} else {
			return new ResponseEntity(Constantes.NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity buscarPorNome(@PathVariable String nome) {

		Optional<Planeta> p = planetaService.buscarPorNome(nome);

		if (p.isPresent()) {

			buscarQtdDeAparicoes(p.get());

			return ResponseEntity.ok().body(p);

		} else {
			return new ResponseEntity(Constantes.NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping
	public ResponseEntity buscarTodos() {

		List<Planeta> planetas = planetaService.buscarTodos();

		if (!planetas.isEmpty()) {
			for (Planeta p : planetas) {
				buscarQtdDeAparicoes(p);
			}
		}

		return new ResponseEntity(planetas, HttpStatus.OK);
	}

	private void buscarQtdDeAparicoes(Planeta p) {

		if (apiExterna.getResults().isEmpty()) {
			apiExterna.buscarTodosApiExterna();
		}

		if (!apiExterna.getResults().isEmpty()) {
			for (PlanetaApi api : apiExterna.getResults()) {
				if (api.getName().equalsIgnoreCase(p.getNome())) {
					p.setQtdAparicoesFilme(api.getFilms().size());
				}
			}
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity alterar(@PathVariable Long id, @RequestBody Planeta planeta) {

		Optional<Planeta> p = planetaService.buscarPorId(id);

		if (p.isPresent()) {

			planeta.setId(id);

			if (verificarExistencia(planeta, 2)) {
				return new ResponseEntity(Constantes.PLANETA_JA_CADASTRADO, HttpStatus.FORBIDDEN);
			}

			planetaService.salvar(planeta);

			buscarQtdDeAparicoes(planeta);

			return new ResponseEntity(planeta, HttpStatus.OK);

		} else {
			return new ResponseEntity(Constantes.NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deletar(@PathVariable Long id) {

		Optional<Planeta> p = planetaService.buscarPorId(id);

		if (p.isPresent()) {

			planetaService.deletarPorId(id);

			return new ResponseEntity(Constantes.PLANETA_DELETADO, HttpStatus.OK);

		} else {
			return new ResponseEntity(Constantes.NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
		}
	}

	public boolean verificarExistencia(Planeta p, int acao) {

		boolean existe = false;

		planetaList = (List<Planeta>) buscarTodos().getBody();

		if (!planetaList.isEmpty()) {

			for (Planeta planeta1 : planetaList) {

				if (acao == Constantes.INCLUIR) {
					if (!planeta1.getId().equals(p.getId()) 
							&& planeta1.getNome().equalsIgnoreCase(p.getNome())) {
						existe = true;
						break;
					}
				}
				if (acao == Constantes.ALTERAR) {
					if (planeta1.getNome().equals(p.getNome())) {
						existe = true;
						break;
					}
				}
			}
		}
		return existe;

	}
	
	
	
	
}