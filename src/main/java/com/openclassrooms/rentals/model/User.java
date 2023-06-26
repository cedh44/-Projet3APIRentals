package com.openclassrooms.rentals.model;

import com.sun.istack.NotNull;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data //Annotation Lombok qui gère setter et getter
@Entity //Annotation qui indique que la classe correspond à une table de la base de données
@Table(name = "users") //Table users
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String name;

    private String password;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;
}
