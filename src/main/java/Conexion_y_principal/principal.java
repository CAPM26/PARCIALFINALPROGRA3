/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion_y_principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.Scanner;

/**
 *
 * @author alejo
 */
public class principal {
    
    public static Scanner teclado = new Scanner(System.in);
    static coneccionNeo4j n = new coneccionNeo4j();
    static Connection con = n.conexion();
    
    public static int encabezado(){
        
        int opcion=-1;
        System.out.println("¿QUE DESEA REALIZAR?");
        System.out.println("-1. Importar codigo Cypher\n-2.Escribir sentencia GQL\n-0. Salir");
        opcion=teclado.nextInt();
        while(opcion<0 || opcion>2){
            System.out.println("Opcion invalida. Por favor, ingrese una opcion valida");
            opcion = teclado.nextInt();
        }
        return opcion;
    }
    
    public static void ejecutarquerito(String codigo){
        try {
            PreparedStatement pst = con.prepareStatement(codigo);
            pst.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public static void main(String[] args){    
        int opcion = encabezado();
        while(opcion!=0){
            if(opcion==1){
                try {
                    teclado.nextLine();
                    System.out.println("Ingrese la ruta del archivo para importar el codigo Cypher:");
                    String ruta = teclado.nextLine();
                    BufferedReader bf = new BufferedReader(new FileReader(ruta));
                    String temp = "";
                    String bfRead;
                    while((bfRead = bf.readLine()) != null){
                        temp = temp + bfRead;
                    }
                    String querito = temp;
                    ejecutarquerito(querito);
                    
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.out.println("---------------DATOS IMPORTADOS CORRECTAMENTE----------------");
                opcion = encabezado();
            }
            if(opcion==2){
                try {
                    teclado.nextLine();
                    System.out.println("Ingrese la sentencia GQL que desea ejecutar: ");
                    String querito = teclado.nextLine();
                    //System.out.println(querito);
                    ejecutarquerito(querito);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.out.println("---------------SENTENCIA EJECUTADA CORRECTAMENTE-----------");
                opcion = encabezado();
            }
        }
        System.out.println("Ha salido del programa, ha sido un gusto servirle");
         System.exit(0);
    }
}
