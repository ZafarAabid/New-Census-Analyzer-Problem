package censusanalyser;

import censusanalyser.*;
import censusanalyser.sortby.*;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class USAdapterTest {
    private static final String US_STATE_CODE_CSV = "/home/user/workspace/newCode/src/test/resources/USCensusData.csv";
    private static final String US_STATE_CODE_CSV_FILE_PATH_WITH_WRONG_DELIMITER="/home/user/workspace/newCode/src/test/resources/USCensusData.txt";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_WITH_NULL_VALUE = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData_NullFile.csv";

    @Test
    public void givenData_WhenCensusDataPassesOnlyOneCSVFile_ThrowException() {
        try {
            Map<String, CensusDAO> indiaCensusData = new USCensusAdapter().loadCensusData(US_STATE_CODE_CSV);
            Assert.assertEquals(51, indiaCensusData.size());
        }catch (CensusAnalyserException e){

        }
    }

    @Test
    public void givenTestCase_ifWrongDelimiter_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US,US_STATE_CODE_CSV_FILE_PATH_WITH_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithNullFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US,INDIA_CENSUS_CSV_FILE_PATH_WITH_NULL_VALUE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenData_whenSortByName_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US,US_STATE_CODE_CSV);
            ISortBy sortBy = new SortByState();

            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(SortParameter.Parameter.STATE);
            CensusDAO[] CsvDataList = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Alabama", CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Test
    public void givenData_whenReverseSortedByAreaUsingInterface_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US,US_STATE_CODE_CSV);
            ISortBy iSortBy = new SortByArea();
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(iSortBy);
            IndiaCensusDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDTO[].class);
            System.out.println(CsvDataList[0].state);
            Assert.assertEquals("Alaska",CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    @Test
    public void givenData_whenReverseSortedByDensityUsingInterface_ShouldReturnSortedoutput() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CvsLoaderFactory.Country.US,US_STATE_CODE_CSV);
            ISortBy iSortBy = new SortByDensity();
            String sortedCensusData = censusAnalyser.getStateWithSortByParameter(iSortBy);
            IndiaCensusDTO[] CsvDataList = new Gson().fromJson(sortedCensusData, IndiaCensusDTO[].class);
            Assert.assertEquals("District of Columbia",CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }


}
