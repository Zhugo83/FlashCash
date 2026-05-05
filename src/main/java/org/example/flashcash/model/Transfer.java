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
    @ManyToOne
    private String from;
    @ManyToOne
    private String to;
    private LocalDateTime date;
    private Double amountBeforeTax;
    private Double amountAfterTax;

}
