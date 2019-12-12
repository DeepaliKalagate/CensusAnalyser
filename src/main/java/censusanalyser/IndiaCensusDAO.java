package censusanalyser;

public class IndiaCensusDAO
{
    public String state;
    public String stateCode;
    public int population;
    public double populationDensity;
    public double totalArea;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV)
    {
        state = indiaCensusCSV.state;
        totalArea =indiaCensusCSV.areaInSqKm;
        populationDensity =indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }

    public IndiaCensusDAO(USCensusData censusData)
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
