package com.sample_project_aem_cloud.core.services.v5.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import com.sample_project_aem_cloud.core.configs.MDBAPIConfig;
import com.sample_project_aem_cloud.core.services.v5.MDBAPIService;

@Component(
    name = "com.sample_project_aem_cloud.core.services.v5.MDBAPIServiceImpl",
    service = MDBAPIService.class
)
@Designate(ocd = MDBAPIConfig.class)
public class MDBAPIServiceImpl implements MDBAPIService {

    private String mdbBaseUrl;

    private String mdbAuthToken;

    private String moviePopularEndpoint;

    
    @Activate
    protected void activate(MDBAPIConfig config) {
        this.mdbBaseUrl = config.mdbBaseUrl();

        this.mdbAuthToken = config.mdbAuthToken();

        this.moviePopularEndpoint = config.moviePopularEndpoint();   
    }

    @Override
    public String getMdbBaseUrl() {
        return this.mdbBaseUrl;
    }

    @Override
    public String getMdbAuthToken() {
        return this.mdbAuthToken;
    }

    @Override
    public String getMoviePopularEndpoint() {
        return this.moviePopularEndpoint;
    }

}
