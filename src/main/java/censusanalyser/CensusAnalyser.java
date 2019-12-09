package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> indiaCensusList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        /* JAVA 8 FEATURES ALLOW YOU IF YOU WANT TO USE ANY IO STREAM WITHIN TRY BLOCK, DECLARE IT IN TRY PARAMETER BLOCK SO THAT
         * IT WILL AUTOMATICALLY CLOSE IT AFTER ENDING ITS TRY BLOCK*/
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            indiaCensusList = csvbuilder.getCsvFileList(reader, IndiaCensusCSV.class);
            return indiaCensusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
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
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public String getStateWithSortedData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            indiaCensusList = csvbuilder.getCsvFileList(reader, IndiaCensusCSV.class);
            if (indiaCensusList ==null | indiaCensusList.size()==0){
                throw new CensusAnalyserException("Null file",CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }
            Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(census -> census.state);
            this.sort(censusCSVComparator);
            String sortedData = new Gson().toJson(indiaCensusList);
            return sortedData;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator iterator) {
        Iterable<IndiaCensusCSV> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }

    private void sort( Comparator<IndiaCensusCSV> censusCSVComparator) {
        for (int i = 0; i < indiaCensusList.size() - 1; i++){
            for (int j = 0; j < indiaCensusList.size() - i - 1; j++) {
                IndiaCensusCSV censusCSV1 = indiaCensusList.get(j);
                IndiaCensusCSV censusCSV2 = indiaCensusList.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    indiaCensusList.set(j, censusCSV2);
                    indiaCensusList.set(j + 1, censusCSV1);
                }
            }
        }
    }
}
