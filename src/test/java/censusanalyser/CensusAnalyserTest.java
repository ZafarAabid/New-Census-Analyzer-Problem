package censusanalyser;

import censusanalyser.sortby.ISortBy;
import censusanalyser.sortby.SortByState;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_WITH_WRONG_DELIMITER = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData.txt";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_WITH_NULL_VALUE = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData_NullFile.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String STATE_CODE_CSV = "/home/user/workspace/newCode/src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenTestCase_ifWrongDelimiter_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NULL_FILE_ERROR, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NULL_FILE_ERROR, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithNullFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH_WITH_NULL_VALUE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NULL_FILE_ERROR, e.type);
        }
    }

    @Test
    public void givenIndianStateCsv__shouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            int numberOfEntries = censusAnalyser.loadIndiaStateCode(STATE_CODE_CSV);
            Assert.assertEquals(29, numberOfEntries);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenData_whenSortByName_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            ISortBy sortBy = new SortByState();

            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(SortParameter.Parameter.STATE);
            IndiaCensusDAO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }


    @Test
    public void givenData_whenSortedByPopulation_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(SortParameter.Parameter.POPULATION);
            IndiaCensusDAO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", CsvDataList[28].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Test
    public void givenData_whenSortedByArea_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
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
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndiaStateCode(STATE_CODE_CSV);
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
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndiaStateCode(STATE_CODE_CSV);
            ISortBy iSortBy = new SortByState();
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(iSortBy);
            IndiaCensusDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDTO[].class);
            Assert.assertEquals("Andhra Pradesh", CsvDataList[CsvDataList.length - 1].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }





}