package com.searchfight.main;

import com.searchfight.utils.IllegalArgException;
import com.searchfight.callers.BingSearchApi;
import com.searchfight.callers.GoogleSearchApi;
import com.searchfight.model.EachQueryResult;
import com.searchfight.model.Results;
import com.searchfight.utils.QueryEngine;
import com.searchfight.utils.SearchConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.ServiceNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchFightProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EachQueryResult.class);
    private final GoogleSearchApi googleSearchApi;
    private final BingSearchApi bingSearchApi;

    SearchFightProcessor() throws IOException,IllegalArgException {
        this.googleSearchApi = new GoogleSearchApi();
        this.bingSearchApi = new BingSearchApi();
    }

    void searchFight(String[] args) throws URISyntaxException, IOException, ServiceNotFoundException,IllegalArgException {
        if (verifyInput(args)) {
            return;
        }
        List<Results> results = new ArrayList<>();
        for (String searchTerm : Arrays.asList(args)) {
            results.add(new Results(searchTerm, findSearchTermHits(searchTerm)));
        }
        Collections.sort(results);
        System.out.println(printResults(results));
        System.out.println(printEachSearchWinner(results));
        System.out.println(SearchConstants.TOTAL_WINNER + SearchConstants.COLON + SearchConstants.SPACE + results.get(0).getSearchTerm());

    }

    boolean verifyInput(String[] args) {
        if (StringUtils.isEmpty(validateArguments(args))) {
            System.out.print(SearchConstants.PROCESSING_THE_RESULT);
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    List<EachQueryResult> findSearchTermHits(String searchTerm) throws ServiceNotFoundException, IOException, IllegalArgException, URISyntaxException {
        List<EachQueryResult> eachQueryResults = new ArrayList<>();
        eachQueryResults.add(new EachQueryResult(QueryEngine.GOOGLE, googleSearchApi.queryAndGetResultCount(searchTerm)));
        eachQueryResults.add(new EachQueryResult(QueryEngine.BING, bingSearchApi.queryAndGetResultCount(searchTerm)));
        return eachQueryResults;
    }

    String printResults(List<Results> results) {
        StringBuilder builder = new StringBuilder();
        for (Results result : results) {
            builder.append(result.getSearchTerm() + SearchConstants.COLON);
            for (QueryEngine queryEngine : QueryEngine.values()) {
                EachQueryResult queryResult = result.getResultForParticularQueryEngine(queryEngine);
                builder.append(SearchConstants.SPACE + queryResult.getQueryEngine().getValue() + SearchConstants.COLON);
                builder.append(SearchConstants.SPACE + queryResult.getResultCount());
            }
            builder.append(SearchConstants.NEW_LINE);
        }
        return builder.toString();
    }

    String printEachSearchWinner(List<Results> results)throws IllegalArgException  {
        if (results.isEmpty()) {
            return SearchConstants.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        for (QueryEngine queryEngine : QueryEngine.values()) {
            List<Results> resultNew = new ArrayList<>();
            builder.append(queryEngine + SearchConstants.SPACE + "Winner" + SearchConstants.SPACE + SearchConstants.COLON);
            for (Results result : results) {
                resultNew.add(new Results(result.getSearchTerm(), Collections.singletonList(result.getResultForParticularQueryEngine(queryEngine))));
            }
            Collections.sort(resultNew);
            if (!resultNew.isEmpty()) {
                builder.append(SearchConstants.SPACE + resultNew.get(0).getSearchTerm() + SearchConstants.NEW_LINE);
            }
        }
        return builder.toString();
    }

    String validateArguments(String[] args) {
        if (args.length == 0) {
            LOGGER.info(SearchConstants.SPECIFY_THE_SEARCH);
            return SearchConstants.SPECIFY_THE_SEARCH;
        } else {
            return SearchConstants.EMPTY;
        }
    }

}
