package censusanalyser;

import censusanalyser.sortby.ISortBy;
import censusanalyser.sortby.SortByArea;
import com.csvBuilder.CSVBuilderException;
import com.csvBuilder.CSVBuilderFactory;
import com.csvBuilder.ICSVBuilder;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    Map<String, IndiaCensusDAO> censusMap = null;
    Map<SortParameter, Comparator> sortParameterComparator = new HashMap<>();

    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        /* JAVA 8 FEATURES ALLOW YOU IF YOU WANT TO USE ANY IO STREAM WITHIN TRY BLOCK, DECLARE IT IN TRY PARAMETER BLOCK SO THAT
         * IT WILL AUTOMATICALLY CLOSE IT AFTER ENDING ITS TRY BLOCK*/
        if (new File(csvFilePath).length() == 0 | new File(csvFilePath).length() == 0) {
            throw new CensusAnalyserException("Empty or null file is passed", CensusAnalyserException.ExceptionType.NULL_FILE_ERROR);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            Iterator<IndiaCensusDTO> csvfileiterator = csvbuilder.getCsvFileIterator(reader, IndiaCensusDTO.class);
            Iterable<IndiaCensusDTO> csvIterable = () -> csvfileiterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(IndidCensusDTO -> censusMap.put(IndidCensusDTO.state, new IndiaCensusDAO(IndidCensusDTO)));
            return censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    public int loadIndiaStateCode(String stateCodeCsv) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(stateCodeCsv));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            Iterator<IndiaStateCodeDAO> IndiaStateCodeIterator = csvbuilder.getCsvFileIterator(reader, IndiaStateCodeDAO.class);
            while (IndiaStateCodeIterator.hasNext()) {
                IndiaStateCodeDAO stateCodeDAO = IndiaStateCodeIterator.next();
                IndiaCensusDAO censusDAO = censusMap.get(stateCodeDAO.state);
                if (censusDAO == null) continue;
                censusDAO.stateCode = stateCodeDAO.stateCode;
            }
            return censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    public String getStateWithSortByParameter(SortParameter.Parameter parameter) throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

        Comparator<IndiaCensusDAO> censusCSVComparator = SortParameter.getParameter(parameter);
        List<IndiaCensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        censusDAOS = sort(censusDAOS, censusCSVComparator);
        String sortedData = new Gson().toJson(censusDAOS);
        return sortedData;
    }
    public String getStateWithSortByParameter(ISortBy iSortBy) throws CensusAnalyserException {
        if (censusMap == null | censusMap.size() == 0) {
            throw new CensusAnalyserException("Null file", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

        Comparator<IndiaCensusDAO> censusCSVComparator = iSortBy.getComparator();
        List<IndiaCensusDAO> censusDAOS = censusMap.values().stream().collect(Collectors.toList());
        censusDAOS = sort(censusDAOS, censusCSVComparator);
        String sortedData = new Gson().toJson(censusDAOS);
        return sortedData;
    }

    private List sort(List<IndiaCensusDAO> censusDAOS, Comparator<IndiaCensusDAO> censusCSVComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                IndiaCensusDAO censusCSV1 = censusDAOS.get(j);
                IndiaCensusDAO censusCSV2 = censusDAOS.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    censusDAOS.set(j, censusCSV2);
                    censusDAOS.set(j + 1, censusCSV1);
                }
            }
        }
        return censusDAOS;
    }
}
