package org.example.flashcash.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime date;
    @ManyToOne
    private User from;
    @ManyToOne
    private User to;
    private Double amountBeforeTax;
    private Double amountAfterTax;

}
