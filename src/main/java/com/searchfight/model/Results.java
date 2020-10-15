package com.searchfight.model;

import com.searchfight.utils.IllegalArgException;
import com.searchfight.utils.QueryEngine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Results implements Comparable<Results> {
    private static final String VALUE_IS = "Value is ";
    private static final Logger LOGGER = LoggerFactory.getLogger(Results.class);
    private final String searchTerm;
    private final List<EachQueryResult> eachQueryResults;

    public Results(String searchTerm, List<EachQueryResult> eachQueryResults) throws IllegalArgException {
        validate(searchTerm, eachQueryResults);
        this.searchTerm = searchTerm;
        this.eachQueryResults = eachQueryResults;
    }

    private void validate(String searchTermValue, List<EachQueryResult> eachQueryResults) throws IllegalArgException  {
        validateSearchTerm(searchTermValue);
        validateEachQueryResult(eachQueryResults);

    }

    public EachQueryResult getResultForParticularQueryEngine(QueryEngine queryEngine) {
        EachQueryResult queryResultValue = null;
        for (EachQueryResult queryResult : this.getEachQueryResults()) {
            if (queryEngine == queryResult.getQueryEngine()) {
                queryResultValue = queryResult;
            }
        }
        return queryResultValue;
    }

    private void validateSearchTerm(String searchTermValue) throws IllegalArgException {
        if (StringUtils.isEmpty(searchTermValue.trim())) {
            IllegalArgException illegalArgException = new IllegalArgException("Search Term Cannot be Empty");
            LOGGER.error("Value is-", searchTermValue, illegalArgException);
            throw illegalArgException;

        }

    }

    private void validateEachQueryResult(List<EachQueryResult> eachQueryResults) throws  IllegalArgException {
        if (eachQueryResults == null || eachQueryResults.isEmpty()) {
            IllegalArgException illegalArgException = new IllegalArgException("Collection List Cannot be Empty");
            LOGGER.error(VALUE_IS, eachQueryResults, illegalArgException);
            throw illegalArgException;

        }

    }

    @Override
    public int compareTo(Results other) {
        Collections.sort(eachQueryResults);
        Collections.sort(other.eachQueryResults);
        if (eachQueryResults.get(0).getResultCount() > other.eachQueryResults.get(0).getResultCount()) {
            return -1;
        } else if (eachQueryResults.get(0).getResultCount() < other.eachQueryResults.get(0).getResultCount()) {
            return 1;
        } else {
            return 0;
        }
    }


    public String getSearchTerm() {
        return searchTerm;
    }

    public List<EachQueryResult> getEachQueryResults() {
        return eachQueryResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Results)) return false;
        Results results = (Results) o;
        return Objects.equals(searchTerm, results.searchTerm) &&
                Objects.equals(eachQueryResults, results.eachQueryResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchTerm, eachQueryResults);
    }

    @Override
    public String toString() {
        return "searchTerm-" + getSearchTerm() + " eachQueryResults-" + getEachQueryResults();
    }
}
