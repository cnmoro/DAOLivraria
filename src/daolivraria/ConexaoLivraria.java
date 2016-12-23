package daolivraria;

import java.sql.*;

public class ConexaoLivraria {
    
    public static Connection getConnectionLivraria() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/livraria";
            String usuario = "root";
            String senha = "root";
            Connection con = DriverManager.getConnection(url, usuario, senha);

            return con;
            
        } catch (Exception e1) {
            return null;
        }
        
    }
    
}
