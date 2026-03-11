package com.convive.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@IdClass(ChatMensajeId.class)
public class ChatMensaje {

    @Id
    private Long chatId;
    @Id
    private Long messageNumber;

    private String sender;
    private String receiver;
    private String message;
    private LocalDateTime timestamp;

    public ChatMensaje() {}

    public Long getChatId() { return chatId; }
    public void setChatId(Long chatId) { this.chatId = chatId; }

    public Long getMessageNumber() { return messageNumber; }
    public void setMessageNumber(Long messageNumber) { this.messageNumber = messageNumber; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}