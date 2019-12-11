package censusanalyser.sortby;

import censusanalyser.IndiaCensusDAO;

import java.util.Comparator;

public class SortByDensity implements ISortBy {

    @Override
    public Comparator getComparator() {
        return Comparator.<IndiaCensusDAO,Double>comparing(census -> census.densityPerSqKm,Comparator.reverseOrder());
    }
}
