package censusanalyser;

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
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            Iterator<IndiaCensusCSV> censusCSVIterator = getCsvFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
            int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return numOfEntries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    public int loadIndiaStateCode(String stateCodeCsv) throws CensusAnalyserException {
        try(Reader reader = Files.newBufferedReader(Paths.get(stateCodeCsv));
        ) {
            Iterator<IndiaStateCode> IndiaStateCodeIterator = getCsvFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> csvIterable = () -> IndiaStateCodeIterator;
            int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return numOfEntries;

        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> Iterator<E> getCsvFileIterator(Reader reader,Class csvClass)
    {

        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return  csvToBean.iterator();
    }
}
