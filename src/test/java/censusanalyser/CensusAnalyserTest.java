package censusanalyser;

import censusanalyser.sortby.*;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_WITH_WRONG_DELIMITER = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData.txt";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_WITH_NULL_VALUE = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData_NullFile.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String STATE_CODE_CSV = "/home/user/workspace/newCode/src/test/resources/IndiaStateCode.csv";
    private static final String US_STATE_CODE_CSV = "/home/user/workspace/newCode/src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            Map indiaCensusData = censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, STATE_CODE_CSV);
            Assert.assertEquals(29, indiaCensusData.size());
        } catch (CensusAnalyserException e) {
            System.out.println("EXCe");
        }
    }

    @Test
    public void givenTestCase_ifWrongDelimiter_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithNullFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH_WITH_NULL_VALUE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_SUCH_FILE_ERROR, e.type);
        }
    }

    @Test
    public void givenIndianStateCsv__shouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            Map indiaCensusData = censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, STATE_CODE_CSV);
            Assert.assertEquals(29, indiaCensusData.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenData_whenSortByName_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, STATE_CODE_CSV);
            ISortBy sortBy = new SortByState();

            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(SortParameter.Parameter.STATE);
            CensusDAO[] CsvDataList = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Test
    public void givenData_whenSortedByPopulation_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, STATE_CODE_CSV);
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(SortParameter.Parameter.POPULATION);
            CensusDAO[] CsvDataList = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", CsvDataList[28].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Test
    public void givenData_whenSortedByArea_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, STATE_CODE_CSV);
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(SortParameter.Parameter.AREA);
            IndiaCensusDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDTO[].class);
            Assert.assertEquals("Goa", CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Test
    public void givenData_whenSortedByDensity_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, STATE_CODE_CSV);
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(SortParameter.Parameter.DENSITY);
            IndiaCensusDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDTO[].class);
            Assert.assertEquals("Arunachal Pradesh", CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Test
    public void givenData_whenReverseSortedByAreaUsingInterface_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, STATE_CODE_CSV);
            ISortBy iSortBy = new SortByArea();
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(iSortBy);
            IndiaCensusDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDTO[].class);
            Assert.assertEquals("Rajasthan", CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Test
    public void givenData_WhenReadUSCensusData_ShouldReturnOutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            Map indiaCensusData = censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US, US_STATE_CODE_CSV);
            Assert.assertEquals(51, indiaCensusData.size());
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenData_WhenReadIndianCensusDataUsingAdapter_ShouldReturnOutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            Map indiaCensusData = censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US, US_STATE_CODE_CSV);
            Assert.assertEquals(51, indiaCensusData.size());
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenData_whenReverseSortedByPopulationWithDensity_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US, US_STATE_CODE_CSV);
            ISortBy iSortBy = new SortByDensity();
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(iSortBy);
            IndiaCensusDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDTO[].class);
            Assert.assertEquals("District of Columbia", CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Test
    public void givenData_whenReverseSortedByDensityUsingInterface_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US, US_STATE_CODE_CSV);
            ISortBy iSortBy = new SortByDensity();
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(iSortBy);
            IndiaCensusDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDTO[].class);
            Assert.assertEquals("District of Columbia", CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

}