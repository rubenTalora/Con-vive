
package com.convive.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String city;
    private Boolean piso;
    private int price;
    private String sex;
    private Boolean visible; // Para si el perfil es visible o no
    private int age;

}
