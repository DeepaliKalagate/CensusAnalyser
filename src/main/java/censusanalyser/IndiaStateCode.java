package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode
{
    @CsvBindByName(column = "StateName", required = true)
    public String stateName;
    @CsvBindByName(column = "StateCode", required = true)
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
