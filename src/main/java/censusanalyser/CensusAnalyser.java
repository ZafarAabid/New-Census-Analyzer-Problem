package censusanalyser;

import OpenCsvBuilder.OpenCsvBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        /* JAVA 8 FEATURES ALLOW YOU IF YOU WANT TO USE ANY IO STREAM WITHIN TRY BLOCK, DECLARE IT IN TRY PARAMETER BLOCK SO THAT
         * IT WILL AUTOMATICALLY CLOSE IT AFTER ENDING ITS TRY BLOCK*/
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            Iterator<IndiaCensusCSV> IndiaCensusIterator = csvbuilder.getCsvFileIterator(reader, IndiaCensusCSV.class);
            return getCount(IndiaCensusIterator);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public int loadIndiaStateCode(String stateCodeCsv) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(stateCodeCsv));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            Iterator<IndiaStateCode> IndiaStateCodeIterator = csvbuilder.getCsvFileIterator(reader, IndiaStateCode.class);
            return this.getCount(IndiaStateCodeIterator);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator iterator) {
        Iterable<IndiaCensusCSV> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }
}
