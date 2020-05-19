package main.chat.to;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebsocketSendToUserController {
    private ObjectMapper objectMapper = new ObjectMapper();

    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
        return objectMapper.writeValueAsString(message);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
