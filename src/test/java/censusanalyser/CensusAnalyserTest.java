package censusanalyser;

import com.bridgelabz.CSVBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest
{
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/admin1/Desktop/CensusAnalyser/src/test/resources/StateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_FILE_PATH="/home/admin1/Desktop/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_TATE_CSV_FILE_PATH="/home/admin1/Desktop/CensusAnalyser/src/test/resources/IndiaStateCode.txt";


    CensusAnalyser censusAnalyser = new CensusAnalyser();

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords()
    {
        try
        {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
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
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
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
            int numOfRecords=censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        }
        catch (CSVBuilderException e)
        {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult()
    {
        try
        {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.giveStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSVS=new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSVS[0].state);
        }
        catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecords()
    {
        try
        {
            int numOfRecords=censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        }
        catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException()
    {
        try
        {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            censusAnalyser.loadIndianStateCode(WRONG_STATE_CSV_FILE_PATH);
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
            censusAnalyser.loadIndianStateCode(WRONG_TATE_CSV_FILE_PATH);
        }
        catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.STATE_CODE_FILE_PROBLEM,e.type);
        }
    }

}
