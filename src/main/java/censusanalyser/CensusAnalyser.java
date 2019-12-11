package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import com.bridgelabz.CSVBuilderFactory;
import com.bridgelabz.ICVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser
{
    Map<String,IndiaCensusDAO> censusStateMap = null;

    public CensusAnalyser()
    {
        this.censusStateMap = new HashMap<>();
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
        catch (IOException e)
        {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndianStateCode(String csvFilePath) throws CSVBuilderException
    {
        int counter=0;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICVBuilder icvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> stateCodeIterator=icvBuilder.getCSVFileIterator(reader,IndiaStateCode.class);
            while (stateCodeIterator.hasNext())
            {
                counter++;
                IndiaStateCode stateCsv = stateCodeIterator.next();
                IndiaCensusDAO censusDAO=censusStateMap.get(stateCsv.stateName);
                if (censusDAO==null) continue;
                censusDAO.stateCode=stateCsv.stateCode;
            }
            return counter;
        }
        catch (IOException | CSVBuilderException e)
        {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.STATE_CODE_FILE_PROBLEM);
        }
    }

    public String giveStateWiseSortedCensusData() throws CSVBuilderException
    {
        if (censusStateMap == null || censusStateMap.size() == 0)
            throw new CSVBuilderException("No Census Data",
                    CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        List<IndiaCensusDAO> censusDAOS=censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,csvComparator);
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
