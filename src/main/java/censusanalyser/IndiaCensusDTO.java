package censusanalyser;

public class IndiaCensusDTO {

    public double densityPerSqKm;
    public long population;
    public long areaInSqKm;
    public String state;

    public IndiaCensusDTO(IndiaCensusDAO indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

}
