package censusanalyser.sortby;

import censusanalyser.CensusDAO;

import java.util.Comparator;

public class SortByPopulation implements ISortBy {

    @Override
    public Comparator getComparator() {
        return Comparator.<CensusDAO,Long>comparing(census -> census.population,Comparator.reverseOrder());
    }
}
