package com.example.GameVault.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Jesús Rodrigo Villegas Argüelles - 261186
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="juegos")

@Repository
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String titulo;

    @Column(nullable=false)
    private String descripcion;

    @Column(nullable=false)
    private String portadaUrl;

    @Column
    private Double precio;

    @Column(nullable=false)
    private String categoria;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

}
