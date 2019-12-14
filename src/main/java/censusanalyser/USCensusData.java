package censusanalyser;
import com.opencsv.bean.CsvBindByName;
public class USCensusData
{
    @CsvBindByName(column = "State")
    public String state;
    @CsvBindByName(column = "State Id")
    public String  stateId;
    @CsvBindByName(column = "Population")
    public double population;
    @CsvBindByName(column = "Population Density")
    public double populationDensity;
    @CsvBindByName(column = "Total area")
    public double totalArea;
    @CsvBindByName(column = "StateCode")
    public String stateCode;

    public USCensusData(String state, String  stateId, double population, double populationDensity, double totalArea, String stateCode)
    {
        this.state = state;
        this.stateId = stateId;
        this.population = population;
        this.populationDensity = populationDensity;
        this.totalArea = totalArea;
        this.stateCode = stateCode;
    }

    public USCensusData()
    {
    }
}
