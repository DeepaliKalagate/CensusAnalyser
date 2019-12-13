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

    CensusAnalyser censusAnalyser=new CensusAnalyser();

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            Map<String,CensusDAO> numOfRecords = indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        }
        catch (CSVBuilderException e)
        {

        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CENSUS_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaStateCensusData_WithIncorrectDelimiter_ShouldThrowException()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CENSUS_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_WithIncorrectOrMissingHeader_ShouldThrowException()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CENSUS_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            Map<String,CensusDAO> numOfRecords=indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.State);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATE_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATE_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithIncorrectDelimiter_ShouldThrowException()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATE_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithIncorrectOrMissingHeader_ShouldThrowException()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATE_CSV_FILE_TYPE);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.Population);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensityPerSqKm_ShouldReturnSortedResult()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.DensityPerSqKm);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnAreaInSqKm_ShouldReturnSortedResult()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.AreaInSqKm);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Rajsthan",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords()
    {
        CensusAdapter usCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.US);
        try
        {
            Map<String,CensusDAO> result = usCensusAdapter.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            Assert.assertEquals(51,result.size());
        } catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedResult()
    {
        IndiaCensusAdapter indiaCensusAdapter=CensusAdapterFactory.getAdapterObject(CensusAnalyser.Country.INDIA);
        try
        {
            indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.genericSortMethod(StateCensusFieldName.State);
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Alabama",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }
}
