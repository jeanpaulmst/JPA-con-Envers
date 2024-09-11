package org.example;

import org.example.audit.Revision;
import org.example.entidades.*;

import lombok.*;
import org.example.entidades.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");
        //nombre de la unidad persistente
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("en marcha Alberto");

        try {
            //Iniciar Transacción
            entityManager.getTransaction().begin();

            //Creo una Categoria
            Categoria categoria1 = Categoria.builder()
                    .denominacion("Baño")
                    .build();

            //Creo un Articulo
            Articulo articulo1 = Articulo.builder()
                    .cantidad(30)
                    .denominacion("Shampoo")
                    .precio(15)
                    .build();
            Articulo articulo2 = Articulo.builder()
                    .cantidad(50)
                    .denominacion("Jabon")
                    .precio(5)
                    .build();

            articulo1.getCategorias().add(categoria1); // Añado la categoria baño a shampoo
            articulo2.getCategorias().add(categoria1); // Añado la categoria baño a shampoo


            //Creo un domicilio
            Domicilio domicilio1 = Domicilio.builder()
                    .numero(459)
                    .nombreCalle("Sobremonte")
                    .build();

            //Creo un cliente
            Cliente cliente1 = Cliente.builder()
                    .apellido("Perez")
                    .dni(25347564)
                    .nombre("Jorge")
                    .domicilio(domicilio1)  //asigno domicilio en sobremonte
                    .build();

            //Creo una factura
            Factura factura1 = Factura.builder()
                    .fecha("18-08-2024")
                    .numero(21234)
                    .cliente(cliente1)  //Relaciono el cliente a la factura
                    .total(500).build();

            //Creo dos detalles
            DetalleFactura detalle1 = DetalleFactura.builder()
                    .cantidad(12)
                    .subtotal(200)
                    .articulo(articulo1)    //asigno articulo shampoo
                    .factura(factura1)
                    .build();
            DetalleFactura detalle2 = DetalleFactura.builder()
                    .cantidad(10)
                    .subtotal(300)
                    .articulo(articulo2)    //asigno articulo jabon
                    .factura(factura1)
                    .build();

            factura1.getDetallesFactura().add(detalle1);    //Relaciono los detalles a la factura
            factura1.getDetallesFactura().add(detalle2);


            entityManager.persist(factura1);      //Equivalente al save


            entityManager.getTransaction().commit();

            //Actualizar tablas
            entityManager.getTransaction().begin();
            Domicilio domicilio2 = Domicilio.builder()
                    .numero(459)
                    .nombreCalle("Vicente Gil")
                    .build();
            cliente1.setDomicilio(domicilio2);

            detalle1.setCantidad(6);
            detalle1.setSubtotal(100);
            factura1.setTotal(400);

            Revision revision = new Revision();
            entityManager.persist(revision);
            entityManager.getTransaction().commit();

            // Consultar y mostrar la entidad persistida
           /*Domicilio retrievedPerson = entityManager.find(Domicilio.class, domicilio1.getId());
            System.out.println("Retrieved Person: " + retrievedPerson.getName());*/

        }catch (Exception e){

            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase ");}

        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}