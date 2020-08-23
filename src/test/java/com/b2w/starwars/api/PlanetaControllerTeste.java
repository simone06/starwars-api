package com.b2w.starwars.api;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.service.PlanetaService;
import com.b2w.starwars.api.service.SwApiPlanetResponseService;
import com.b2w.starwars.api.util.Utilidade;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PlanetaControllerTeste {
	
	@LocalServerPort
	private int port;


    @Autowired
    PlanetaService planetaService;

    @Autowired
    private TestRestTemplate restTemplate;
    
    
    private List<Planeta> listaPlanetas;
    
    @MockBean
    private SwApiPlanetResponseService swapiPlanetServiceMocked;
    
	@Before
	public void init() {
		RestAssured.port = port;
		planetaService.deletAll();		
		this.listaPlanetas = planetaService.saveAll(Utilidade.prepararMassaDados());
   
	}

//	Inicio dos testes de cadastro
	
	@Test
	public void deveRetornarStatusCreated_QuandoCadastrarPlaneta()throws URISyntaxException {
		Planeta planeta = new Planeta("Tatooine","arid","desert");		
		
		  String planetaJson = Utilidade.retornoJson(planeta);

	        RequestEntity<String> entity =  RequestEntity
	                .post(new URI("/api/planetas"))
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .body(planetaJson);

//	        Execute a solicitação especificada no dado RequestEntitye retorne a resposta como ResponseEntity.
	        ResponseEntity<Planeta> exchange = restTemplate
	                .exchange(entity, Planeta.class);

	        Assert.assertNotNull(exchange);
	        Assert.assertEquals(HttpStatus.CREATED, exchange.getStatusCode());
    	   
	}
	
	@Test
	public void deveRetornonarStatusConflit_QuandoPlanetaJaCadastrado() throws URISyntaxException {
	Planeta planeta = new Planeta("Hoth","frozen","tundra, ice caves, mountain ranges");		
		
		  String planetaJson = Utilidade.retornoJson(planeta);

	        RequestEntity<String> entity =  RequestEntity
	                .post(new URI("/api/planetas"))
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .body(planetaJson);


	        ResponseEntity<Planeta> exchange = restTemplate
	                .exchange(entity, Planeta.class);

	        Assert.assertNotNull(exchange);
	        Assert.assertEquals(HttpStatus.CONFLICT, exchange.getStatusCode());
    	   
	}
	
	@Test
	public void deveTrazerQtdDeAparicoesPreenchido_QuandoCadastrarPlaneta()throws URISyntaxException {
		Planeta planeta = new Planeta("Bespin","temperate","gas giant");		
		
		  String planetaJson = Utilidade.retornoJson(planeta);

	        RequestEntity<String> entity =  RequestEntity
	                .post(new URI("/api/planetas"))
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .body(planetaJson);


	        ResponseEntity<Planeta> exchange = restTemplate
	                .exchange(entity, Planeta.class);

	        Assert.assertNotNull(exchange.getBody());
	        Assert.assertNotNull(exchange.getBody().getQtdAparicoesEmFilme());	 
	        Assert.assertEquals(HttpStatus.CREATED, exchange.getStatusCode());
	           
    	   
	}
		
	
//	Fim teste cadastro	
	

	//Inicia testes listagens 
	@Test
	public void deveRetornonarStatusOk_QuandoForPlanetaEncontrado()throws URISyntaxException {	

	        RequestEntity<Void> entity =  RequestEntity
	                .get(new URI("/api/planetas"))
	                .accept(MediaType.APPLICATION_JSON)
	                .build();


	        ResponseEntity<String> exchange = restTemplate
	                .exchange(entity, String.class);

	        Assert.assertNotNull(exchange);
	        Assert.assertEquals(HttpStatus.OK,exchange.getStatusCode());	       
	   
    	   
	}
	

	
//	Fim testes listagens
	
//	Inicio teste por Id
	
	@Test
	public void deveRetornonarStatusOk_QuandoBuscarPorId() throws URISyntaxException {	

		   RequestEntity<Void> entity =  RequestEntity
	                .get(new URI("/api/planetas/"+this.listaPlanetas.get(0).getId()))
	                .accept(MediaType.APPLICATION_JSON)
	                .build();

	        ResponseEntity<Planeta> exchangeBusca = restTemplate
	                .exchange(entity, Planeta.class);

	        Assert.assertNotNull(exchangeBusca.getBody().getId());
	        Assert.assertEquals(HttpStatus.OK, exchangeBusca.getStatusCode());      	   
	}
	
	@Test
	public void deveRetornonarStatusNotFound_QuandoBuscarPorId_EPlanetaInexistente() throws URISyntaxException {	

		   RequestEntity<Void> entity =  RequestEntity
	                .get(new URI("/api/planetas/1"))
	                .accept(MediaType.APPLICATION_JSON)
	                .build();

	        ResponseEntity<Planeta> retornoRequest = restTemplate
	                .exchange(entity, Planeta.class);

	        Assert.assertNull(retornoRequest.getBody().getId());	       
	        Assert.assertEquals(HttpStatus.NOT_FOUND, retornoRequest.getStatusCode());
	}       
	
	@Test
	public void deveTrazerQtdDeAparicoesPreenchido_QuandoBuscarPorId() throws URISyntaxException {	

		   RequestEntity<Void> entity =  RequestEntity
				   .get(new URI("/api/planetas/"+this.listaPlanetas.get(0).getId()))
	                .accept(MediaType.APPLICATION_JSON)
	                .build();

	        ResponseEntity<Planeta> retornoRequest = restTemplate
	                .exchange(entity, Planeta.class);

	        Assert.assertNotNull(retornoRequest.getBody().getId());	
	        Assert.assertNotNull(retornoRequest.getBody().getQtdAparicoesEmFilme());	       
	        Assert.assertEquals(HttpStatus.OK, retornoRequest.getStatusCode());
	} 
	
	
	//Fim testes buscar Por Id
	
	
//	Inicio teste por nome
	
	@Test
	public void deveRetornonarStatusOk_QuandoBuscarPorNome() throws URISyntaxException {	

		   RequestEntity<Void> entity =  RequestEntity
	                .get(new URI("/api/planetas/?"+this.listaPlanetas.get(0).getNome()))
	                .accept(MediaType.APPLICATION_JSON)
	                .build();

	        ResponseEntity<String> exchangeBusca = restTemplate
	                .exchange(entity, String.class);

	        Assert.assertNotNull(exchangeBusca.getBody());
	        Assert.assertEquals(HttpStatus.OK, exchangeBusca.getStatusCode());      	   
	}
	
	@Test
	public void deveRetornonarStatusNotFound_QuandoBuscarPorNome_EPlanetaInexistir() throws URISyntaxException {	

		   RequestEntity<Void> entity =  RequestEntity
	                .get(new URI("/api/planetas/1"))
	                .accept(MediaType.APPLICATION_JSON)
	                .build();

	        ResponseEntity<Planeta> retornoRequest = restTemplate
	                .exchange(entity, Planeta.class);

	        Assert.assertNull(retornoRequest.getBody().getNome());	       
	        Assert.assertEquals(HttpStatus.NOT_FOUND, retornoRequest.getStatusCode());
	}       
	
	@Test
	public void deveTrazerQtdDeAparicoesPreenchido_QuandoBuscarPorNome() throws URISyntaxException {	

		   RequestEntity<Void> entity =  RequestEntity
				   .get(new URI("/api/planetas/?nome="+this.listaPlanetas.get(1).getNome()))
	                .accept(MediaType.APPLICATION_JSON)
	                .build();

	        ResponseEntity<String> retornoRequest = restTemplate
	                .exchange(entity, String.class);
	        
	        Assert.assertEquals(HttpStatus.OK, retornoRequest.getStatusCode());
	} 
	
	
	//Fim testes buscar Por nome
	
   //Inicio  testes delete
	
	@Test
    public void deveRetornarStatusNotContent_QuandoDeletarPlanetaComsucesso() throws URISyntaxException {

        RequestEntity<Void> entity =  RequestEntity
                .delete(new URI("/api/planetas/delete/"+this.listaPlanetas.get(2).getId()))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<Planeta> retornoRequest = restTemplate
                .exchange(entity, Planeta.class);

        Assert.assertEquals(HttpStatus.NO_CONTENT, retornoRequest.getStatusCode());
        Assert.assertNull(retornoRequest.getBody());       
    }
	
	@Test
    public void deveRetornarStatusNotFound_QuandoDeletarPlanetaInexistente() throws URISyntaxException {

        RequestEntity<Void> entity =  RequestEntity
                .delete(new URI("/api/planetas/delete/22"))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<Planeta> retornoRequest = restTemplate
                .exchange(entity, Planeta.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, retornoRequest.getStatusCode());
        Assert.assertNull(retornoRequest.getBody().getId());
       
    }
	//Fim  testes delete

   
}
