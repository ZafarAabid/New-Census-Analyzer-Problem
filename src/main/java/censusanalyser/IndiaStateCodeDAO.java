package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeDAO {
    public IndiaStateCodeDAO(){}
    @CsvBindByName(column = "State Name", required = true)
    public String state;
    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;
    @CsvBindByName
    public int TIN;
    @CsvBindByName
    public int SrNo;

    public int getTIN() {
        return TIN;
    }

    public void setTIN(int TIN) {
        this.TIN = TIN;
    }

    public int getSrNo() {
        return SrNo;
    }

    public void setSrNo(int srNo) {
        SrNo = srNo;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

}
