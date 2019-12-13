package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusData
{
    @CsvBindByName(column = "State", required = true)
    public String state;
    @CsvBindByName(column = "State Id", required = true)
    public String stateId;
    @CsvBindByName(column = "Population", required = true)
    public int population;
    @CsvBindByName(column = "Population Density", required = true)
    public double populationDensity;
    @CsvBindByName(column = "Total area", required = true)
    public double totalArea;
    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    public USCensusData(String state, int population, double densityPerSqKm, double areaInSqKm)
    {
    }
}
