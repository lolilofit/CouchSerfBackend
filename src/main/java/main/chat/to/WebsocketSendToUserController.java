package main.chat.to;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketSendToUserController {
    private ObjectMapper objectMapper = new ObjectMapper();

    //public String processMessageFromClient(@Payload String message, Principal principal) throws JsonProcessingException {
    public String processMessageFromClient(@Payload String message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
