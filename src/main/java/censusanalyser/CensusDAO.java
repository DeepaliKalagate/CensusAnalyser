package censusanalyser;

public class CensusDAO
{
    public String state;
    public String stateCode;
    public int population;
    public double populationDensity;
    public double totalArea;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV)
    {
        state = indiaCensusCSV.state;
        totalArea =indiaCensusCSV.areaInSqKm;
        populationDensity =indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }

    public CensusDAO(USCensusData censusData)
    {
        this.state=censusData.state;
        this.stateCode=censusData.stateCode;
        this.population=censusData.population;
        this.populationDensity=censusData.populationDensity;
        this.totalArea=censusData.totalArea;
    }
    public IndiaCensusCSV getIndiaCensusCSV()
    {
        return new IndiaCensusCSV(state,population,populationDensity,totalArea);
    }

}
