package com.searchfight.callers;

import com.searchfight.utils.IllegalArgException;
import com.searchfight.utils.SearchConstants;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.ServiceNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


public abstract class WebSearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSearch.class);
    private final String apiKey;
    private final Header[] headers;
    private URIBuilder builder = new URIBuilder();

    public WebSearch() throws IOException, IllegalArgException {
        this.apiKey = setApiKey();
        builder = buildBasicURI();
        headers = addHeaders();

    }

    abstract String setApiKey() throws IOException, IllegalArgException;

    void trustStore() {
        Properties systemProps = System.getProperties();
        InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(SearchConstants.CERT_NAME);
        systemProps.put("javax.net.ssl.trustStore", resourceStream);
        systemProps.put("javax.net.ssl.trustAnchors", resourceStream);
        System.setProperties(systemProps);
    }

    abstract long buildResponseFromJson(JSONObject jsonResponsr);

    public long queryAndGetResultCount(String query) throws URISyntaxException, IOException, ServiceNotFoundException, IllegalArgException {
        URI uri = addParametersAndBuildURI(query);
        HttpGet get = new HttpGet(uri);
        get.setHeaders(headers);
        trustStore();
        HttpClient httpClient = HttpClientBuilder.create().setProxy(new HttpHost("seproxy.hm.com")).build();
        LOGGER.debug("<querying> {}", uri);
        HttpResponse response = httpClient.execute(get);

        if (response.getStatusLine().getStatusCode() != 200) {
            logError(response);
        }
        return buildResponseFromJson(new JSONObject(IOUtils.toString(response.getEntity().getContent(), "utf-8")));
    }

    void logError(HttpResponse response) throws IOException, ServiceNotFoundException {
        LOGGER.error("Got HTTP error {}. Message is: {}", response.getStatusLine().getStatusCode(),
                response.getEntity().getContent());
        throw new UnknownHostException("Got response code:" + response.getStatusLine().getStatusCode());
    }

    URIBuilder buildBasicURI() throws IOException, IllegalArgException {
        builder.setScheme(setAPIProtocol()).setHost(setAPIHost()).setPath(setAPIPath());
        return builder;
    }

    String getBuilderInfo() {
        return builder.toString();

    }

    URI addParametersAndBuildURI(String query) throws URISyntaxException, IOException, IllegalArgException {
        builder.clearParameters().addParameter(SearchConstants.Q, query).addParameter(SearchConstants.KEY, getApiKey());
        builder.addParameters(addAdditionalParameters());
        return builder.build();
    }

    Header[] addHeaders() throws IOException, IllegalArgException {
        List<Header> headerDetails = new ArrayList<>();
        headerDetails.addAll(addAdditionalHeaders());
        return headerDetails.stream().toArray(Header[]::new);
    }

    List<Header> addAdditionalHeaders() throws IOException, IllegalArgException {
        return Collections.emptyList();
    }

    List<NameValuePair> addAdditionalParameters() throws IOException, IllegalArgException {
        return Collections.emptyList();
    }

    String setAPIProtocol() {
        return SearchConstants.API_PROTOCOL;
    }


    abstract String setAPIHost() throws IOException, IllegalArgException;

    abstract String setAPIPath() throws IOException, IllegalArgException;

    private String getApiKey() {
        return apiKey;
    }

}
