package com.searchfight.main;

import com.searchfight.utils.IllegalArgException;
import com.searchfight.model.EachQueryResult;
import com.searchfight.model.Results;
import com.searchfight.utils.QueryEngine;
import com.searchfight.utils.SearchConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchFightProcessorTest {

    @Test
    public void validateArguments() throws IOException, IllegalArgException {

        Assertions.assertEquals("Please specify the search terms to get the Search Fight Results", new SearchFightProcessor().validateArguments(new String[]{}));
        Assertions.assertEquals(SearchConstants.EMPTY, new SearchFightProcessor().validateArguments(new String[]{"Test", "123"}));
    }

    @Test
    public void testPrintResultWith2SearchTerms() throws IOException, IllegalArgException {

        List<Results> results = new ArrayList<>();
        results.add(new Results(".net", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 500000), new EachQueryResult(QueryEngine.BING, 700000))));
        results.add(new Results("java", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 800000), new EachQueryResult(QueryEngine.BING, 600000))));
        Collections.sort(results);
        String resultToBePrinted = new SearchFightProcessor().printResults(results);
        StringBuilder expectedResult = new StringBuilder();
        for (Results result : results) {
            expectedResult.append(buildResult(result));
        }
        Assertions.assertEquals(expectedResult.toString(), resultToBePrinted);
    }

    @Test
    public void testPrintResultWith4SearchTerms() throws IOException, IllegalArgException {

        List<Results> results = new ArrayList<>();
        results.add(new Results(".net", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 500000), new EachQueryResult(QueryEngine.BING, 700000))));
        results.add(new Results("java", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 800000), new EachQueryResult(QueryEngine.BING, 600000))));
        results.add(new Results("python", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 400000), new EachQueryResult(QueryEngine.BING, 300000))));
        results.add(new Results("perl", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 300000), new EachQueryResult(QueryEngine.BING, 350000))));
        Collections.sort(results);
        String resultToBePrinted = new SearchFightProcessor().printResults(results);
        StringBuilder expectedResult = new StringBuilder();
        for (Results result : results) {
            expectedResult.append(buildResult(result));
        }
        Assertions.assertEquals(expectedResult.toString(), resultToBePrinted);
    }

    @Test
    public void testPrintWinnerWith2SearchTerms() throws IOException, IllegalArgException {

        List<Results> results = new ArrayList<>();
        results.add(new Results(".net", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 500000), new EachQueryResult(QueryEngine.BING, 700000))));
        results.add(new Results("java", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 800000), new EachQueryResult(QueryEngine.BING, 600000))));
        Collections.sort(results);
        String resultToBePrinted = new SearchFightProcessor().printEachSearchWinner(results);
        StringBuilder expectedResult = new StringBuilder();
        expectedResult.append(buildWinner(results));
        Assertions.assertEquals(buildWinner(results), resultToBePrinted);
    }

    @Test
    public void testPrintWinnerWithEmptyResult() throws IOException, IllegalArgException {
        String resultToBePrinted = new SearchFightProcessor().printEachSearchWinner(Collections.emptyList());
        Assertions.assertEquals(SearchConstants.EMPTY, resultToBePrinted);
    }

    @Test
    public void testPrintResultWithEmptyResult() throws IOException, IllegalArgException {
        String resultToBePrinted = new SearchFightProcessor().printResults(Collections.emptyList());
        Assertions.assertEquals(SearchConstants.EMPTY, resultToBePrinted);
    }

    @Test
    public void testPrintResultverifyInput() throws IOException, IllegalArgException {
        Assertions.assertTrue(new SearchFightProcessor().verifyInput(new String[]{}));
        Assertions.assertFalse(new SearchFightProcessor().verifyInput(new String[]{"Test"}));
        Assertions.assertFalse(new SearchFightProcessor().verifyInput(new String[]{"Test", "123"}));
    }

    @Test
    public void testPrintWinnerWith4SearchTerms() throws IOException, IllegalArgException {

        List<Results> results = new ArrayList<>();
        results.add(new Results(".net", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 500000), new EachQueryResult(QueryEngine.BING, 700000))));
        results.add(new Results("java", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 800000), new EachQueryResult(QueryEngine.BING, 600000))));
        results.add(new Results("python", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 400000), new EachQueryResult(QueryEngine.BING, 300000))));
        results.add(new Results("perl", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 300000), new EachQueryResult(QueryEngine.BING, 350000))));
        Collections.sort(results);
        String resultToBePrinted = new SearchFightProcessor().printEachSearchWinner(results);
        StringBuilder expectedResult = new StringBuilder();
        for (Results result : results) {
            expectedResult.append(buildResult(result));
        }
        Assertions.assertEquals(buildWinner(results), resultToBePrinted);
    }

    private String buildResult(Results results) {
        StringBuilder builder = new StringBuilder();
        builder.append(results.getSearchTerm() + SearchConstants.COLON);
        for (QueryEngine queryEngine : QueryEngine.values()) {
            EachQueryResult queryResult = results.getResultForParticularQueryEngine(queryEngine);
            builder.append(SearchConstants.SPACE + queryResult.getQueryEngine().getValue() + SearchConstants.COLON);
            builder.append(SearchConstants.SPACE + queryResult.getResultCount());
        }
        builder.append(SearchConstants.NEW_LINE);
        return builder.toString();
    }

    private String buildWinner(List<Results> results) throws IllegalArgException {
        StringBuilder builder = new StringBuilder();
        for (QueryEngine queryEngine : QueryEngine.values()) {
            List<Results> resultNew = new ArrayList<>();
            builder.append(queryEngine + SearchConstants.SPACE + "Winner" + SearchConstants.SPACE + SearchConstants.COLON);
            for (Results result : results) {
                resultNew.add(new Results(result.getSearchTerm(), Collections.singletonList(result.getResultForParticularQueryEngine(queryEngine))));
            }
            Collections.sort(resultNew);
            builder.append(SearchConstants.SPACE + resultNew.get(0).getSearchTerm() + SearchConstants.NEW_LINE);
        }
        return builder.toString();
    }
}
