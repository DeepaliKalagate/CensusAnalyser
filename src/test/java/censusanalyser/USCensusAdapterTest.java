package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USCensusAdapterTest
{
    private static final String US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    private static final String INCORRECT_US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/USCensusData123.csv";

    USCensusAdapter usCensusAdapter = new USCensusAdapter();

    @Test
    public void givenUSCensusFile_ShouldReturnExactCount()
    {
        try
        {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(US_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(51, censusDAOMap.size());
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenUSCensusFile_WhenFilePathIncorrect_ShouldReturnException()
    {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try
        {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(INCORRECT_US_CENSUS_DATA_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenUSCensusFile_WhenFilePathNull_ShouldReturnException()
    {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try
        {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData("");
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenUSCensusFile_WhenIncorrectDelimiter_ShouldReturnException()
    {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try
        {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(INCORRECT_US_CENSUS_DATA_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenHeaderNotFound_ShouldReturnsException()
    {
        try
        {
            Map<String,CensusDAO> censusDAOMap=usCensusAdapter.loadCensusData(INCORRECT_US_CENSUS_DATA_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
}
