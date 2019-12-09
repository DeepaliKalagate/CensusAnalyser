package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode
{
    @CsvBindByName(column = "StateName")
    public String stateName;
    @CsvBindByName(column = "StateCode")
    private String stateCode;


    @Override
    public String toString()
    {
        return "IndiaStateCode{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
