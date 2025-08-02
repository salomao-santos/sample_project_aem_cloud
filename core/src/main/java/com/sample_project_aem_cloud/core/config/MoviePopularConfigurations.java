package com.sample_project_aem_cloud.core.config;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
  name = "Sample Project - API Popular Movies Configurations",
  description = "Configurations for the Sample Project - API Popular Movies"
)
public @interface MoviePopularConfigurations {

    // Logout
  @AttributeDefinition(
    name = "Base URL for Popular Movies API",
    description = "Base URL used for the Popular Movies API"
  )
  String apiBaseUrlPopularMovies();


    @AttributeDefinition(
        name = "API Key for Popular Movies",
        description = "API Key used to access the Popular Movies API"
    )
    String apiKeyPopularMovies();

    @AttributeDefinition(
        name = "Connection Timeout (ms)",
        description = "Timeout for establishing a connection to the Popular Movies API"
    )
    int connectionTimeoutMs() default 5000;

    @AttributeDefinition(
        name = "Response Timeout (ms)",
        description = "Timeout for receiving a response from the Popular Movies API"
    )
    int responseTimeoutMs() default 10000;

    @AttributeDefinition(
        name = "Request Timeout (ms)",
        description = "Overall timeout for requests to the Popular Movies API"
    )
    int requestTimeoutMs() default 5000;

}
