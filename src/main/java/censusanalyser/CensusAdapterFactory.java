package censusanalyser;
public class CensusAdapterFactory
{
    public static CensusAdapter getAdapterObject(CensusAnalyser.Country country)
    {
        if(country.equals(CensusAnalyser.Country.INDIA))
        {
            return new IndiaCensusAdapter();
        }
        else if(country.equals(CensusAnalyser.Country.US))
        {
            return new USCensusAdapter();
        }
        return null;
    }
}
