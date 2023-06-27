package com.openclassrooms.rentals.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data //Annotation Lombok qui gère setter et getter
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

    private Long owner_id;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;
}
