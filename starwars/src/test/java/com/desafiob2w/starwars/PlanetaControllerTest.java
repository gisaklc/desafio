package com.desafiob2w.starwars;

import com.desafiob2w.controller.PlanetaController;
import com.desafiob2w.model.Planeta;
import com.desafiob2w.model.ResultApi;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PlanetaControllerTest {
    @Autowired
    private PlanetaController controller;

    private Planeta planeta;

    private Planeta criarPlaneta() {
        Planeta p = new Planeta();

        p.setNome("Tatooine");
        p.setClima("Arid");
        p.setTerreno("Dessert");

        return p;
    }

    @Test
    public void salvarPlanetaTest() {
        planeta = criarPlaneta();
        controller.salvar(planeta);

        assertNotNull(planeta.getId());
    }

    @Test
    public void buscarPorIdTest() {
        planeta = criarPlaneta();
        controller.salvar(planeta);

        ResponseEntity<Planeta> r1 = controller.buscarPorId(planeta.getId());

        assertNotNull(r1.getBody());
    }

	@Test
	public void buscarTodosTest() {
		planeta = criarPlaneta();
		controller.salvar(planeta);

		ResponseEntity p1 = controller.buscarTodos();
		List<Planeta> planetas = (List<Planeta>) p1.getBody();

		assertTrue(planetas.size() > 0);
	}

    @Test
    public void buscarPorNomeTest() {
        planeta = criarPlaneta();
        controller.salvar(planeta);
        ResponseEntity<Planeta> r1 = controller.buscarPorNome("Tatooine");

       assertEquals("Tatooine", r1.getBody().getNome());
    }

    @Test
    public void alterarPlanetaTest() {
        planeta = criarPlaneta();
        controller.salvar(planeta);

        Long idCriacao = planeta.getId();

        assertEquals("Tatooine", planeta.getNome());
        assertEquals("Arid", planeta.getClima());
        assertEquals("Dessert", planeta.getTerreno());

        Planeta planetaUpdate = new Planeta();
        planetaUpdate.setId(idCriacao);
        planetaUpdate.setNome("Naboo");
        planetaUpdate.setClima("Temperate");
        planetaUpdate.setTerreno("Earth");

        ResponseEntity<Planeta> r = controller.salvar(planetaUpdate);

        planetaUpdate = r.getBody();
        Long idAlteracao = planetaUpdate.getId();

        assertTrue(idCriacao.equals(idAlteracao));
        assertEquals(planeta.getNome(), planetaUpdate.getNome());
        assertEquals(planeta.getClima(), planetaUpdate.getClima());
        assertEquals(planeta.getTerreno(), planetaUpdate.getTerreno());
    }

    @Test
    public void deletarPlanetaTest() {
        planeta = criarPlaneta();
        controller.salvar(planeta);

        ResponseEntity responseEntity = controller.deletar(planeta.getId());

        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void servicoStartWarsTest() {
        ResultApi resultApi = new ResultApi();

        resultApi.buscarTodosApiExterna();

        assertTrue(!resultApi.getResults().isEmpty());
    }
}