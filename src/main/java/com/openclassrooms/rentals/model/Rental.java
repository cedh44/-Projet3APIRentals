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
    public Long id;

    public String name;

    public Double surface;

    public Double price;

    public String picture;

    public String description;

    public Long owner_id;

    @Column(name="created_at")
    public Date createdAt;

    @Column(name="updated_at")
    public Date updatedAt;
}
