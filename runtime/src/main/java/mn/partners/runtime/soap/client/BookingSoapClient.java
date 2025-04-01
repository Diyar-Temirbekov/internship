package mn.partners.runtime.soap.client;

import lombok.RequiredArgsConstructor;
import mn.partners.runtime.soap.dto.client.GetBookedRoomsRequest;
import mn.partners.runtime.soap.dto.client.GetBookedRoomsResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
@RequiredArgsConstructor
public class BookingSoapClient {

    private final WebServiceTemplate webServiceTemplate;

    public GetBookedRoomsResponse getBookedRooms(GetBookedRoomsRequest request) {
        return (GetBookedRoomsResponse) webServiceTemplate
                .marshalSendAndReceive(request);
    }
}