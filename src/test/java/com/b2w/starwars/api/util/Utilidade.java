package com.b2w.starwars.api.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.service.PlanetaService;


public class Utilidade {
	
	@Autowired
	static
	PlanetaService planetaService;

    public static String retornoJson(Planeta planeta) {

        String retornoJson= "{  \n" +              
                "      \"nome\":\""+ planeta.getNome() +"\",\n" +
                "      \"clima\": \""+ planeta.getClima() +"\",\n" +
                "      \"terreno\": \""+ planeta.getTerreno() +"\"\n" +               
                "}";

        return retornoJson;
    }


    
	public static List<Planeta> prepararMassaDados() {
		List<Planeta> listaPlanetas = new ArrayList<Planeta>();
		
		Planeta planeta1 = new Planeta("Hoth", "frozen", "tundra, ice caves, mountain ranges");		
		listaPlanetas.add(planeta1);

		Planeta planeta2 = new Planeta("Dagobah", "murky", "swamp, jungles");		
		listaPlanetas.add(planeta2);		
		
		Planeta planeta3 = new Planeta("Kamino", "temperate", "ocean");		
		listaPlanetas.add(planeta3);
		
		return listaPlanetas;

	}
}
