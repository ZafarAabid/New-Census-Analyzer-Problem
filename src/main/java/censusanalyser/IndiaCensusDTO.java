package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusDTO {
    public IndiaCensusDTO() {
    }

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int densityPerSqKm;

    public IndiaCensusDTO(String state, long population, double populationDensity, double totalArea) {
        this.state = state;
        this.population= (int) population;
        this.densityPerSqKm = (int) populationDensity;
        this.areaInSqKm = (int) totalArea;

    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}
