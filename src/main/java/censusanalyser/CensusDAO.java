package censusanalyser;
public class CensusDAO
{
    public String state;
    public String stateCode;
    public double population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String stateId;
    public double populationDensity;
    public double totalArea;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV)
    {
        state = indiaCensusCSV.state;
        areaInSqKm =indiaCensusCSV.areaInSqKm;
        densityPerSqKm =indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }

    public CensusDAO(USCensusData censusData)
    {
        this.state=censusData.state;
        this.stateCode=censusData.stateCode;
        this.population=censusData.population;
        this.densityPerSqKm = (int) censusData.populationDensity;
        this.areaInSqKm = (int) censusData.totalArea;
        this.stateId=censusData.stateId;
    }

    public Object getCensusDTO(CensusAnalyser.Country country)
    {
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusData(state,stateId,population,populationDensity,totalArea,stateCode);

        return new IndiaCensusCSV(state,population,densityPerSqKm,areaInSqKm);
    }
}
