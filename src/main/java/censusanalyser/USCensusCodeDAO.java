package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCodeDAO {
// State Id,State,Population,Housing units,Total area,Water area,Land area,Population Density,Housing Density

    @CsvBindByName(column = "State")
    public String state;
    @CsvBindByName(column = "State Id")
    public String stateId;
    @CsvBindByName(column = "Population")
    public int population;
    @CsvBindByName(column = "Total area")
    public double totalArea;
    @CsvBindByName(column = "Population Density")
    public double populationDensity;

}
