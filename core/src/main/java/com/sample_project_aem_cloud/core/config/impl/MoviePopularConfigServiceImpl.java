package com.sample_project_aem_cloud.core.config.impl;

import org.osgi.service.component.annotations.Activate;

import com.sample_project_aem_cloud.core.config.MoviePopularConfigService;
import com.sample_project_aem_cloud.core.config.MoviePopularConfigurations;


import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(
  name = "com.sample_project_aem_cloud.core.config.MoviePopularConfigService",
  service = MoviePopularConfigService.class
)
@Designate(ocd = MoviePopularConfigurations.class)
public class MoviePopularConfigServiceImpl implements MoviePopularConfigService {

    private String apiBaseUrlPopularMovies;
    private String apiKeyPopularMovies;
    private int connectionTimeoutMs;
    private int responseTimeoutMs;
    private int requestTimeoutMs;


    @Activate
    protected void init(MoviePopularConfigurations configurations) {
        this.apiBaseUrlPopularMovies = configurations.apiBaseUrlPopularMovies();
        this.apiKeyPopularMovies = configurations.apiKeyPopularMovies();
        this.connectionTimeoutMs = configurations.connectionTimeoutMs();
        this.responseTimeoutMs = configurations.responseTimeoutMs();
        this.requestTimeoutMs = configurations.requestTimeoutMs();
    }

    @Override
    public String getApiBaseUrlPopularMovies() {
        return apiBaseUrlPopularMovies;
    }

    @Override
    public String getApiKeyPopularMovies() {
        return apiKeyPopularMovies;
    }

    @Override
    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    @Override
    public int getResponseTimeoutMs() {
        return responseTimeoutMs;
    }

    @Override
    public int getRequestTimeoutMs() {
        return requestTimeoutMs;
    }

}
