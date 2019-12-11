package censusanalyser.sortby;

import censusanalyser.IndiaCensusDAO;

import java.util.Comparator;

public class SortByState implements ISortBy {

    @Override
    public Comparator getComparator() {
        return Comparator.<IndiaCensusDAO, String>comparing(census -> census.state,Comparator.reverseOrder());
    }
}
