import censusanalyser.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class IndiaAdaptorTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_WITH_WRONG_DELIMITER = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData.txt";
    private static final String INDIA_CENSUS_CSV_FILE_PATH_WITH_NULL_VALUE = "/home/user/workspace/newCode/src/test/resources/IndiaStateCensusData_NullFile.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String STATE_CODE_CSV = "/home/user/workspace/newCode/src/test/resources/IndiaStateCode.csv";
    private static final String US_STATE_CODE_CSV = "/home/user/workspace/newCode/src/test/resources/USCensusData.csv";

    @Test
        public void givenData_WhenReadIndianCensusDataUsingAdapter_ShouldReturnOutput() {
            try {
                Map<String, CensusDAO> indiaCensusData = new IndiasCensusAdaptor().loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,STATE_CODE_CSV);
                Assert.assertEquals(29, indiaCensusData.size());
            }catch (CensusAnalyserException e){

            }
        }

    @Test
    public void givenData_WhenCensusDataPassesOnlyOneCSVFile_ThrowException() {
        try {
            Map<String, CensusDAO> indiaCensusData = new IndiasCensusAdaptor().loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, indiaCensusData.size());
        }catch (CensusAnalyserException e){
                Assert.assertEquals(e.type, CensusAnalyserException.ExceptionType.NO_SUCH_FILE_ERROR);
        }
    }
    @Test
    public void givenTestCase_ifWrongDelimiter_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH_WITH_WRONG_DELIMITER,STATE_CODE_CSV);
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
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
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
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH_WITH_NULL_VALUE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }



}


