package com.D2D.clientBank.webSocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendAccountUpdate(Long customerId, String message) {
        messagingTemplate.convertAndSend("/topic/account-updates/" + customerId, message);
    }
}

