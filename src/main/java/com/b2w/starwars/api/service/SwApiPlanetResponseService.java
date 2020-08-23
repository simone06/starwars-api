package com.b2w.starwars.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.b2w.starwars.api.exceptions.SwApiPlanetaResponseNotFoundException;
import com.b2w.starwars.api.model.SwApiPlanetResponse;
import com.b2w.starwars.api.util.SwApiUtil;

import reactor.core.publisher.Mono;

@PropertySource("classpath:application.properties")
@Service
public class SwApiPlanetResponseService {

	@Value("${swapi.resource}")
	private String swApiResource;

	@Value("${swapi.resource.search}")
	private String swApiResourceSearch;

	@Autowired
	private WebClient webClientSwApi;

	private String getSwApiUrlResource() {
		return this.swApiResource + this.swApiResourceSearch;
	}

	public Integer getResultsByNamePlanet(String nome) {
		try {

			Integer qtdAparicoesEmFilmes = 0;

			Mono<SwApiPlanetResponse> monoPlanetResponse = this.webClientSwApi.method(HttpMethod.GET)
					.uri(this.getSwApiUrlResource() + nome).retrieve().bodyToMono(SwApiPlanetResponse.class);
			SwApiPlanetResponse planetResponse = monoPlanetResponse.block();

			if (SwApiUtil.isNullOrEmpty(planetResponse.getResults())) {
				throw new SwApiPlanetaResponseNotFoundException(
						"O Planeta " + nome + " não foi encontrado na API 'https://swapi.dev/api/planets/' ");
			}

			qtdAparicoesEmFilmes = this.getQtdAparicoes(planetResponse);
			return qtdAparicoesEmFilmes;

		} catch (SwApiPlanetaResponseNotFoundException e) {
			throw new SwApiPlanetaResponseNotFoundException(e.getMessage());
		}
	}

	private Integer getQtdAparicoes(SwApiPlanetResponse swApiPlanetResponse) {
		Integer qtdAparicoes = 0;

		if (!SwApiUtil.isNullOrEmpty(swApiPlanetResponse.results)) {
			qtdAparicoes = swApiPlanetResponse.results.get(0).films.size();

			return qtdAparicoes;
		}
		
		return qtdAparicoes;
	}
}
