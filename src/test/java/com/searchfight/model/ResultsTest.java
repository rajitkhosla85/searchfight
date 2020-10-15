package com.searchfight.model;

import com.searchfight.utils.IllegalArgException;
import com.searchfight.utils.QueryEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ResultsTest {
    private List<Results> results = new ArrayList<>();

    @BeforeEach
    public void before() {
        results.clear();

    }

    @Test
    void emptyTest() {
        Collections.sort(results);
        Assertions.assertTrue(results.isEmpty());
    }

    @Test
    void equalTests() throws IllegalArgException {
        results.add(new Results("Test", Collections.singletonList(new EachQueryResult(QueryEngine.GOOGLE, 5000))));
        results.add(new Results("Test2", Collections.singletonList(new EachQueryResult(QueryEngine.BING, 5000))));
        Collections.sort(results);
        Assertions.assertEquals(5000, results.get(0).getEachQueryResults().get(0).getResultCount());
        Assertions.assertEquals(2, results.size());
        Assertions.assertEquals(5000, results.get(1).getEachQueryResults().get(0).getResultCount());
    }

    @Test
    void getMethodVerification() throws IllegalArgException {
        results.add(new Results("Test", Collections.singletonList(new EachQueryResult(QueryEngine.GOOGLE, 5000))));

        Assertions.assertEquals("Test", results.get(0).getSearchTerm());
        Assertions.assertTrue(results.get(0).equals(new Results("Test", Collections.singletonList(new EachQueryResult(QueryEngine.GOOGLE, 5000)))));
        String toStringOutput = results.get(0).toString();
        Assertions.assertTrue(toStringOutput.contains("Test"));
        Assertions.assertTrue(toStringOutput.contains("5000"));
        Assertions.assertTrue(toStringOutput.contains(QueryEngine.GOOGLE.toString()));
        Assertions.assertEquals(Objects.hash("Test", Collections.singletonList(new EachQueryResult(QueryEngine.GOOGLE, 5000))), results.get(0).hashCode());
    }

    @Test
    void emptySearchTerm() {
        Assertions.assertThrows(IllegalArgException.class, () -> results.add(new Results("", Collections.singletonList(new EachQueryResult(QueryEngine.BING, 5000)))));

    }

    @Test
    void spaceSearchTerm() {
        Assertions.assertThrows(IllegalArgException.class, () -> results.add(new Results(" ", Collections.singletonList(new EachQueryResult(QueryEngine.BING, 5000)))));

    }

    @Test
    void emptyEachQueryResult() {
        Assertions.assertThrows(IllegalArgException.class, () ->
                results.add(new Results("Test", Collections.emptyList()))
        );
        Assertions.assertThrows(IllegalArgException.class, () ->
                results.add(new Results("Test", null))
        );
    }


    @Test
    void twoSearchTerm() throws IllegalArgException {
        results.add(new Results(".net", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 500000), new EachQueryResult(QueryEngine.BING, 700000))));
        results.add(new Results("java", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 800000), new EachQueryResult(QueryEngine.BING, 600000))));
        Collections.sort(results);
        Assertions.assertEquals(800000, results.get(0).getEachQueryResults().get(0).getResultCount());
        Assertions.assertEquals(600000, results.get(0).getEachQueryResults().get(1).getResultCount());
        Assertions.assertEquals(QueryEngine.GOOGLE, results.get(0).getEachQueryResults().get(0).getQueryEngine());
        Assertions.assertEquals(QueryEngine.BING, results.get(0).getEachQueryResults().get(1).getQueryEngine());
        Assertions.assertEquals(700000, results.get(1).getEachQueryResults().get(0).getResultCount());
        Assertions.assertEquals(500000, results.get(1).getEachQueryResults().get(1).getResultCount());
        Assertions.assertEquals(QueryEngine.BING, results.get(1).getEachQueryResults().get(0).getQueryEngine());
        Assertions.assertEquals(QueryEngine.GOOGLE, results.get(1).getEachQueryResults().get(1).getQueryEngine());
        Assertions.assertEquals(2, results.size());
    }

    @Test
    void fourSearchTerm() throws IllegalArgException {
        results.add(new Results(".net", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 500000), new EachQueryResult(QueryEngine.BING, 700000))));
        results.add(new Results("java", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 800000), new EachQueryResult(QueryEngine.BING, 600000))));
        results.add(new Results("python", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 900000), new EachQueryResult(QueryEngine.BING, 600000))));
        results.add(new Results("perl", Arrays.asList(new EachQueryResult(QueryEngine.GOOGLE, 800000), new EachQueryResult(QueryEngine.BING, 11000000))));
        Collections.sort(results);
        //result 0
        Assertions.assertEquals(11000000, results.get(0).getEachQueryResults().get(0).getResultCount());
        Assertions.assertEquals(800000, results.get(0).getEachQueryResults().get(1).getResultCount());
        Assertions.assertEquals(QueryEngine.BING, results.get(0).getEachQueryResults().get(0).getQueryEngine());
        Assertions.assertEquals(QueryEngine.GOOGLE, results.get(0).getEachQueryResults().get(1).getQueryEngine());
        //result 1
        Assertions.assertEquals(900000, results.get(1).getEachQueryResults().get(0).getResultCount());
        Assertions.assertEquals(600000, results.get(1).getEachQueryResults().get(1).getResultCount());
        Assertions.assertEquals(QueryEngine.GOOGLE, results.get(1).getEachQueryResults().get(0).getQueryEngine());
        Assertions.assertEquals(QueryEngine.BING, results.get(1).getEachQueryResults().get(1).getQueryEngine());
        //result 2
        Assertions.assertEquals(800000, results.get(2).getEachQueryResults().get(0).getResultCount());
        Assertions.assertEquals(600000, results.get(2).getEachQueryResults().get(1).getResultCount());
        Assertions.assertEquals(QueryEngine.GOOGLE, results.get(2).getEachQueryResults().get(0).getQueryEngine());
        Assertions.assertEquals(QueryEngine.BING, results.get(2).getEachQueryResults().get(1).getQueryEngine());
        //result 3
        Assertions.assertEquals(700000, results.get(3).getEachQueryResults().get(0).getResultCount());
        Assertions.assertEquals(500000, results.get(3).getEachQueryResults().get(1).getResultCount());
        Assertions.assertEquals(QueryEngine.BING, results.get(3).getEachQueryResults().get(0).getQueryEngine());
        Assertions.assertEquals(QueryEngine.GOOGLE, results.get(3).getEachQueryResults().get(1).getQueryEngine());
        Assertions.assertEquals(4, results.size());
    }
}
