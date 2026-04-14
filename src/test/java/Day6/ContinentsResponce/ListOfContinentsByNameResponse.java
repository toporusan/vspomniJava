package Day6.ContinentsResponce;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ListOfContinentsByNameResponse {

    @JacksonXmlProperty(
            namespace = "http://www.oorsprong.org/websamples.countryinfo",
            localName = "ListOfContinentsByNameResult"
    )
    private ListOfContinentsByNameResult listOfContinentsByNameResult;


    public ListOfContinentsByNameResult getListOfContinentsByNameResult() { 
        return this.listOfContinentsByNameResult;
    } 

    public void setListOfContinentsByNameResult(ListOfContinentsByNameResult ListOfContinentsByNameResult) { 
        this.listOfContinentsByNameResult = ListOfContinentsByNameResult;
    } 


}