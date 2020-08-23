package com.b2w.starwars.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.service.PlanetaService;

@RestController
@RequestMapping("api/planetas")
public class PlanetaController {

	@Autowired
	private PlanetaService planetaService;

	@GetMapping
	public List<Planeta> obterTodos() throws Exception {
		return this.planetaService.obterTodos();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Planeta criar(@RequestBody @Valid Planeta planeta) throws Exception {
		return this.planetaService.salvar(planeta);

	}

	@GetMapping("/{codigo}")
	@ResponseStatus(HttpStatus.OK)
	public Planeta obterPorCodigo(@PathVariable String codigo) throws Exception {
		return this.planetaService.obterPorId(codigo);
	}

	@GetMapping("/por-nome")
	public Planeta obterPorNome(@RequestParam("nome") String nome) throws Exception {
		return this.planetaService.obterPorNome(nome);
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{codigo}")
	public void deletar(@PathVariable String codigo) throws Exception {
		this.planetaService.deletar(codigo);
	}

}
