package censusanalyser;

import censusanalyser.sortby.ISortBy;
import censusanalyser.sortby.SortByDensity;
import censusanalyser.sortby.SortByPopulation;
import censusanalyser.sortby.SortParameter;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    private Country country;

    public enum Country {INDIA, US}

    Map<String, CensusDAO> censusMap = null;

    public CensusAnalyser(Country country) {
        this.country = country;
        censusMap = new HashMap<>();
    }

    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
    }

    public Map loadCensusData(CvsLoaderFactory.Country country, String... filePath) throws CensusAnalyserException {
        CensusAdapter censusAdaptor = CvsLoaderFactory.createAdaptor(country);
        censusMap = censusAdaptor.loadCensusData(filePath);
        return censusMap;
    }

    public String getStateWithSortByParameter(SortParameter.Parameter parameter) throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusCSVComparator = SortParameter.getParameter(parameter);
        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        censusDAOS.sort(censusCSVComparator);
        List<Object> censusDto = new ArrayList<>();
        for (CensusDAO censusDAO : censusDAOS
        ) {
            censusDto.add(censusDAO.getCensusDTO(country));
        }
        String sortedData = new Gson().toJson(censusDAOS);
        return sortedData;
    }

    public String getStateWithSortByParameter(ISortBy iSortBy) throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusCSVComparator = iSortBy.getComparator();

        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        censusDAOS.sort(censusCSVComparator);
        List<Object> censusDto = new ArrayList<>();
        for (CensusDAO censusDAO : censusDAOS
        ) {
            censusDto.add(censusDAO.getCensusDTO(country));
        }
        String sortedData = new Gson().toJson(censusDto);
        return sortedData;
    }

    public String getStateWithDualSortByParameter() throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> PopulationComparator = new SortByPopulation().getComparator();
        Comparator<CensusDAO> DensityComparator = new SortByDensity().getComparator();

        List<CensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        List<CensusDAO> sortedList= sort(censusDAOS, PopulationComparator, DensityComparator);
        sortedList.forEach(System.out::println);
        String sortedData = new Gson().toJson(sortedList);
        return sortedData;
    }

    private List sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> populationComparater, Comparator<CensusDAO> densityComparater) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                CensusDAO censusCSV1 = censusDAOS.get(j);
                CensusDAO censusCSV2 = censusDAOS.get(j + 1);
                if (populationComparater.compare(censusCSV1, censusCSV2) == 0) {
                    if (densityComparater.compare(censusCSV1, censusCSV2) > 0) {
                        censusDAOS.set(j, censusCSV2);
                        censusDAOS.set(j + 1, censusCSV1);
                    }
                } else if (populationComparater.compare(censusCSV1, censusCSV2) > 0) {
                    censusDAOS.set(j, censusCSV2);
                    censusDAOS.set(j + 1, censusCSV1);
                }
            }
        }
        return censusDAOS;
    }
}
