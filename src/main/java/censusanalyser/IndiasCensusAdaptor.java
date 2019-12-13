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

public class IndiasCensusAdaptor extends CensusAdaptor {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... filepath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusDAOMap= new HashMap<>();
        try {
            censusDAOMap = super.loadCensusData(IndiaCensusDTO.class, filepath[0]);
         censusDAOMap = this.loadIndiaStateCode(censusDAOMap, filepath[1]);
     }catch (IndexOutOfBoundsException e){
         throw new CensusAnalyserException("all require files are not provided",CensusAnalyserException.ExceptionType.NO_SUCH_FILE_ERROR);
     }
        return censusDAOMap;
    }

    public Map loadIndiaStateCode(Map<String ,CensusDAO> censusMap ,String stateCodeCsv) throws CensusAnalyserException {
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
