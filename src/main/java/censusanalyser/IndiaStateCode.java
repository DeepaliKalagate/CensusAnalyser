package censusanalyser;
import com.opencsv.bean.CsvBindByName;
public class IndiaStateCode
{
    @CsvBindByName(column = "StateName")
    public String stateName;
    @CsvBindByName(column = "StateCode")
    public String stateCode;
    @CsvBindByName(column = "TIN")
    public String tin;
    @CsvBindByName(column = "SrNo")
    public String srNo;

    @Override
    public String toString()
    {
        return "IndiaStateCode{" +
                "stateName='" + stateName + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", tin='" + tin + '\'' +
                ", srNo='" + srNo + '\'' +
                '}';
    }
}
