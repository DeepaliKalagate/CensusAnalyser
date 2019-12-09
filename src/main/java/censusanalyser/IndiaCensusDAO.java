package censusanalyser;

public class IndiaCensusDAO
{
    public   String state;
    public int population;
    public int densityInSqKm;
    public int areaInSqKm;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV)
    {
        state = indiaCensusCSV.state;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityInSqKm=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }
}
