package censusanalyser;

public class CensusDAO {

    public String state;
    public String  stateCode;
    public long population;
    public double totalArea;
    public double populationDensity;


    public CensusDAO(IndiaCensusDTO indiaCensusDAO) {
        state = indiaCensusDAO.state;
        totalArea = indiaCensusDAO.areaInSqKm;
        populationDensity = indiaCensusDAO.densityPerSqKm;
        population = indiaCensusDAO.population;
    }

    public CensusDAO(USCensusCodeDAO censusCodeDAO) {
        state = censusCodeDAO.state;
        stateCode = censusCodeDAO.stateId;
        populationDensity = censusCodeDAO.populationDensity;
        totalArea = censusCodeDAO.totalArea;
    }




}
