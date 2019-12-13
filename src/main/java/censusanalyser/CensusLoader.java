package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICVBuilder;
import org.apache.commons.collections.map.HashedMap;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader
{
    Map<String, CensusDAO> censusStateMap =new HashedMap();
    public Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CSVBuilderException
    {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return this.loadCensusData(IndiaCensusCSV.class,csvFilePath);
        else  if (country.equals(CensusAnalyser.Country.US))
           return this.loadCensusData(IndiaCensusCSV.class,csvFilePath);
        else throw new CSVBuilderException("Incorrect Country",CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
    }

    private  <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CSVBuilderException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));)
        {
            ICVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator=csvBuilder.getCSVFileIterator(reader,censusCSVClass);
            Iterable<E> censusCSVIterable = () -> csvFileIterator;
            if (censusCSVClass.getName().equals("censusAnalyser.IndiaCensusCSV"))
            {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            else if (censusCSVClass.getName().equals("censusAnalyser.USCensusData"))
            {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(USCensusData.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            if(csvFilePath.length==1) return censusStateMap;
              this.loadIndianStateCode(censusStateMap,csvFilePath[1]);
            return censusStateMap;
        }
        catch (IOException | RuntimeException | CSVBuilderException e)
        {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private int loadIndianStateCode(Map<String, CensusDAO> censusStateMap, String csvFilePath) throws CSVBuilderException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICVBuilder icvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> stateCodeIterator = icvBuilder.getCSVFileIterator(reader, IndiaStateCode.class);
            Iterable<IndiaStateCode> codeIterable=()->stateCodeIterator;
            StreamSupport.stream(codeIterable.spliterator(),false)
                    .filter(csvState-> this.censusStateMap.get(csvState.stateName)!=null)
                    .forEach(csvState-> this.censusStateMap.get(csvState.stateCode=csvState.stateCode));
            return this.censusStateMap.size();
        }
        catch (IOException | CSVBuilderException | RuntimeException e)
        {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.STATE_CODE_FILE_PROBLEM);
        }
    }
}
