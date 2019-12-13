package censusanalyser;

public class CensusDAO
{
    public String state;
    public String stateCode;
    public int population;
    public double densityPerSqKm;
    public double areaInSqKm;

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
        this.densityPerSqKm =censusData.populationDensity;
        this.areaInSqKm =censusData.totalArea;
    }
    public IndiaCensusCSV getIndiaCensusCSV()
    {
        return new IndiaCensusCSV(state,population, densityPerSqKm, areaInSqKm);
    }

    public Object getCensusDTO(CensusAnalyser.Country country)
    {
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusData(state,population,densityPerSqKm,areaInSqKm);
        return new IndiaCensusCSV(state,population,densityPerSqKm,areaInSqKm);
    }
}
