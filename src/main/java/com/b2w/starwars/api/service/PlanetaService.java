package com.b2w.starwars.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.b2w.starwars.api.exceptions.PlanetaConflictException;
import com.b2w.starwars.api.exceptions.PlanetaNotFoundException;
import com.b2w.starwars.api.exceptions.SwApiPlanetaResponseNotFoundException;
import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.repository.PlanetaRepository;
import com.b2w.starwars.api.util.SwApiUtil;

@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;

	@Autowired
	private SwApiPlanetResponseService swApiPlanetService;

	public List<Planeta> obterTodos() throws Exception {
		return setQtdAparicoesEmFilmes(this.planetaRepository.findAll());
	}

	@Transactional
	public Planeta salvar(Planeta planeta) throws Exception {
		 swApiPlanetService.getResultsByNamePlanet(planeta.getNome());
		Planeta plane = this.planetaRepository.findByNome(planeta.getNome());
		try {
			if (!SwApiUtil.isNullOrEmpty(plane)) {
				throw new PlanetaConflictException("Planeta" + plane.getNome() + " já existente.");
			}
			return this.setQtdAparicoesEmFilmes(planetaRepository.save(planeta));

		} catch (PlanetaConflictException ex) {
			throw new PlanetaConflictException(ex.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());

		}

	}

	public Planeta obterPorId(String codigo) throws Exception {
		try {
			// Optional<Planeta> planetaOptional = null;
			Optional<Planeta> planetaOptional = planetaRepository.findById(codigo);

			if (!planetaOptional.isPresent()) {
				throw new PlanetaNotFoundException("Não existe Planeta com o código: " + codigo);
			}

			return this.setQtdAparicoesEmFilmes(planetaOptional.get());

		} catch (PlanetaNotFoundException e) {
			throw new PlanetaNotFoundException(e.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}

	}

	public Planeta obterPorNome(String nome) throws Exception {
		try {
			Planeta planeta = this.planetaRepository.findByNome(nome);
			if (SwApiUtil.isNullOrEmpty(planeta)) {
				throw new PlanetaNotFoundException("Planeta " + nome + " não encontrado");
			}

			return this.setQtdAparicoesEmFilmes(this.planetaRepository.findByNome(nome));
		} catch (PlanetaNotFoundException e) {
			throw new PlanetaNotFoundException(e.getMessage());
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	private Planeta setQtdAparicoesEmFilmes(Planeta planeta) {
		Integer qtdAparicoesEmFilme = swApiPlanetService.getResultsByNamePlanet(planeta.getNome());
		try {
			planeta.setQtdAparicoesEmFilme(qtdAparicoesEmFilme);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return planeta;

	}

	private List<Planeta> setQtdAparicoesEmFilmes(List<Planeta> listaPlanetas) throws Exception {
		List<Planeta> listaPlanetasRetorno = new ArrayList<Planeta>();

		for (Planeta planeta : listaPlanetas) {
			planeta.setQtdAparicoesEmFilme(swApiPlanetService.getResultsByNamePlanet(planeta.getNome()));
			listaPlanetasRetorno.add(planeta);
		}

		return listaPlanetasRetorno;

	}

	@Transactional
	public void deletar(String codigo) throws Exception {
		this.obterPorId(codigo);

		try {

			planetaRepository.deleteById(codigo);

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}

	}

	@Transactional
	public void deletAll() {
		planetaRepository.deleteAll();

	}

	@Transactional
	public List<Planeta> saveAll(List<Planeta> lista) {
		return planetaRepository.saveAll(lista);

	}

}
