package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class USCensusAdapter extends CensusAdapter
{
    Map<String,CensusDAO> censusDAOMap=null;
    public  USCensusAdapter()
    {
        censusDAOMap=new HashMap<>();
    }

    @Override
    public Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CSVBuilderException
    {

        censusDAOMap = super.loadCensusData(USCensusData.class, csvFilePath[0]);
        return censusDAOMap;
    }

    protected  <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CSVBuilderException
    {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        Iterator<E> csvFileIterator = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0])))
        {
            ICVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            try
            {
                csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
                Iterator<E> finalCsvFileIterator = csvFileIterator;
                Iterable<E> csvIterable = () -> finalCsvFileIterator;
                StreamSupport.stream
                        (csvIterable.spliterator(), false)
                        .map(USCensusData.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            catch (RuntimeException e)
            {
                throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
            }
            catch (CSVBuilderException e)
            {
                e.printStackTrace();
            }
            return censusMap;
        }
        catch (CSVBuilderException e)
        {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        catch (IOException e)
        {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (RuntimeException e)
        {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }


}
