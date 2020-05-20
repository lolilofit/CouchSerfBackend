package main.chat.chatcontrollers;

import main.chat.model.Greeting;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/hello/{from}/{to}")
    @SendTo({"/topic/{to}"})
    public Greeting greeting(Greeting message, @DestinationVariable String from, @DestinationVariable String to) {
        //from to checks
        return message;
    }
}
