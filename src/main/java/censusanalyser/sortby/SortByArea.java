package censusanalyser.sortby;

import censusanalyser.CensusDAO;

import java.util.Comparator;

public class SortByArea implements ISortBy {

    @Override
    public Comparator getComparator() {
               Comparator comparator = Comparator.<CensusDAO,Double>comparing(census -> census.totalArea,Comparator.reverseOrder());
        return comparator;
    }
}
