package com.searchfight.callers;

import com.searchfight.utils.IllegalArgException;
import com.searchfight.utils.SearchConstants;
import com.searchfight.utils.SearchProperty;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GoogleSearchApi extends WebSearch {


    public GoogleSearchApi() throws IOException, IllegalArgException {
        super();
    }

    @Override
    String setApiKey() throws IOException, IllegalArgException {
        return SearchProperty.getPropertyValue(SearchConstants.GOOGLE_API_KEY);
    }

    @Override
    public long buildResponseFromJson(JSONObject jsonResponse) {
        return jsonResponse.getJSONObject(SearchConstants.SEARCH_INFORMATION).getLong(SearchConstants.TOTAL_RESULTS);

    }

    @Override
    String setAPIHost() throws IOException, IllegalArgException {
        return SearchProperty.getPropertyValue(SearchConstants.API_HOST);
    }

    @Override
    String setAPIPath() throws IOException, IllegalArgException {
        return SearchProperty.getPropertyValue(SearchConstants.API_PATH);
    }

    @Override
    List<NameValuePair> addAdditionalParameters() throws IOException, IllegalArgException {
        NameValuePair valuePair = new BasicNameValuePair(SearchConstants.CX, SearchProperty.getPropertyValue(SearchConstants.SEARCH_ENGINE_ID));
        return Collections.singletonList(valuePair);
    }
}
