package com.scm.Helper;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String message;
    @Builder.Default
    private MessageType type = MessageType.blue;
}
