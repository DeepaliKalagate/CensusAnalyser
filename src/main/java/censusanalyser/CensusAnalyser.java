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

    Map<String, CensusDAO> censusMap = null;
    Map<StateCensusFieldName,Comparator<CensusDAO>> fieldNameComparatorMap=null;
    private Country country;

    public CensusAnalyser(Country country)
    {
        this.country = country;
    }

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


    public Map<String,CensusDAO> loadCensusData(Country country, String... csvFilePath) throws CSVBuilderException
    {
        CensusAdapter censusAdapter = CensusAdapterFactory.getAdapterObject(country);
        censusMap = censusAdapter.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);

        return censusMap;
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
                .map(censusDAO -> ((CensusDAO) censusDAO).getCensusDTO(Country.INDIA))
                .collect(toCollection(ArrayList::new));

        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }

    private void sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> censusComparator)
    {
        for (int i = 0; i < censusDAOS.size(); i++)
        {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++)
            {
                CensusDAO indiaCensusCSV1 = censusDAOS.get(j);
                CensusDAO indiaCensusCSV2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(indiaCensusCSV1, indiaCensusCSV2) > 0)
                {
                    censusDAOS.set(j, indiaCensusCSV2);
                    censusDAOS.set(j + 1, indiaCensusCSV1);
                }
            }
        }
    }
}
