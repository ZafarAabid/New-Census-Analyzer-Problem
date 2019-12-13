package censusanalyser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortParameter {
    public enum Parameter {
        STATE, POPULATION, AREA, DENSITY;
    }

    static Map<SortParameter.Parameter, Comparator> sortParameterComparator = new HashMap<>();
    public static Comparator getParameter(SortParameter.Parameter parameter) {

        sortParameterComparator.put(Parameter.STATE, Comparator.<CensusDAO,String>comparing(census -> census.state));
        sortParameterComparator.put(Parameter.POPULATION, Comparator.<CensusDAO,Long>comparing(census -> census.population));
        sortParameterComparator.put(Parameter.AREA, Comparator.<CensusDAO,Double>comparing(census -> census.totalArea));
        sortParameterComparator.put(Parameter.DENSITY, Comparator.<CensusDAO,Double>comparing(census -> census.populationDensity));

        Comparator<CensusDAO> comparator = sortParameterComparator.get(parameter);
        return comparator;
    }

}