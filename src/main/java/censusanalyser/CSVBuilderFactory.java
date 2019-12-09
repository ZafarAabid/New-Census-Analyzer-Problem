package censusanalyser;

import OpenCsvBuilder.OpenCsvBuilder;

public class CSVBuilderFactory {
    public static ICSVBuilder createCsvbuilder() {
     return new OpenCsvBuilder();
    }
}
