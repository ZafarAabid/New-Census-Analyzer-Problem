package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder<E> {
    public Iterator<E> getCsvFileIterator(Reader reader, Class csvClass);
}
