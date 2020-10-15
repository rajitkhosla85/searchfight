package com.searchfight.callers;

import com.searchfight.utils.IllegalArgException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class GoogleSeachApiTest {


    @Test
    public void verifyAddAddtionalParamater() throws IOException, IllegalArgException {
        GoogleSearchApi googleSearchApi = new GoogleSearchApi();
        List<NameValuePair> nameValuePairs = googleSearchApi.addAdditionalParameters();
        Assertions.assertTrue(!nameValuePairs.isEmpty());
        Assertions.assertEquals(1, nameValuePairs.size());
        Assertions.assertEquals("cx", nameValuePairs.get(0).getName());

    }

    @Test
    public void verifyJSONResponse() throws IOException, IllegalArgException {
        GoogleSearchApi googleSearchApi = new GoogleSearchApi();
        long result = googleSearchApi.buildResponseFromJson(new JSONObject("{\"searchInformation\":{\"searchTime\":0.318552,\"totalResults\":\"112000000\",\"formattedTotalResults\":\"112,000,000\",\"formattedSearchTime\":\"0.32\"}}"));
        Assertions.assertEquals(112000000, result);
    }

    @Test
    public void verifyJSONEmptyResponse() throws IOException, IllegalArgException {
        GoogleSearchApi googleSearchApi = new GoogleSearchApi();
        Assertions.assertThrows(JSONException.class, () -> googleSearchApi.buildResponseFromJson(new JSONObject("")));
    }

    @Test
    public void verifyJSONWithNoSearchInfo() throws IOException, IllegalArgException {
        GoogleSearchApi googleSearchApi = new GoogleSearchApi();
        Assertions.assertThrows(JSONException.class, () ->
                googleSearchApi.buildResponseFromJson(new JSONObject("{\"searchInformation1\":{\"searchTime\":0.318552,\"totalResults\":\"112000000\",\"formattedTotalResults\":\"112,000,000\",\"formattedSearchTime\":\"0.32\"}}"))
        );
    }

    @Test
    public void verifyStringResponse() throws IOException, IllegalArgException {
        GoogleSearchApi googleSearchApi = new GoogleSearchApi();
        Assertions.assertThrows(JSONException.class, () ->
                googleSearchApi.buildResponseFromJson(new JSONObject("{\"searchInformation1\":{\"searchTime\":0.318552,\"totalResults\":\"Test\",\"formattedTotalResults\":\"112,000,000\",\"formattedSearchTime\":\"0.32\"}}"))
        );
    }

    @Test
    public void verifyJSONWithNoSearchResultCount() throws IOException, IllegalArgException {
        GoogleSearchApi googleSearchApi = new GoogleSearchApi();
        Assertions.assertThrows(JSONException.class, () -> googleSearchApi.buildResponseFromJson(new JSONObject("{\"searchInformation\":{\"searchTime\":0.318552,\"formattedTotalResults\":\"112,000,000\",\"formattedSearchTime\":\"0.32\"}}")));
    }

    @Test
    public void setBasicTest() throws IOException, IllegalArgException {
        GoogleSearchApi googleSearchApi = new GoogleSearchApi();
        Assertions.assertTrue(StringUtils.isNotEmpty(googleSearchApi.getBuilderInfo()));
    }

}
