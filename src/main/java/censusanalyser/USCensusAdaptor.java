package censusanalyser;

import java.util.Map;

public class USCensusAdaptor extends CensusAdaptor{
    @Override
    public Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String... filepath) throws CensusAnalyserException {
        Map <String, CensusDAO> censusDAOMap =  super.loadCensusData(USCensusCodeDAO.class,filepath[0]);
        return censusDAOMap;
    }
}
