package org.example.entidades;

import lombok.*;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Factura")
@Audited
public class Factura {
    private String fecha;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private int numero;

    @Column(name = "total")
    private int total;

    //Propietaria
    @ManyToOne(cascade = CascadeType.PERSIST) //quiero que el cliente se persista si la factura se va a persisitir. Si la act de modifica o elimina el cliente no
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    //No Propietaria
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura")
    private Set<DetalleFactura> detallesFactura = new HashSet<>();
}
