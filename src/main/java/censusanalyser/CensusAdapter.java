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

public abstract class CensusAdapter
{
   public abstract Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CSVBuilderException;
    protected   <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CSVBuilderException
    {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));)
        {
            ICVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator=csvBuilder.getCSVFileIterator(reader,censusCSVClass);
            Iterable<E> censusCSVIterable = () -> csvFileIterator;
            if (censusCSVClass.getName().equals("censusAnalyser.IndiaCensusCSV"))
            {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            else if (censusCSVClass.getName().equals("censusAnalyser.USCensusData"))
            {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(USCensusData.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusMap;
        }
        catch (IOException | RuntimeException | CSVBuilderException e)
        {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
