package com.searchfight.model;

import com.searchfight.utils.IllegalArgException;
import com.searchfight.utils.QueryEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EachQueryResultTest {
    private List<EachQueryResult> eachQueryResultList = new ArrayList<>();

    @BeforeEach
    public void before() {
        eachQueryResultList.clear();

    }

    @Test
    void emptyTest() {
        Collections.sort(eachQueryResultList);
        Assertions.assertTrue(eachQueryResultList.isEmpty());
    }

    @Test
    void equalTests() throws IllegalArgException {
        eachQueryResultList.add(new EachQueryResult(QueryEngine.GOOGLE, 5000));
        eachQueryResultList.add(new EachQueryResult(QueryEngine.BING, 5000));
        Collections.sort(eachQueryResultList);
        Assertions.assertEquals(5000, eachQueryResultList.get(0).getResultCount());
        Assertions.assertTrue(eachQueryResultList.get(0).getResultCount() == (eachQueryResultList.get(1).getResultCount()));
    }

    @Test
    void negativeResultCount() {
        Assertions.assertThrows(IllegalArgException.class, () ->
                eachQueryResultList.add(new EachQueryResult(QueryEngine.GOOGLE, -5))
        );

    }

    @Test
    void getMethodVerification() throws IllegalArgException {
        eachQueryResultList.add(new EachQueryResult(QueryEngine.GOOGLE, 5000));
        Collections.sort(eachQueryResultList);
        Assertions.assertTrue(eachQueryResultList.get(0).equals(new EachQueryResult(QueryEngine.GOOGLE, 5000)));
        String toStringOutput = eachQueryResultList.get(0).toString();
        Assertions.assertTrue(toStringOutput.contains("5000"));
        Assertions.assertTrue(toStringOutput.contains(QueryEngine.GOOGLE.toString()));
        Assertions.assertEquals(Objects.hash(new EachQueryResult(QueryEngine.GOOGLE, 5000)), eachQueryResultList.hashCode());
    }

    @Test
    void differenceTestWith2Data() throws IllegalArgException {
        eachQueryResultList.add(new EachQueryResult(QueryEngine.GOOGLE, 6000));
        eachQueryResultList.add(new EachQueryResult(QueryEngine.BING, 5000));
        Collections.sort(eachQueryResultList);
        Assertions.assertEquals(6000, eachQueryResultList.get(0).getResultCount());
        Assertions.assertEquals(5000, eachQueryResultList.get(1).getResultCount());
        Assertions.assertEquals(QueryEngine.GOOGLE, eachQueryResultList.get(0).getQueryEngine());
        Assertions.assertEquals(QueryEngine.BING, eachQueryResultList.get(1).getQueryEngine());
        Assertions.assertTrue(eachQueryResultList.get(0).getResultCount() > (eachQueryResultList.get(1).getResultCount()));
    }
}
