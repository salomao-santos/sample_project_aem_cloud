package com.sample_project_aem_cloud.core.config;

public interface MoviePopularConfigService {

    public String getApiBaseUrlPopularMovies();

    public String getApiKeyPopularMovies();

    public int getConnectionTimeoutMs();

    public int getResponseTimeoutMs();

    public int getRequestTimeoutMs();

}
