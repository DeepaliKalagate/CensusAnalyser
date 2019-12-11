package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode
{
    @CsvBindByName(column = "StateName")
    public String stateName;
    @CsvBindByName(column = "StateCode")
    public String stateCode;


    @Override
    public String toString()
    {
        return "IndiaStateCode{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
