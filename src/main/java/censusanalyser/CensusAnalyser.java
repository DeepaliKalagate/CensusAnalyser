package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser
{
    List<IndiaCensusDAO> censusList=null;

    public CensusAnalyser()
    {
        this.censusList=new ArrayList<IndiaCensusDAO>();

    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator=csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> censusCSVIterable = () -> csvFileIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(), false).forEach(censusCSV -> censusList.add(new IndiaCensusDAO(censusCSV)));
            while (csvFileIterator.hasNext())
            {
                this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            return censusList.size();
        }
        catch (IOException e)
        {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (CSVBuilderException e)
        {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException
    {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICVBuilder icvBuilder=CSVBuilderFactory.createCSVBuilder();
            List<IndiaStateCode> stateCodeList=icvBuilder.getCSVFileList(reader,IndiaStateCode.class);
            return stateCodeList.size();
        }
        catch (IOException e)
        {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (CSVBuilderException e)
        {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator)
    {
        Iterable<E> csvIterable=()-> iterator;
        int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return namOfEateries;
    }

    public String giveStateWiseSortedCensusData() throws CensusAnalyserException
    {
        if(censusList==null || censusList.size()==0)
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<IndiaCensusDAO> csvComparator=Comparator.comparing(census->census.state);
        this.sort(csvComparator);
            String sortedStateCensusJson=new Gson().toJson(this.censusList);
            return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusDAO> censusComparator)
    {
        for (int i=0;i<censusList.size();i++)
        {
            for (int j=0;j<censusList.size()-i-1;j++)
            {
                IndiaCensusDAO indiaCensusCSV1=censusList.get(j);
                IndiaCensusDAO indiaCensusCSV2=censusList.get(j+1);
                if (censusComparator.compare(indiaCensusCSV1,indiaCensusCSV2)>0)
                {
                    censusList.set(j,indiaCensusCSV2);
                    censusList.set(j+1,indiaCensusCSV1);
                }
            }
        }
    }
}
