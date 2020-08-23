package com.b2w.starwars.api.model;
import java.net.URI;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * Entidade da API Star Wars * 
 */

@Data
public class SwApiPlanet {

    public String gravity;
    /**
     * A number denoting the gravity of this planet, where "1" is normal or 1 standard G. "2" is twice or 2 standard Gs. "0.5" is half or 0.5
     */
    public String terrain;
    /**
     * The terrain of this planet. Comma separated if diverse.
     */
    public Date created;
    /**
     * the ISO 8601 date format of the time that this resource was created.
     */
    public List<Object> residents = null;
    /**
     * An array of People URL Resources that live on this planet.
     */
    public String surface_water;
    /**
     *  The percentage of the planet surface that is naturally occurring water or bodies of water.
     */
    public Date edited;
    /**
     * the ISO 8601 date format of the time that this resource was edited.
     */
    public List<Object> films = null;
    /**
     * An array of Film URL Resources that this planet has appeared in.
     */
    public String climate;
    /**
     * The climate of this planet. Comma separated if diverse.
     */
    public String name;
    /**
     * The name of this planet.
     */
    public String diameter;
    /**
     * The diameter of this planet in kilometers.
     */
    public String population;
    /**
     *  The average population of sentient beings inhabiting this planet.
     */
    public String rotation_period;
    /**
     * The number of standard hours it takes for this planet to complete a single rotation on its axis.
     */
    public URI url;
    /**
     *  the hypermedia URL of this resource.
     */
    public String orbital_period;
    /**
     *  The number of standard days it takes for this planet to complete a single orbit of its local star.
     */
   
    
   // public SwapiPlanet() {}

    
}
