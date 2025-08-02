package com.sample_project_aem_cloud.core.servlets.v6;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample_project_aem_cloud.core.config.MoviePopularConfigService;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/v6/movies/popular")
public class PopularMovieServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(PopularMovieServlet.class);
    private static final long serialVersionUID = 1L;

    @Reference
    private MoviePopularConfigService moviePopularConfigService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Receber os parâmetros da requisição
        String paginationParam = request.getParameter("page");
        String languageParam = request.getParameter("language");

        // Valores padrão caso os parâmetros não sejam fornecidos
        int pagination = (paginationParam != null) ? Integer.parseInt(paginationParam) : 1;
        String language = (languageParam != null) ? languageParam : "pt-BR";

        try (CloseableHttpClient httpClient = createHttpClientWithTimeout()) {
            String url = buildUrl(moviePopularConfigService.getApiBaseUrlPopularMovies(), pagination, language);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Authorization", "Bearer " + moviePopularConfigService.getApiKeyPopularMovies());

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                LOG.info("Código de status: {}", httpResponse.getStatusLine().getStatusCode());

                HttpEntity entity = httpResponse.getEntity();
                String resposta = EntityUtils.toString(entity);
                LOG.info("Resposta:\n{}", resposta);
                EntityUtils.consume(entity);
                response.getWriter().write(resposta);
            } catch (IOException e) {
                LOG.error("Erro ao fazer a requisição", e);
                response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Erro ao fazer a requisição\"}");
            }
        } catch (IOException e) {
            LOG.error("Erro ao criar o cliente HTTP", e);
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Erro ao criar o cliente HTTP\"}");
        }
    }

    private CloseableHttpClient createHttpClientWithTimeout() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(moviePopularConfigService.getConnectionTimeoutMs())
                .setSocketTimeout(moviePopularConfigService.getResponseTimeoutMs())
                .setConnectionRequestTimeout(moviePopularConfigService.getRequestTimeoutMs())
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    private String buildUrl(String baseUrl, int pagination, String language) throws IOException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("language", language));
        params.add(new BasicNameValuePair("page", String.valueOf(pagination)));

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        if (!params.isEmpty()) {
            urlBuilder.append("?");
            for (int i = 0; i < params.size(); i++) {
                NameValuePair param = params.get(i);
                urlBuilder.append(URLEncoder.encode(param.getName(), StandardCharsets.UTF_8));
                urlBuilder.append("=");
                urlBuilder.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8));
                if (i < params.size() - 1) {
                    urlBuilder.append("&");
                }
            }
        }
        return urlBuilder.toString();
    }
}