package com.ccs.WebSockets.controller;

import com.ccs.WebSockets.chat.ChatMessage;
import com.ccs.WebSockets.chat.MessageType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    /**
     * Notes: @Payload is equivalent to @RequestBody for HTTP/Rest apis, this annotation will make sure that
     * the payload / message is passed in as a parameter
     *
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor){
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setContent(chatMessage.getSender() + " joined the chat!");
        chatMessage.setMessageType(MessageType.JOIN);
        return chatMessage;
    }
}
