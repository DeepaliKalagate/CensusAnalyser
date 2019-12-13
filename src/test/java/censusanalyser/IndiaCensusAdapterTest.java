package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndiaCensusAdapterTest
{
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusFiles_loadIndianCensusData_ShouldReturn_ExactCount()
    {
        IndiaCensusAdapter indianCensusAdapter = new IndiaCensusAdapter();
        Map<String, CensusDAO> censusDAOMap = null;
        try
        {
            censusDAOMap = indianCensusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, censusDAOMap.size());
        } catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }


    }
}
