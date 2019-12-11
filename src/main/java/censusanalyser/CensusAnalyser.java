package censusanalyser;

import com.csvBuilder.CSVBuilderException;
import com.csvBuilder.CSVBuilderFactory;
import com.csvBuilder.ICSVBuilder;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        /* JAVA 8 FEATURES ALLOW YOU IF YOU WANT TO USE ANY IO STREAM WITHIN TRY BLOCK, DECLARE IT IN TRY PARAMETER BLOCK SO THAT
         * IT WILL AUTOMATICALLY CLOSE IT AFTER ENDING ITS TRY BLOCK*/
        if (new File(csvFilePath).length() == 0 | new File(csvFilePath).length() == 0) {
            throw new CensusAnalyserException("Empty or null file is passed",CensusAnalyserException.ExceptionType.NULL_FILE_ERROR);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            ICSVBuilder csvbuilder = CSVBuilderFactory.createCsvbuilder();
            Iterator<IndiaCensusDTO> csvfileiterator = csvbuilder.getCsvFileIterator(reader, IndiaCensusDTO.class);

            Iterable<IndiaCensusDTO> csvIterable = () -> csvfileiterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(IndidCensusDTO -> censusList.add( new IndiaCensusDAO(IndidCensusDTO)));

            return censusList.size();
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
            return this.getCount(IndiaStateCodeIterator);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    public String getStateWithSortByParameter(String csvFilePath, String parameter) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            if (censusList == null | censusList.size() == 0) {
                throw new CensusAnalyserException("Null file", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }
            Comparator<IndiaCensusDAO> censusCSVComparator = getSortingField(parameter);
            this.sort(censusCSVComparator);
            String sortedData = new Gson().toJson(this.censusList);
            return sortedData;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private Comparator getSortingField(String parameter) throws CensusAnalyserException {
        parameter = parameter.toLowerCase();
        Comparator<IndiaCensusDAO> censusDAOComparator = null;
        switch (parameter) {
            case "state":
                censusDAOComparator = Comparator.comparing(census -> census.state);
                break;
            case "population":
                censusDAOComparator = Comparator.comparing(census -> census.population);
                break;
            case "area":
                censusDAOComparator = Comparator.comparing(census -> census.areaInSqKm);
                break;
            case "density":
                censusDAOComparator = Comparator.comparing(census -> census.densityPerSqKm);
                break;
            default:
                throw new CensusAnalyserException("improper Field Name", CensusAnalyserException.ExceptionType.IMPROPER_PARAMETER_TYPE_ERROR);
        }
        return censusDAOComparator;
    }

    private <E> int getCount(Iterator iterator) {
        Iterable<IndiaCensusDTO> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }

    private void sort(Comparator<IndiaCensusDAO> censusCSVComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO censusCSV1 = censusList.get(j);
                IndiaCensusDAO censusCSV2 = censusList.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    censusList.set(j, censusCSV2);
                    censusList.set(j + 1, censusCSV1);
                }
            }
        }
    }
}
