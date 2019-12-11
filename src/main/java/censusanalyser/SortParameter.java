package censusanalyser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortParameter {
    enum Parameter {
        STATE, POPULATION, AREA, DENSITY;
    }

    static Map<SortParameter.Parameter, Comparator> sortParameterComparator = new HashMap<>();
    public static Comparator getParameter(SortParameter.Parameter parameter) {

        sortParameterComparator.put(Parameter.STATE, Comparator.<IndiaCensusDAO,String>comparing(census -> census.state));
        sortParameterComparator.put(Parameter.POPULATION, Comparator.<IndiaCensusDAO,Long>comparing(census -> census.population));
        sortParameterComparator.put(Parameter.AREA, Comparator.<IndiaCensusDAO,Long>comparing(census -> census.areaInSqKm));
        sortParameterComparator.put(Parameter.DENSITY, Comparator.<IndiaCensusDAO,Double>comparing(census -> census.densityPerSqKm));

        Comparator<IndiaCensusDAO> comparator = sortParameterComparator.get(parameter);
        return comparator;
    }

}