package com.sample_project_aem_cloud.core.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
            name = "MDB API Configuration", 
            description = "Configurations for the Site Sample"
)
public @interface MDBAPIConfig {

    
    @AttributeDefinition(
        name = "MDB Base URL",
        description = "URL base da API do Movie Database"
    )
    String mdbBaseUrl();

    @AttributeDefinition(
        name = "MDB Auth Token",
        description = "Token de autenticação"
    )
    String mdbAuthToken();

    @AttributeDefinition(
        name = "Movie Popular Endpoint",
        description = "Endpoint para filmes populares"
    )
    String moviePopularEndpoint();


}