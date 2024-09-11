package org.example.entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Audited
public class Articulo {

    @Column
    private int cantidad;

    @Column
    private String denominacion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private int precio;

    //propietaria
    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Categoria> categorias = new HashSet<Categoria>();

    //No propietaria -- relacion con los detalles
    @Builder.Default
    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "articulo")
    private Set<DetalleFactura> detalleFacturas = new HashSet<DetalleFactura>();
}
