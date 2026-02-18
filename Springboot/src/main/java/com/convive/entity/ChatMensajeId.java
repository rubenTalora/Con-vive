package com.convive.entity;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMensajeId implements Serializable {

    private Long chatId;
    private Long messageNumber;
}
