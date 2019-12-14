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
public class IndiaCensusAdapter extends CensusAdapter
{
    Map<String,CensusDAO> censusDAOMap=new HashMap<>();
    public int loadIndiaStateCode(Map<String,CensusDAO> censusStateMap,String csvFilePath) throws CSVBuilderException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader, IndiaStateCode.class);
            Iterable<IndiaStateCode> csvIterable=()->censusCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState->censusDAOMap.get(csvState.stateName)!=null)
                    .forEach(csvState->censusDAOMap.get(csvState.stateName).stateCode=csvState.stateCode);
            return censusDAOMap.size();

        } catch (IOException | RuntimeException e)
        {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.STATE_CODE_FILE_PROBLEM);
        }
    }

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException
    {
        Map<String,CensusDAO> censusStateMap=super.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);
        if (csvFilePath.length !=2)
        {
            throw new CSVBuilderException("Not Sufficient file", CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        this.loadIndiaStateCode(censusStateMap,csvFilePath[1]);
        return censusStateMap;
    }
}
