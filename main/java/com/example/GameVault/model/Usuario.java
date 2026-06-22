package com.example.GameVault.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jesús Rodrigo Villegas Argüelles - 261186
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios")

@Repository
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String nombre;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String contrasena;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Juego> juegos;


}
