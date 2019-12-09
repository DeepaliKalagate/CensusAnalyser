package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICVBuilder<E>
{
    public  Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException;
}
