package censusanalyser;

public class IndiaCensusDAO {

    public String state;
    public String  stateCode;
    public long population;
    public long areaInSqKm;
    public double densityPerSqKm;


    public IndiaCensusDAO(IndiaCensusDTO indiaCensusDAO) {
        state = indiaCensusDAO.state;
        areaInSqKm = indiaCensusDAO.areaInSqKm;
        densityPerSqKm = indiaCensusDAO.densityPerSqKm;
        population = indiaCensusDAO.population;
    }

}
