package com.searchfight.model;

import com.searchfight.utils.IllegalArgException;
import com.searchfight.utils.QueryEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class EachQueryResult implements Comparable<EachQueryResult> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EachQueryResult.class);
    private final QueryEngine queryEngine;
    private final long resultCount;

    public EachQueryResult(QueryEngine queryEngine, long resultCount) throws IllegalArgException {
        validate(resultCount);
        this.queryEngine = queryEngine;
        this.resultCount = resultCount;
    }

    private void validate(long resultCountValue) throws IllegalArgException {
        if (resultCountValue < 0) {
            IllegalArgException illegalArgException= new IllegalArgException("Result Count Value Cannot be Negative");
            LOGGER.error("Value is-" , resultCountValue , illegalArgException);
            throw illegalArgException;

        }
    }

    @Override
    public int compareTo(EachQueryResult other) {

        if (resultCount > other.resultCount) {
            return -1;
        } else if (resultCount < other.resultCount) {
            return 1;
        } else {
            return 0;
        }
    }

    public long getResultCount() {
        return resultCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EachQueryResult)) return false;
        EachQueryResult that = (EachQueryResult) o;
        return resultCount == that.resultCount &&
                queryEngine == that.queryEngine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryEngine, resultCount);
    }

    public QueryEngine getQueryEngine() {
        return queryEngine;
    }
    @Override
    public String toString() {
        return "queryEngine-"+getQueryEngine()+" count-"+getResultCount();
    }
}
