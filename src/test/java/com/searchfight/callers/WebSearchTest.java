package com.searchfight.callers;

import com.searchfight.utils.IllegalArgException;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

public class WebSearchTest {

    @Test
    public void trustStore() throws IOException, IllegalArgException {

        WebSearch webSearch = buildWebSearch("Test");
        webSearch.trustStore();
        Properties systemProps = System.getProperties();
        Assertions.assertTrue(systemProps.get("javax.net.ssl.trustStore") instanceof InputStream);
        Assertions.assertTrue(systemProps.get("javax.net.ssl.trustAnchors") instanceof InputStream);

    }

    @Test
    public void testAddParamters() throws URISyntaxException, IOException, IllegalArgException {

        WebSearch webSearch = buildWebSearch("Test");
        URI uri = webSearch.addParametersAndBuildURI("Test");
        Assertions.assertTrue(uri.getQuery().contains("Test"));
        Assertions.assertTrue(uri.getQuery().contains("key"));

    }

    @Test
    public void testAddParamtersWithSpace() throws URISyntaxException, IOException, IllegalArgException {

        WebSearch webSearch = buildWebSearch("TestKey");
        URI uri = webSearch.addParametersAndBuildURI("Test Again");
        Assertions.assertTrue(uri.getQuery().contains("Test+Again"));
        Assertions.assertTrue(uri.getQuery().contains("key"));
        Assertions.assertTrue(uri.getQuery().contains("TestKey"));
    }

    @Test
    public void testaddAddtionalHeaders() throws IOException, IllegalArgException {

        WebSearch webSearch = buildWebSearch("Test");
        List<Header> headers = webSearch.addAdditionalHeaders();
        Assertions.assertEquals(0, headers.size());
    }

    @Test
    public void testaddAddtionalParamters() throws IOException, IllegalArgException {
        WebSearch webSearch = buildWebSearch("Test");
        List<NameValuePair> nameValuePairs = webSearch.addAdditionalParameters();
        Assertions.assertEquals(0, nameValuePairs.size());
    }

    @Test
    public void testBuilderInf() throws IOException, IllegalArgException {
        WebSearch webSearch = buildWebSearch("Test");
        Assertions.assertEquals("https://1234weretete", webSearch.getBuilderInfo());
    }

    @Test
    public void testLogError() throws IOException, IllegalArgException {
        WebSearch webSearch = buildWebSearch("Test");
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponse.getStatusLine()).thenReturn(Mockito.mock(StatusLine.class));
        Mockito.when(httpResponse.getStatusLine().getStatusCode()).thenReturn(100);
        Mockito.when(httpResponse.getEntity()).thenReturn(Mockito.mock(HttpEntity.class));
        Mockito.when(httpResponse.getEntity().getContent()).thenReturn(new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        });
        Assertions.assertThrows(UnknownHostException.class, () -> webSearch.logError(httpResponse));

    }

    @Test
    public void testQueryAndLogError() throws IOException, URISyntaxException, IllegalArgException {
        WebSearch webSearch = buildWebSearch("Test");
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        URI url = webSearch.addParametersAndBuildURI("Test");

        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(httpClient.execute(new HttpGet(url))).thenReturn(httpResponse);

        Mockito.when(httpResponse.getStatusLine()).thenReturn(Mockito.mock(StatusLine.class));
        Mockito.when(httpResponse.getStatusLine().getStatusCode()).thenReturn(200);
        Assertions.assertThrows(UnknownHostException.class, () ->
                webSearch.queryAndGetResultCount("Test")
        );

    }


    private WebSearch buildWebSearch(String searchTerm) throws IOException, IllegalArgException {
        return new WebSearch() {
            @Override
            String setApiKey() {
                return searchTerm;
            }

            @Override
            long buildResponseFromJson(JSONObject jsonResponsr) {
                return 0;
            }

            @Override
            String setAPIHost() {
                return "1234";
            }

            @Override
            String setAPIPath() {
                return "weretete";
            }
        };

    }
}
