package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USCensusAdapterTest
{
    private static final String US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    private static final String INCORRECT_US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/USCensusData123.csv";
    private static final String INCORRECT_DELIMITER_US_CENSUS_DATA_CSV_FILE_PATH = "./src/test/resources/IncorrectDelimeterUSCensusData.csv";

    USCensusAdapter usCensusAdapter = new USCensusAdapter();

    @Test
    public void givenMessage_loadUS_CensusData_ShouldReturn_ExactCount()
    {
        try
        {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_DATA_CSV_FILE_PATH);
            Assert.assertEquals(51, censusDAOMap.size());
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenMessage_loadUS_CensusData_WhenFilePathIncorrect_ShouldReturn_Exception()
    {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try
        {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(CensusAnalyser.Country.US,INCORRECT_US_CENSUS_DATA_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenMessage_loadUS_CensusData_WhenFilePathNull_ShouldReturn_Exception()
    {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try
        {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(CensusAnalyser.Country.US,"");
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenMessage_loadUS_CensusData_WhenFileHasIncorrectDelimiter_ShouldReturn_Exception()
    {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try
        {
            Map<String, CensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(CensusAnalyser.Country.US,INCORRECT_DELIMITER_US_CENSUS_DATA_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
}
