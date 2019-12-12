package censusanalyser.sortby;

import censusanalyser.CensusDAO;

import java.util.Comparator;

public class SortByState implements ISortBy {

    @Override
    public Comparator getComparator() {
        return Comparator.<CensusDAO, String>comparing(census -> census.state,Comparator.reverseOrder());
    }
}
