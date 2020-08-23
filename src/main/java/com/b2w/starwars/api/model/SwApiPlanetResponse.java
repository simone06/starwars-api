package com.b2w.starwars.api.model;

import com.b2w.starwars.api.model.SwApiPlanet;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SwApiPlanetResponse {

    public Integer count;
    public Object next;
    public Object previous;
    public List<SwApiPlanet> results;

    public SwApiPlanetResponse() {
    }
}
