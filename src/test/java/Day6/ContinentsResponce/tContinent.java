package Day6.ContinentsResponce;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class tContinent {
    @JacksonXmlProperty(
            namespace = "http://www.oorsprong.org/websamples.countryinfo",
            localName = "sCode"
    )
    private String sCode;

    @JacksonXmlProperty(
            namespace = "http://www.oorsprong.org/websamples.countryinfo",
            localName = "sName"
    )
    private String sName;

    public String getsCode() { 
        return this.sCode; 
    } 

    public void setsCode(String sCode) { 
        this.sCode = sCode; 
    } 

    public String getsName() { 
        return this.sName; 
    } 

    public void setsName(String sName) { 
        this.sName = sName; 
    }

    @Override
    public String toString() {
        return "tContinent{" +
                "sCode='" + sCode + '\'' +
                ", sName='" + sName + '\'' +
                '}';
    }
}