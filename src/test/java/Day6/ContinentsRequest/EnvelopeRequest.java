package Day6.ContinentsRequest;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(
        localName = "Envelope",
        namespace = "http://www.w3.org/2003/05/soap-envelope"
)
public class EnvelopeRequest {

    @JacksonXmlProperty(
            localName = "Body",
            namespace = "http://www.w3.org/2003/05/soap-envelope"
    )
    public BodyRequest body;
}



