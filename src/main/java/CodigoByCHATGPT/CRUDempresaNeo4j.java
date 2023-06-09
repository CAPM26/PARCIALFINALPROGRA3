/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CodigoByCHATGPT;

/**
 *
 * @author alejo
 */
import org.neo4j.driver.*;
import static org.neo4j.driver.Values.parameters;

public class CRUDempresaNeo4j {

    private final Driver driver;

    public CRUDempresaNeo4j(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void close() {
        driver.close();
    }

    public void crearProducto(String nombre, String categoria, double precio) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE (p:Producto {nombre: $nombre, categoria: $categoria, precio: $precio})",
                        parameters("nombre", nombre, "categoria", categoria, "precio", precio));
                return null;
            });
        }
    }

    public void actualizarPrecio(String nombre, double nuevoPrecio) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (p:Producto {nombre: $nombre}) SET p.precio = $nuevoPrecio",
                        parameters("nombre", nombre, "nuevoPrecio", nuevoPrecio));
                return null;
            });
        }
    }

    public void eliminarProducto(String nombre) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (p:Producto {nombre: $nombre}) DELETE p",
                        parameters("nombre", nombre));
                return null;
            });
        }
    }

    public void mostrarProductos() {
        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run("MATCH (p:Producto) RETURN p.nombre AS nombre, p.categoria AS categoria, p.precio AS precio");
                while (result.hasNext()) {
                    var record = result.next();
                    String nombre = record.get("nombre").asString();
                    String categoria = record.get("categoria").asString();
                    double precio = record.get("precio").asDouble();
                    System.out.println("Nombre: " + nombre + ", Categoría: " + categoria + ", Precio: " + precio);
                }
                return null;
            });
        }
    }

    public void crearCliente(String nombre, String direccion) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE (c:Cliente {nombre: $nombre, direccion: $direccion})",
                        parameters("nombre", nombre, "direccion", direccion));
                return null;
            });
        }
    }

    public void actualizardireccion(String nombre, String nuevaDire) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (c:Cliente {nombre: $nombre}) SET c.direccion = $nuevaDire",
                        parameters("nombre", nombre, "nuevaDire", nuevaDire));
                return null;
            });
        }
    }

    public void eliminarCliente(String nombre) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (c:Cliente {nombre: $nombre}) DELETE c",
                        parameters("nombre", nombre));
                return null;
            });
        }
    }

    public void mostrarClientes() {
        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run("MATCH (c:Cliente) RETURN c.nombre AS nombre, c.direccion AS direccion");
                while (result.hasNext()) {
                    var record = result.next();
                    String nombre = record.get("nombre").asString();
                    String direccion = record.get("direccion").asString();
                    System.out.println("Nombre: " + nombre + ", Direccion: " + direccion);
                }
                return null;
            });
        }
    }
    
    public void crearRelacionC_P(String nombreC, String nombreP, String fechaCompra) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH(c:Cliente {nombre: $nombreC}) MATCH(p:Producto {nombre: $nombreP})"
                        + "CREATE(c)-[:COMPRA {fecha: $fechaCompra}]->(p)",
                        parameters("nombreC", nombreC, "nombreP", nombreP, "fechaCompra", fechaCompra));
                return null;
            });
        }
    }
        //MATCH(c:Cliente {nombre: 'cliente1'})-[r:COMPRA]->(p:Producto {nombre: 'Manzana'}) SET r.fecha = '23/05/2022'
    public void actualizarfecha(String nombreC, String nombreP, String nuevafecha) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH(c:Cliente {nombre: $nombreC})-[r:COMPRA]->(p:Producto {nombre: $nombreP})"
                        + "SET r.fecha = $nuevafecha",
                        parameters("nombreC", nombreC, "nombreP", nombreP, "nuevafecha", nuevafecha));
                return null;
            });
        }
    }
    public void eliminarRelacion(String nombreC, String nombreP) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH(c:Cliente {nombre: $nombreC})-[r:COMPRA]->(p:Producto {nombre: $nombreP})"
                        + "DELETE r",
                        parameters("nombreC", nombreC, "nombreP", nombreP));
                return null;
            });
        }
    }
                
    public void mostrarRelaciones() {
        try (Session session = driver.session()) {
            session.readTransaction(tx -> {
                Result result = tx.run("MATCH(c:Cliente)-[r:COMPRA]->(p:Producto)"
                        + "RETURN c.nombre AS nombreC, p.nombre AS nombreP, r.fecha AS fecha");
                while (result.hasNext()) {
                    var record = result.next();
                    String nombreC = record.get("nombreC").asString();
                    String nombreP = record.get("nombreP").asString();
                    String fecha = record.get("fecha").asString();
                    System.out.println("Nombre_Cliente: " + nombreC + ", Nombre_Producto: " + nombreP +", Fecha_Compra: " + fecha);
                }
                return null;
            });
        }
    }
    
    public void eliminarTodaRelacion() {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH(c)-[r]->(p) DELETE r");
                return null;
            });
        }
    }
    public void eliminarTodo() {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH(n) DELETE n");
                return null;
            });
        }
    }
    
    public static void main(String[] args) {
        String uri = "bolt://localhost:7687"; // Cambiar con la dirección de tu base de datos Neo4j
        String user = "neo4j";
        String password = "987654321";

        CRUDempresaNeo4j Datos = new CRUDempresaNeo4j(uri, user, password);

        // Ejemplo de uso del CRUD
        Datos.crearProducto("Manzana", "Frutas", 2.50);
        Datos.crearProducto("Leche", "Lácteos", 8.00);
        Datos.crearProducto("Yogurt", "Lácteos", 9.50);
        Datos.crearProducto("Galletas", "Botanas", 1.25);
        System.out.println("MOSTRANDO PRODUCTOS");
        Datos.mostrarProductos();
        Datos.actualizarPrecio("Manzana", 2.29);
        Datos.actualizarPrecio("Yogurt", 10.50);
        System.out.println("MOSTRANDO CAMBIOS A PRODUCTOS");
        Datos.mostrarProductos();
        Datos.crearCliente("cliente1","Jutiapa");
        Datos.crearCliente("cliente2","Jalapa");
        Datos.crearCliente("cliente3","Mita");
        System.out.println("MOSTRANDO CLIENTES");
        Datos.mostrarClientes();
        Datos.actualizardireccion("cliente1", "Amatitlan");
        Datos.actualizardireccion("cliente3", "El Progreso, Jutiapa");
        System.out.println("MOSTRANDO CAMBIOS A CLIENTES");
        Datos.mostrarClientes();
        Datos.crearRelacionC_P("cliente1", "Manzana", "14/04/2023");
        Datos.crearRelacionC_P("cliente1", "Yogurt", "15/04/2023");
        Datos.crearRelacionC_P("cliente2", "Leche", "07/05/2023");
        Datos.crearRelacionC_P("cliente3", "Galletas", "25/01/2023");
        Datos.crearRelacionC_P("cliente3", "Yogurt", "23/12/2022");
        System.out.println("MOSTRANDO RELACIONES");
        Datos.mostrarRelaciones();
        Datos.actualizarfecha("cliente1", "Manzana", "15/05/2023");
        System.out.println("MOSTRANDO CAMBIOS A RELACIONES");
        Datos.mostrarRelaciones();
        //Datos.eliminarRelacion("cliente1", "Yogurt");
        System.out.println("MOSTRANDO CAMBIOS AL ELIMINAR RELACIONES");
        Datos.mostrarRelaciones();
        //Datos.eliminarTodaRelacion();
        //Datos.eliminarTodo();

        Datos.close();
    }
}


