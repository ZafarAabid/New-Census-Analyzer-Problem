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

    public CensusDAO(USCensusCodeDTO censusCodeDAO) {
        state = censusCodeDAO.state;
        stateCode = censusCodeDAO.stateId;
        populationDensity = censusCodeDAO.populationDensity;
        totalArea = censusCodeDAO.totalArea;
        this.population = censusCodeDAO.population;
    }

    public Object getCensusDTO(CensusAnalyser.Country country){
        if (country.equals(CensusAnalyser.Country.INDIA)){
            return new IndiaCensusDTO(state,population,populationDensity,totalArea);
        }
        else if (country.equals(CensusAnalyser.Country.US)){
            return new USCensusCodeDTO(state,stateCode,population,populationDensity,totalArea);
        }
        return null;
    }

    @Override
    public String toString() {
        return "CensusDAO{" +
                "state='" + state + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", population=" + population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }
}
