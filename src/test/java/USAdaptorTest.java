import censusanalyser.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USAdaptorTest {
    private static final String US_STATE_CODE_CSV = "/home/user/workspace/newCode/src/test/resources/USCensusData.csv";
    @Test
    public void givenData_WhenCensusDataPassesOnlyOneCSVFile_ThrowException() {
        try {
            Map<String, CensusDAO> indiaCensusData = new USCensusAdaptor().loadCensusData(US_STATE_CODE_CSV);
            Assert.assertEquals(51, indiaCensusData.size());
        }catch (CensusAnalyserException e){

        }
    }
}
