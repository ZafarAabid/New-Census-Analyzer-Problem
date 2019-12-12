package censusanalyser.sortby;

import censusanalyser.CensusDAO;

import java.util.Comparator;

public class SortByDensity implements ISortBy {

    @Override
    public Comparator getComparator() {
        return Comparator.<CensusDAO,Double>comparing(census -> census.populationDensity,Comparator.reverseOrder());
    }
}
