package Day6.ContinentsResponce;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class ListOfContinentsByNameResult {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(
            namespace = "http://www.oorsprong.org/websamples.countryinfo",
            localName = "tContinent"
    )
    private List<tContinent> tContinent;

    public List<tContinent> gettContinent() { 
        return this.tContinent; 
    } 

    public void settContinent(List<tContinent> tContinent) { 
        this.tContinent = tContinent; 
    } 
}