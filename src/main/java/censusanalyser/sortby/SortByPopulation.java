package censusanalyser.sortby;

import censusanalyser.IndiaCensusDAO;

import java.util.Comparator;

public class SortByPopulation implements ISortBy {

    @Override
    public Comparator getComparator() {
        return Comparator.<IndiaCensusDAO,Long>comparing(census -> census.population);
    }
}
