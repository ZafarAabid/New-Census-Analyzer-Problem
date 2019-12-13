package censusanalyser;

import com.csvBuilder.CSVBuilderException;
import com.csvBuilder.CSVBuilderFactory;
import com.csvBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {



    public  abstract Map<String, CensusDAO> loadCensusData(String... filepath) throws CensusAnalyserException;

    public <E> Map loadCensusData(Class<E> className, String... filePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath[0]));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            Iterator<E> IndiaStateCodeIterator = csvbuilder.getCsvFileIterator(reader, className);
            Iterable<E> csvIterable = () -> IndiaStateCodeIterator;
            if (className.getName().equals("censusanalyser.IndiaCensusDTO")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusDTO.class::cast)
                        .forEach(IndiaCensusDTO -> censusMap.put(IndiaCensusDTO.state, new CensusDAO(IndiaCensusDTO)));
            } else if (className.getName().equals("censusanalyser.USCensusCodeDTO")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCodeDTO.class::cast)
                        .forEach(USCensusCodeDAO -> censusMap.put(USCensusCodeDAO.state, new CensusDAO(USCensusCodeDAO)));
            }
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
