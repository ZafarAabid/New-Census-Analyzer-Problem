package censusanalyser;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... filepath) throws CensusAnalyserException {
        Map <String, CensusDAO> censusDAOMap =  super.loadCensusData(USCensusCodeDTO.class,filepath[0]);
        return censusDAOMap;
    }
}
