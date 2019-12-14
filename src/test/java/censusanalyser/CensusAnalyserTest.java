package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Map;

public class CensusAnalyserTest
{
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/admin1/Desktop/CensusAnalyser/src/test/resources/StateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CENSUS_CSV_FILE_TYPE="/home/admin1/Desktop/CensusAnalyser/src/test/resources/WrongCensusFile.csg";
    private static final String INDIA_STATE_CSV_FILE_PATH="/home/admin1/Desktop/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_FILE_TYPE="/home/admin1/Desktop/CensusAnalyser/src/test/resources/WrongFile.csv";
    private static final String US_CENSUS_FILE_PATH = "/home/admin1/Desktop/CensusAnalyser/src/test/resources/USCensusData.csv";
    private static final String EMPTY_FILE_PATH="/home/admin1/Desktop/CensusAnalyser/src/test/resources/WrongFile.csv";

    CensusAnalyser censusAnalyser=new CensusAnalyser();

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords()
    {
        try
        {
            int result = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29,result);
        }
        catch (CSVBuilderException e)
        {
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CENSUS_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaStateCensusData_WithIncorrectDelimiter_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CENSUS_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_WithIncorrectOrMissingHeader_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CENSUS_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount()
    {
        try
        {
            int numOfRecords=censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_STATE_CSV_FILE_PATH,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATE_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATE_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithIncorrectDelimiter_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATE_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithIncorrectOrMissingHeader_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATE_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedState_AndhraPradesh()
    {
        try
        {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.State);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedState_UttarPradesh()
    {
        try
        {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.Population);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensityPerSqKm_ShouldReturnSortedState_Bihar()
    {
        try
        {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.DensityPerSqKm);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnAreaInSqKm_ShouldReturnSortedState_Rajsthan()
    {
        try
        {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.AreaInSqKm);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Rajsthan",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords()
    {
        CensusAdapter usCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.US);
        try
        {
            Map<String,CensusDAO> result = usCensusAdapter.loadCensusData(US_CENSUS_FILE_PATH);
            Assert.assertEquals(51,result.size());
        } catch (CSVBuilderException e)
        {
        }
    }

        @Test
        public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult()
        {
            try
            {
                censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
                String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.State);
                USCensusData[] censusCSVS=new Gson().fromJson(sortedCensusData,USCensusData[].class);
                System.out.println(censusCSVS);
                Assert.assertEquals("Alabama",censusCSVS[0].state);
            }
            catch (CSVBuilderException e)
            {
            }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult()
    {
        try
        {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.Population);
            USCensusData[] censusCSVS=new Gson().fromJson(sortedCensusData,USCensusData[].class);
            System.out.println(censusCSVS);
            Assert.assertEquals("California",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult()
    {
        try
        {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.AreaInSqKm);
            USCensusData[] censusCSVS=new Gson().fromJson(sortedCensusData,USCensusData[].class);
            System.out.println(censusCSVS);
            Assert.assertEquals("Alaska",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnDensity_ShouldReturnSortedResult()
    {
        try
        {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.DensityPerSqKm);
            USCensusData[] censusCSVS=new Gson().fromJson(sortedCensusData,USCensusData[].class);
            System.out.println(censusCSVS);
            Assert.assertEquals("District of Columbia",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenUSCensusData_WhenEmptyFile_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US,EMPTY_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationWithDensity_ShouldReturnSortedResult()
    {
        try
        {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.Result);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("West Begal",censusCSVS[censusCSVS.length-1].state);
        }
        catch (CSVBuilderException e)
        {
        }
    }
}
