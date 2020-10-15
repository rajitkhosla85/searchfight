package com.searchfight.callers;

import com.searchfight.utils.IllegalArgException;
import com.searchfight.utils.SearchConstants;
import com.searchfight.utils.SearchProperty;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BingSearchApi extends WebSearch {


    public BingSearchApi() throws IOException, IllegalArgException {
        super();
    }

    @Override
    String setApiKey() throws IOException, IllegalArgException {
        return SearchProperty.getPropertyValue(SearchConstants.BING_API_KEY);
    }

    @Override
    long buildResponseFromJson(JSONObject jsonResponse) {
        return jsonResponse.has(SearchConstants.WEB_PAGES)
                ? jsonResponse.getJSONObject(SearchConstants.WEB_PAGES).getLong(SearchConstants.ESTIMATED_MATCHES) : 0;

    }

    @Override
    String setAPIHost() throws IOException, IllegalArgException {
        return SearchProperty.getPropertyValue(SearchConstants.BING_API_HOST);
    }

    @Override
    String setAPIPath() throws IOException, IllegalArgException {
        return SearchProperty.getPropertyValue(SearchConstants.BING_API_PATH);
    }

    @Override
    List<Header> addAdditionalHeaders() throws IOException, IllegalArgException {
        return Collections.singletonList(new BasicHeader(SearchConstants.SUBSCRIPTION_KEY_HEADER, SearchProperty.getPropertyValue(SearchConstants.SUBSCRIPTION_KEY)));
    }
}
