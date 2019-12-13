package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;
import java.util.*;
import static java.util.stream.Collectors.toCollection;
public class CensusAnalyser
{
    public enum Country
    {
        INDIA,US
    }
    Map<String, CensusDAO> censusMap = new HashMap<>();
    Map<StateCensusFieldName,Comparator<CensusDAO>> fieldNameComparatorMap=null;
    Country country;
    public CensusAnalyser()
    {
        this.fieldNameComparatorMap=new HashedMap();
        this.fieldNameComparatorMap.put(StateCensusFieldName.State,Comparator.comparing(censusField->censusField.state));
        this.fieldNameComparatorMap.put(StateCensusFieldName.Population,
                                Comparator.comparing(censusField->censusField.population,Comparator.reverseOrder()));
        this.fieldNameComparatorMap.put(StateCensusFieldName.DensityPerSqKm,
                                Comparator.comparing(censusField->censusField.densityPerSqKm,Comparator.reverseOrder()));
        this.fieldNameComparatorMap.put(StateCensusFieldName.AreaInSqKm,
                                Comparator.comparing(censusField->censusField.areaInSqKm,Comparator.reverseOrder()));
    }

    public int loadCensusData(Country country,String... csvFilePath) throws CSVBuilderException
    {
        CensusAdapter censusAdapter = CensusAdapterFactory.getAdapterObject(country);
        this.country=country;
        censusMap = censusAdapter.loadCensusData(csvFilePath);
        return censusMap.size();
    }

    public String genericSortMethod(StateCensusFieldName fieldName) throws CSVBuilderException
    {
        if (censusMap == null || censusMap.size() == 0)
        {
            throw new CSVBuilderException("No Census Data",
                    CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList censusDTOS= (ArrayList) censusMap.values().stream()
                .sorted(this.fieldNameComparatorMap.get(fieldName))
                .map(censusDAO -> ((CensusDAO) censusDAO).getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }
}
