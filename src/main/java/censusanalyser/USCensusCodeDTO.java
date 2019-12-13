package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCodeDTO {
// State Id,State,Population,Housing units,Total area,Water area,Land area,Population Density,Housing Density

    public USCensusCodeDTO() {
    }

    @CsvBindByName(column = "State")
    public String state;
    @CsvBindByName(column = "State Id")
    public String stateId;
    @CsvBindByName(column = "Population")
    public long population;
    @CsvBindByName(column = "Total area")
    public double totalArea;
    @CsvBindByName(column = "Population Density")
    public double populationDensity;

    public USCensusCodeDTO(String state, String stateCode, long population, double populationDensity, double totalArea) {
    this.state = state;
    this.population = population;
    this.populationDensity = populationDensity;
    this.totalArea = totalArea ;
    this.stateId = stateCode;
    }
}
