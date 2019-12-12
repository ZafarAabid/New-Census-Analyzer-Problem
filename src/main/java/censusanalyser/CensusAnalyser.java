package censusanalyser;

import censusanalyser.sortby.ISortBy;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

     public enum Country {INDIA, US}
    Map<String, CensusDAO> censusMap = null;
    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
    }

    public Map loadCensusData(CensusAnalyser.Country country, String... filePath) throws CensusAnalyserException {
        return  CvsLoaderFactory.loadCensusData(country,filePath);
    }

    public String getStateWithSortByParameter(SortParameter.Parameter parameter) throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

        Comparator<CensusDAO> censusCSVComparator = SortParameter.getParameter(parameter);
        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        censusDAOS = sort(censusDAOS, censusCSVComparator);
        String sortedData = new Gson().toJson(censusDAOS);
        return sortedData;
    }

    public String getStateWithSortByParameter(ISortBy iSortBy) throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

        Comparator<CensusDAO> censusCSVComparator = iSortBy.getComparator();
        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        censusDAOS = sort(censusDAOS, censusCSVComparator);
        String sortedData = new Gson().toJson(censusDAOS);
        return sortedData;
    }

    private List sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> censusCSVComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                CensusDAO censusCSV1 = censusDAOS.get(j);
                CensusDAO censusCSV2 = censusDAOS.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    censusDAOS.set(j, censusCSV2);
                    censusDAOS.set(j + 1, censusCSV1);
                }
            }
        }
        return censusDAOS;
    }
}
