package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICVBuilder;
import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser
{
    Map<String,IndiaCensusDAO> censusStateMap = null;
    Map<StateCensusFieldName,Comparator<IndiaCensusDAO>> fieldNameComparatorMap=null;

    public CensusAnalyser()
    {
        this.censusStateMap = new HashMap<>();
        this.fieldNameComparatorMap=new HashedMap();
        this.fieldNameComparatorMap.put(StateCensusFieldName.State,Comparator.comparing(censusField->censusField.state));
        this.fieldNameComparatorMap.put(StateCensusFieldName.Population,
                                Comparator.comparing(censusField->censusField.population,Comparator.reverseOrder()));
        this.fieldNameComparatorMap.put(StateCensusFieldName.DensityPerSqKm,
                                Comparator.comparing(censusField->censusField.densityInSqKm,Comparator.reverseOrder()));
        this.fieldNameComparatorMap.put(StateCensusFieldName.AreaInSqKm,
                                Comparator.comparing(censusField->censusField.areaInSqKm,Comparator.reverseOrder()));
    }

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator=csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> censusCSVIterable = () -> csvFileIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(), false).forEach(censusCSV -> censusStateMap.put(censusCSV.state,new IndiaCensusDAO(censusCSV)));

            return censusStateMap.size();
        }
        catch (IOException | RuntimeException | CSVBuilderException e)
        {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
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
        List<IndiaCensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, this.fieldNameComparatorMap.get(fieldName));
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    private void sort(List<IndiaCensusDAO> censusDAOS,Comparator<IndiaCensusDAO> censusComparator)
    {
        for (int i = 0; i < censusDAOS.size(); i++)
        {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++)
            {
                IndiaCensusDAO indiaCensusCSV1 = censusDAOS.get(j);
                IndiaCensusDAO indiaCensusCSV2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(indiaCensusCSV1, indiaCensusCSV2) > 0)
                {
                    censusDAOS.set(j, indiaCensusCSV2);
                    censusDAOS.set(j + 1, indiaCensusCSV1);
                }
            }
        }
    }
}
