package OpenCsvBuilder;

import censusanalyser.CSVBuilderException;
import censusanalyser.ICSVBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCsvBuilder<E> implements ICSVBuilder {
    @Override
    public Iterator<E> getCsvFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            return this.getCSVBean(reader, csvClass).iterator();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    @Override
    public List getCsvFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader, csvClass).parse();
    }

    private CsvToBean getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean;
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
