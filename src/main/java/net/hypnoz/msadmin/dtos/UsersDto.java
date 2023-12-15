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
public class UsersDto implements Serializable {
    Long id;
    String usrCivilite;
    String usrNom;
    String usrPrenom;
    String usrInitiale;
    String usrPhoto;
    String usrIdentifiant;
    String usrMatricule;
    String usrPassword;
    String usrLogin;
    AdresseDto userAddress;
    CommunicationDto userCommunication;
}