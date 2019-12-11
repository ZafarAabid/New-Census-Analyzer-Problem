package censusanalyser;

public class IndiaCensusDTO {

    public double densityPerSqKm;
    public long population;
    public long areaInSqKm;
    public String state;

    public IndiaCensusDTO(IndiaCensusDAO indiaCensusDAO) {
        state = indiaCensusDAO.state;
        areaInSqKm = indiaCensusDAO.areaInSqKm;
        densityPerSqKm = indiaCensusDAO.densityPerSqKm;
        population = indiaCensusDAO.population;
    }

}
