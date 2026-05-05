package org.example.flashcash.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    User user1;
    @ManyToOne
    User user2;
}
