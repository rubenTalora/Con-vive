
package com.convive.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@IdClass(ChatMensajeId.class) 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMensaje {

    @Id
    private Long chatId; //ID del chat
    @Id
    private Long messageNumber; // Numero de mensaje dentro del chat

    private String sender;
    private String receiver;
    private String message;
    private LocalDateTime timestamp;
}
