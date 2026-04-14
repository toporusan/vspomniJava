package Day6.ContinentsRequest;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class BodyRequest {

    @JacksonXmlProperty(
            localName = "ListOfContinentsByName",
            namespace = "http://www.oorsprong.org/websamples.countryinfo"
    )
    public ListOfContinentsByName request;
}
