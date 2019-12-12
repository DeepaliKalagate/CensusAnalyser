package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICVBuilder;
import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser
{
    Map<String, CensusDAO> censusStateMap = null;
    Map<StateCensusFieldName,Comparator<CensusDAO>> fieldNameComparatorMap=null;

    public CensusAnalyser()
    {
        this.fieldNameComparatorMap=new HashedMap();
        this.fieldNameComparatorMap.put(StateCensusFieldName.State,Comparator.comparing(censusField->censusField.state));
        this.fieldNameComparatorMap.put(StateCensusFieldName.Population,
                                Comparator.comparing(censusField->censusField.population,Comparator.reverseOrder()));
        this.fieldNameComparatorMap.put(StateCensusFieldName.DensityPerSqKm,
                                Comparator.comparing(censusField->censusField.populationDensity,Comparator.reverseOrder()));
        this.fieldNameComparatorMap.put(StateCensusFieldName.AreaInSqKm,
                                Comparator.comparing(censusField->censusField.totalArea,Comparator.reverseOrder()));
    }

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException
    {
         censusStateMap= new CensusLoader().loadCensusData(csvFilePath,IndiaCensusCSV.class);
        return censusStateMap.size();
    }

    public int loadUSCensusData(String csvFilePath) throws CSVBuilderException
    {
        censusStateMap=new CensusLoader().loadCensusData(csvFilePath,USCensusData.class);
        return censusStateMap.size();
    }


    public int loadIndianStateCode(String csvFilePath) throws CSVBuilderException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICVBuilder icvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> stateCodeIterator = icvBuilder.getCSVFileIterator(reader, IndiaStateCode.class);
            Iterable<IndiaStateCode> codeIterable=()->stateCodeIterator;
            StreamSupport.stream(codeIterable.spliterator(),false)
                    .filter(csvState->censusStateMap.get(csvState.stateName)!=null)
                    .forEach(csvState->censusStateMap.get(csvState.stateCode=csvState.stateCode));
            return censusStateMap.size();
        }
        catch (IOException | CSVBuilderException | RuntimeException e)
        {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.STATE_CODE_FILE_PROBLEM);
        }
    }

    public String genericSortMethod(StateCensusFieldName fieldName) throws CSVBuilderException
    {
        if (censusStateMap == null || censusStateMap.size() == 0)
        {
            throw new CSVBuilderException("No Census Data",
                    CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, this.fieldNameComparatorMap.get(fieldName));
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
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
