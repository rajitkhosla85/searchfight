package com.searchfight.callers;

import com.searchfight.utils.IllegalArgException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.List;

public class BingSeachApiTest {

    @Test
    public void verifyAddAddtionalHeaders() throws IOException, IllegalArgException {
        BingSearchApi bingSearchApi = new BingSearchApi();
        List<Header> addAdditionalHeaders = bingSearchApi.addAdditionalHeaders();
        Assertions.assertTrue(!addAdditionalHeaders.isEmpty());
        Assertions.assertEquals(1, addAdditionalHeaders.size());
        Assertions.assertEquals("Ocp-Apim-Subscription-Key", addAdditionalHeaders.get(0).getName());

    }

    @Test
    public void verifyJSONResponse() throws IOException, IllegalArgException {
        BingSearchApi bingSearchApi = new BingSearchApi();
        Assertions.assertEquals(210000000, bingSearchApi.buildResponseFromJson(new JSONObject("{\"webPages\":{\"totalEstimatedMatches\":210000000,\"webSearchUrl\":\"https://www.bing.com/search?q=Donald+Trump\"}}")));
    }

    @Test
    public void verifyJSONEmptyResponse() throws IOException, IllegalArgException {
        BingSearchApi bingSearchApi = new BingSearchApi();
        Assertions.assertThrows(JSONException.class, () ->
                bingSearchApi.buildResponseFromJson(new JSONObject(""))
        );
    }

    @Test
    public void verifyJSONWithNoWebPage() throws IOException, IllegalArgException {
        BingSearchApi bingSearchApi = new BingSearchApi();
        Assertions.assertEquals(0,
                bingSearchApi.buildResponseFromJson(new JSONObject("{\"webPages1\":{\"totalEstimatedMatches\":210000000,\"webSearchUrl\":\"https://www.bing.com/search?q=Donald+Trump\"}}")));

    }

    @Test
    public void verifyStringResponse() throws IOException, IllegalArgException {
        BingSearchApi bingSearchApi = new BingSearchApi();
        Assertions.assertThrows(JSONException.class, () ->
                bingSearchApi.buildResponseFromJson(new JSONObject("{\"webPages\":{\"totalEstimatedMatches\":test,\"webSearchUrl\":\"https://www.bing.com/search?q=Donald+Trump\"}}")));
    }

    @Test
    public void verifyJSONWithNoSearchResultCount() throws IOException, IllegalArgException {
        BingSearchApi bingSearchApi = new BingSearchApi();
        Assertions.assertThrows(JSONException.class, () -> bingSearchApi.buildResponseFromJson(new JSONObject("{\"webPages\":{\"123\":210000000,\"webSearchUrl\":\"https://www.bing.com/search?q=Donald+Trump\"}}")));
    }

    @Test
    public void setBasicTest() throws IOException, IllegalArgException {
        GoogleSearchApi googleSearchApi = new GoogleSearchApi();
        Assertions.assertTrue(StringUtils.isNotEmpty(googleSearchApi.getBuilderInfo()));
    }

}
