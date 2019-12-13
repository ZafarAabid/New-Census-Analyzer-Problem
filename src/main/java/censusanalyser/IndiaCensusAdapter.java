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

public class IndiaCensusAdapter extends CensusAdapter {
    Map<String, CensusDAO> censusMap = new HashMap<>();

    @Override
    public Map<String, CensusDAO> loadCensusData(String... filepath) throws CensusAnalyserException {
        return loadCensusData(IndiaCensusDTO.class,filepath);
    }

        public <E> Map loadCensusData(Class<E> className, String... filePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath[0]));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            Iterator<E> IndiaStateCodeIterator = csvbuilder.getCsvFileIterator(reader, className);
            Iterable<E> csvIterable = () -> IndiaStateCodeIterator;
            if (className.getName().equals("censusanalyser.IndiaCensusDTO")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusDTO.class::cast)
                        .forEach(IndiaCensusDTO -> censusMap.put(IndiaCensusDTO.state, new CensusDAO(IndiaCensusDTO)));
                censusMap = loadIndiaStateCode(filePath[1]);
            } else if (className.getName().equals("censusanalyser.USCensusCodeDAO")) {
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

    public Map loadIndiaStateCode(String stateCodeCsv) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(stateCodeCsv));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            Iterator<IndiaStateCodeDAO> IndiaStateCodeIterator = csvbuilder.getCsvFileIterator(reader, IndiaStateCodeDAO.class);
            Iterable<IndiaStateCodeDAO> csvIterable = () -> IndiaStateCodeIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusMap.get(csvState.state) != null)
                    .map(IndiaStateCodeDAO.class::cast)
                    .forEach(csvState -> censusMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
