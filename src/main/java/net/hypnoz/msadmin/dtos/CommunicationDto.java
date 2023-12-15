package net.hypnoz.msadmin.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommunicationDto implements Serializable {
    Long id;
    String usrMail;
    String usrSkype;
    String usrMsn;
    String usrYahoo;
    String usrTelBureau;
}