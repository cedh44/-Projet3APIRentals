package com.openclassrooms.rentals.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data //Annotation Lombok qui gère setter et getter
@Setter
@Getter
@Entity //Annotation qui indique que la classe correspond à une table de la base de données
@Table(name = "rentals") //Table des rentals
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double surface;

    private Double price;

    private String picture;

    private String description;
    @Column(nullable = false)
    private Long owner_id;

    private Date created_at;

    private Date updated_at;
}
