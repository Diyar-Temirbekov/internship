
package mn.partners.runtime.soap.dto.client;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 3.0.0
 * Generated source version: 3.0
 * 
 */
@WebService(name = "RoomsPort", targetNamespace = "http://soap.roomservice.example.com")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface RoomsPort {


    /**
     * 
     * @param getBookedRoomsRequest
     * @return
     *     returns mn.partners.runtime.soap.dto.client.GetBookedRoomsResponse
     */
    @WebMethod
    @WebResult(name = "getBookedRoomsResponse", targetNamespace = "http://soap.roomservice.example.com", partName = "getBookedRoomsResponse")
    public GetBookedRoomsResponse getBookedRooms(
        @WebParam(name = "getBookedRoomsRequest", targetNamespace = "http://soap.roomservice.example.com", partName = "getBookedRoomsRequest")
        GetBookedRoomsRequest getBookedRoomsRequest);

}
