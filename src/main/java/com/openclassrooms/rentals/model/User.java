package com.openclassrooms.rentals.model;



import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data //Annotation Lombok qui gère setter et getter
@Entity //Annotation qui indique que la classe correspond à une table de la base de données
@Table(name = "users") //Table users
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;

    private String name;
    @Column(nullable = false)
    private String password;

    @Column
    private Date created_at;

    @Column
    private Date updated_at;
}
