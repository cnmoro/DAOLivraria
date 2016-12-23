package daolivraria;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LivroDAO {
    
    private Connection con;
    private Statement stmt1;
    
    public LivroDAO() {
        try {
            con = ConexaoLivraria.getConnectionLivraria();

            this.stmt1 = this.con.createStatement();
            
        } catch (Exception e1) {
            System.out.println("Erro LivroDAO: " + e1);
        }
    }
    
    public Image getCapaImage(int id) {
        try {
            Image img;
            ResultSet rs = this.stmt1.executeQuery("select capa from livros where livro_id = "+id);
            if (rs.next()) {
                img = ImageIO.read(rs.getBinaryStream(1));
                return img;
            } else {
                return null;
            }
        } catch (SQLException sql1) {
            System.out.println("Erro LivroDAO: "+sql1);
            return null;
        } catch (IOException io1) {
            System.out.println("Erro IO: "+io1);
            return null;
        }
    }
    
    public byte[] getCapaByte(int id) {
        try {
            ResultSet rs = this.stmt1.executeQuery("select capa from livros where livro_id = "+id);
            if (rs.next()) {
                byte img[] = rs.getBytes(1);
                return img;
            } else {
                return null;
            }
        } catch (SQLException sql1) {
            System.out.println("Erro LivroDAO: "+sql1);
            return null;
        }
    }
    
    public ArrayList<LivroTO> listAll() {
        try {
            ResultSet rs = this.stmt1.executeQuery("select * from livros order by livro_id");
            ArrayList<LivroTO> lista = new ArrayList<LivroTO>();
            LivroTO res = null;
            while (rs.next()) {
                res = new LivroTO();
                res.setLivro_id(rs.getInt("livro_id"));
                res.setTitulo(rs.getString("titulo"));
                res.setAutor(rs.getString("autor"));
                res.setPreco(rs.getDouble("preco"));
                res.setAno(rs.getInt("ano"));
                res.setDescricao(rs.getString("descricao"));
                res.setEditora(rs.getString("editora"));
                res.setEstoque(rs.getInt("estoque"));
                res.setReserva(rs.getInt("reserva"));
                res.setGenero_id(rs.getInt("genero_id"));
                lista.add(res);
            }
            return lista;
        } catch (Exception e) {
            System.out.println("Erro:"+e);
            return null;
        }
    }
    
    public boolean update(LivroTO livro) {
        String sql = "update livros set titulo = '"+livro.getTitulo()+"', "+
                     "autor = '"+livro.getAutor()+"', editora = '"+livro.getEditora()+"', "+
                     "genero_id = "+livro.getGenero_id()+", estoque = "+livro.getEstoque()+", "+
                     "reserva = "+livro.getReserva()+", preco = "+livro.getPreco()+", "+
                     "descricao = '"+livro.getDescricao()+"', ano = "+livro.getAno()+
                     " where livro_id = "+livro.getLivro_id();
        System.out.println("Comando: "+sql);
        try {
            int n = this.stmt1.executeUpdate(sql);
            if (n == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException sql3) {
            System.out.println("Erro: " + sql3);
            return false;
        }
    }
    
    public void insert(LivroTO livro){

        String sql = ("INSERT INTO livros VALUES "
                        + "("+livro.getLivro_id()+", '"+livro.getTitulo()+"', '"+livro.getAutor()
                        +"', '"+livro.getEditora()+"', '"+livro.getAno()+"', '"+livro.getGenero_id()
                        +"', '"+livro.getDescricao()+"', "+livro.getPreco()+", "+livro.getEstoque()
                        +", "+livro.getReserva()+", null)");
        System.out.println("Comando: "+sql);
        
        if(find(livro.getLivro_id()) == null){
            try {
                stmt1.executeUpdate(sql);
            } catch (SQLException sql1) {
                System.out.println("Erro: " + sql1);
            }
        }
    }
    
    public boolean delete(LivroTO livro) {
        String sql = "delete from livros where livro_id = "+livro.getLivro_id();
        System.out.println("Comando: "+sql);
        try {
            int n = this.stmt1.executeUpdate(sql);
            if (n == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException sql3) {
            System.out.println("Erro: " + sql3);
            return false;
        }
    }
   
    public LivroTO first() {
        try {
            ResultSet rs = this.stmt1.executeQuery("select * from livros group by livro_id having min(livro_id) limit 1;");
            if (rs.next()) {
                LivroTO res = new LivroTO();
                res.setLivro_id(rs.getInt("livro_id"));
                res.setTitulo(rs.getString("titulo"));
                res.setAutor(rs.getString("autor"));
                res.setPreco(rs.getDouble("preco"));
                res.setAno(rs.getInt("ano"));
                res.setDescricao(rs.getString("descricao"));
                res.setEditora(rs.getString("editora"));
                res.setEstoque(rs.getInt("estoque"));
                res.setReserva(rs.getInt("reserva"));
                res.setGenero_id(rs.getInt("genero_id"));

                return res;
            } else {
                return null;
            }
        } catch (SQLException sql1) {
            System.out.println("Erro LivroDAO: "+sql1);
            return null;
        }
    }
    
    public LivroTO last() {
        try {
            ResultSet rs = this.stmt1.executeQuery("select * from livros group by livro_id having max(livro_id) order by livro_id desc limit 1;");
            if (rs.next()) {
                LivroTO res = new LivroTO();
                res.setLivro_id(rs.getInt("livro_id"));
                res.setTitulo(rs.getString("titulo"));
                res.setAutor(rs.getString("autor"));
                res.setPreco(rs.getDouble("preco"));
                res.setAno(rs.getInt("ano"));
                res.setDescricao(rs.getString("descricao"));
                res.setEditora(rs.getString("editora"));
                res.setEstoque(rs.getInt("estoque"));
                res.setReserva(rs.getInt("reserva"));
                res.setGenero_id(rs.getInt("genero_id"));

                return res;
            } else {
                return null;
            }
        } catch (SQLException sql1) {
            System.out.println("Erro LivroDAO: "+sql1);
            return null;
        }
    }

    public LivroTO previous(int id) {
            try {
                if (id == 0) {
                    String sql = "select * from livros group by livro_id having min(livro_id) limit 1;";
                    ResultSet rs = this.stmt1.executeQuery(sql);
                    if (rs.next()) {
                        LivroTO res = new LivroTO();
                        res.setLivro_id(rs.getInt("livro_id"));
                        res.setTitulo(rs.getString("titulo"));
                        res.setAutor(rs.getString("autor"));
                        res.setPreco(rs.getDouble("preco"));
                        res.setAno(rs.getInt("ano"));
                        res.setDescricao(rs.getString("descricao"));
                        res.setEditora(rs.getString("editora"));
                        res.setEstoque(rs.getInt("estoque"));
                        res.setReserva(rs.getInt("reserva"));
                        res.setGenero_id(rs.getInt("genero_id"));
                        return res;
                    }
                    else {
                        return null;
                    }
                }
                else {
                    String sql = "select * from livros where livro_id ="+(id - 1)+";";
                    ResultSet rs = this.stmt1.executeQuery(sql);
                    if (rs.next()) {
                        LivroTO res = new LivroTO();
                        res.setLivro_id(rs.getInt("livro_id"));
                        res.setTitulo(rs.getString("titulo"));
                        res.setAutor(rs.getString("autor"));
                        res.setPreco(rs.getDouble("preco"));
                        res.setAno(rs.getInt("ano"));
                        res.setDescricao(rs.getString("descricao"));
                        res.setEditora(rs.getString("editora"));
                        res.setEstoque(rs.getInt("estoque"));
                        res.setReserva(rs.getInt("reserva"));
                        res.setGenero_id(rs.getInt("genero_id"));
                        return res;
                    }
                    else {
                        return null;
                    }
                }
            } catch (SQLException sql1) {
                System.out.println("Erro LivroDAO: "+sql1);
                return null;
            }
    }
        
    public ArrayList<LivroTO> buscatitulo(String titulo) {
            try {
                ResultSet rs = this.stmt1.executeQuery("select * from livros where titulo like '%"+titulo+"%';");
                ArrayList<LivroTO> lista = new ArrayList<LivroTO>();
                LivroTO res = null;
                while (rs.next()) {
                    res = new LivroTO();
                    res.setLivro_id(rs.getInt("livro_id"));
                    res.setTitulo(rs.getString("titulo"));
                    res.setAutor(rs.getString("autor"));
                    res.setPreco(rs.getDouble("preco"));
                    res.setAno(rs.getInt("ano"));
                    res.setDescricao(rs.getString("descricao"));
                    res.setEditora(rs.getString("editora"));
                    res.setEstoque(rs.getInt("estoque"));
                    res.setReserva(rs.getInt("reserva"));
                    res.setGenero_id(rs.getInt("genero_id"));
                    lista.add(res);
                }
            return lista;
            } catch (Exception e) {
                System.out.println("Erro:"+e);
                return null;
            }
    }
    
    public LivroTO next(int id) {
            try {
                if (id == 0) {
                    String sql = "select * from livros group by livro_id having max(livro_id) order by livro_id desc limit 1;";
                    ResultSet rs = this.stmt1.executeQuery(sql);
                    if (rs.next()) {
                        LivroTO res = new LivroTO();
                        res.setLivro_id(rs.getInt("livro_id"));
                        res.setTitulo(rs.getString("titulo"));
                        res.setAutor(rs.getString("autor"));
                        res.setPreco(rs.getDouble("preco"));
                        res.setAno(rs.getInt("ano"));
                        res.setDescricao(rs.getString("descricao"));
                        res.setEditora(rs.getString("editora"));
                        res.setEstoque(rs.getInt("estoque"));
                        res.setReserva(rs.getInt("reserva"));
                        res.setGenero_id(rs.getInt("genero_id"));
                        return res;
                    }
                    else {
                        return null;
                    }
                }
                else {
                    String sql = "select * from livros where livro_id ="+(id + 1)+";";
                    ResultSet rs = this.stmt1.executeQuery(sql);
                    if (rs.next()) {
                        LivroTO res = new LivroTO();
                        res.setLivro_id(rs.getInt("livro_id"));
                        res.setTitulo(rs.getString("titulo"));
                        res.setAutor(rs.getString("autor"));
                        res.setPreco(rs.getDouble("preco"));
                        res.setAno(rs.getInt("ano"));
                        res.setDescricao(rs.getString("descricao"));
                        res.setEditora(rs.getString("editora"));
                        res.setEstoque(rs.getInt("estoque"));
                        res.setReserva(rs.getInt("reserva"));
                        res.setGenero_id(rs.getInt("genero_id"));
                        return res;
                    }
                    else {
                        return null;
                    }
                }
            } catch (SQLException sql1) {
                System.out.println("Erro LivroDAO: "+sql1);
                return null;
            }
    }
    
    public LivroTO find(int livro_id) {
        try {
            ResultSet rs = this.stmt1.executeQuery("select * from livros where livro_id = "+livro_id);
            if (rs.next()) {
                LivroTO res = new LivroTO();
                res.setLivro_id(livro_id);
                res.setTitulo(rs.getString("titulo"));
                res.setAutor(rs.getString("autor"));
                res.setPreco(rs.getDouble("preco"));
                res.setAno(rs.getInt("ano"));
                res.setDescricao(rs.getString("descricao"));
                res.setEditora(rs.getString("editora"));
                res.setEstoque(rs.getInt("estoque"));
                res.setReserva(rs.getInt("reserva"));
                res.setGenero_id(rs.getInt("genero_id"));

                return res;
            } else {
                return null;
            }
        } catch (SQLException sql1) {
            System.out.println("Erro LivroDAO: "+sql1);
            return null;
        }
    }
    
    public void finalize() {
        try {
            this.con.close();
        } catch (Exception e2) {
            System.out.println("Erro LivroDAO: " + e2);
        }
    }

}