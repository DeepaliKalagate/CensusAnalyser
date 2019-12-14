package censusanalyser;
import com.bridgelabz.CSVBuilderException;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;
public class IndiaCensusAdapterTest
{
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode12.cst";
    private static final String WRONG_STATE_CSV_FILE_TYPE="/home/admin1/Desktop/CensusAnalyser/src/test/resources/WrongFile.csv";
    IndiaCensusAdapter indianCensusAdapter = new IndiaCensusAdapter();

    @Test
    public void givenIndianCensusFiles_loadIndianCensusData_ShouldReturn_ExactCount()
    {
        Map<String, CensusDAO> censusDAOMap = null;
        try
        {
            censusDAOMap = indianCensusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, censusDAOMap.size());
        }
        catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSendOneFilePath_ShouldReturn_Exception()
    {
        try
        {
            indianCensusAdapter.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH,INDIA_CENSUS_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectFilePath_ShouldReturnsException()
    {
        try
        {
            indianCensusAdapter.loadCensusData(WRONG_CSV_FILE_PATH,WRONG_STATE_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectFileType_ShouldReturnsException()
    {
        try
        {
            indianCensusAdapter.loadCensusData(WRONG_CSV_FILE_PATH,WRONG_STATE_CSV_FILE_TYPE);
        } catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectDelimiter_ShouldReturnsException()
    {
        try
        {
            indianCensusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.STATE_CODE_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenHeaderNotFound_ShouldReturnsException()
    {
        try
        {
            indianCensusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.STATE_CODE_FILE_PROBLEM, e.type);
        }
    }
}
