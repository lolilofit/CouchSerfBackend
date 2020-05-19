package main.chat.chatcontrollers;

import main.chat.model.Greeting;
import main.chat.model.HelloMessage;
import main.chat.model.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {
    @MessageMapping("/hello/{from}/{to}")
    @SendTo({"/topic/{to}"})
    public Greeting greeting(Greeting message, @DestinationVariable String from, @DestinationVariable String to) throws Exception {
        //from to checks
        return message;
    }
}
