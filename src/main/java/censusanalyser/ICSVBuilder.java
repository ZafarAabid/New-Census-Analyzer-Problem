package censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {
    public Iterator<E> getCsvFileIterator(Reader reader, Class csvClass) throws CSVBuilderException;
    public List<E> getCsvFileList(Reader reader, Class csvClass) throws CSVBuilderException;
}
