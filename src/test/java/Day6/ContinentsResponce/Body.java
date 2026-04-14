package Day6.ContinentsResponce;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {

    @JacksonXmlProperty(
            namespace = "http://www.oorsprong.org/websamples.countryinfo",
            localName = "ListOfContinentsByNameResponse"
    )
    private ListOfContinentsByNameResponse listOfContinentsByNameResponse;

    public ListOfContinentsByNameResponse getListOfContinentsByNameResponse() {

        return this.listOfContinentsByNameResponse;
    }

    public void setListOfContinentsByNameResponse(ListOfContinentsByNameResponse ListOfContinentsByNameResponse) {
        this.listOfContinentsByNameResponse = ListOfContinentsByNameResponse;
    }
}