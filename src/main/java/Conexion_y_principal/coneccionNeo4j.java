/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion_y_principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author alejo
 */
public class coneccionNeo4j {
    
    private final String USER = "neo4j";
    private final String PASSWORD = "987654321";
    
    public Connection conexion(){
        Connection c = null;
        try {
            Class.forName("org.neo4j.jdbc.Driver");
            c=DriverManager.getConnection("jdbc:neo4j:http://localhost:7474", USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return c;
    }
    
}
