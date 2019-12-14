package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import java.util.HashMap;
import java.util.Map;
public class USCensusAdapter extends CensusAdapter
{
    Map<String,CensusDAO> censusMap =new HashMap<>();
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException
    {
        censusMap=super.loadCensusData(USCensusData.class,csvFilePath[0]);
        return censusMap;
    }
}

