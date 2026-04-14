package Day6.ContinentsResponce;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Envelope {

    @JacksonXmlProperty(
            namespace = "http://schemas.xmlsoap.org/soap/envelope/",
            localName = "Body"
    )
    private Body body;

    public Body getBody() { 
        return this.body;
    }

    public void setBody(Body Body) { 
        this.body = Body;
    }



}