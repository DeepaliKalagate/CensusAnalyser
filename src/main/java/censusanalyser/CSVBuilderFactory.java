package censusanalyser;

public class CSVBuilderFactory
{

    public static ICVBuilder createCSVBuilder()
    {
       return new OpenCSVBuilder();
    }
}
