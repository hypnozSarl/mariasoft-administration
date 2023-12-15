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
public class AdresseDto implements Serializable {
    Long id;
    String usrAdresse;
    String usrBp;
    String usrVille;
    String usrNomPays;
    String usrRegion;
    String usrCodePostal;
    String usrPays;
}