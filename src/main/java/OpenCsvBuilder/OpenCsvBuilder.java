package OpenCsvBuilder;

import censusanalyser.CensusAnalyserException;
import censusanalyser.ICSVBuilder;
import censusanalyser.IndiaCensusCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCsvBuilder<E> implements ICSVBuilder {
    @Override
    public Iterator<E> getCsvFileIterator(Reader reader, Class csvClass)  {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
    }

}
