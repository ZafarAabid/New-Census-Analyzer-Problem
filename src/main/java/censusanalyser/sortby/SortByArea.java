package censusanalyser.sortby;

import censusanalyser.CensusDAO;

import java.util.Comparator;

public class SortByArea implements ISortBy {

    @Override
    public Comparator getComparator() {
        return Comparator.<CensusDAO,Double>comparing(census -> census.totalArea,Comparator.reverseOrder());
    }
}
