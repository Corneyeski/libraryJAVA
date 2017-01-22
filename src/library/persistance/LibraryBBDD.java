/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static javax.management.Query.match;
import library.model.Author;
import library.model.Book;
import library.model.User;

/**
 *
 * @author alanv
 */
public class LibraryBBDD {

    private Connection conexion;

    public void conectar() throws SQLException {
        String url = "jdbc::mysql://localhost:3306/library";
        String usr = "root";
        String pass = "";
        conexion = DriverManager.getConnection(url, usr, pass);
    }

    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

    public User obtenerUser(ResultSet ps) throws SQLException {

        User user = new User();

        user.setName(ps.getString("nombre"));
        user.setPass(ps.getString("pass"));

        return user;
    }
    
    public Author obtenerAutor(ResultSet ps)throws SQLException{
        
        Author autor = new Author();
        
        autor.setIda(ps.getInt("ida"));
        autor.setName(ps.getString("name"));
        autor.setSurnamje(ps.getString("surname"));
        autor.setCountry(ps.getString("country"));
        
        return autor;
    }

    public Book obtenerLibro(ResultSet ps)throws SQLException {
        
        Book book = new Book();
        
        book.setIdb(ps.getInt("idb"));
        book.setTitle(ps.getString("title"));
        book.setNump(ps.getInt("nump"));
        book.setGenre(ps.getString("genre"));
        
        book.setAuthor(unAutor(ps.getString("author")));
        
        return book;
    }
    
    public boolean consulta(User user) throws SQLException {

        boolean exist = false;

        String obtener = "select * from user where nombre =" + user.getName() + " and pass =" + user.getPass();
        Statement consulta = conexion.createStatement();

        ResultSet ps = consulta.executeQuery(obtener);
        
        if(!ps.next()){
            return exist;
        }else{
            exist = true;
            return exist;
        }
    }
    
    public void insertarLibro(Book book) throws SQLException {
        
        String insert = "INSERT INTO book values (?,?,?,?,?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        
        ps.setInt(1, book.getIdb());
        ps.setString(2, book.getTitle());
        ps.setInt(3, book.getNump());
        ps.setString(4, book.getGenre());
        ps.setString(5, book.getAuthor().getName());
        
        ps.executeUpdate();
    }
    
    public void eliminarlibro(Book book) throws SQLException {
        
       String insert = "delete * from book where idb ="+book.getIdb();
       PreparedStatement ps = conexion.prepareStatement(insert); 
       ps.executeUpdate();
    }
    
    public void updatearLibro(Book book) throws SQLException {
        
       String insert = "update into book set title=?, nump=?, genre=?, author=? where idb="+book.getIdb();
       PreparedStatement ps = conexion.prepareStatement(insert);
       
       ps.setString(1, book.getTitle());
       ps.setInt(2, book.getNump());
       ps.setString(3, book.getGenre());
       ps.setString(4, book.getAuthor().getName());
        
       ps.executeUpdate();
    }
    
    public void insertarAutor(Author author) throws SQLException {
        
        String insert = "insert into author values (?,?,?,?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        
        ps.setInt(1, author.getIda());
        ps.setString(2, author.getName());
        ps.setString(3,author.getSurnamje());
        ps.setString(4, author.getCountry());
        
        ps.executeUpdate();
    }
    
    public void eliminarAutor(Author author) throws SQLException{
        
        String insert = "delete * from author where ida="+author.getIda();
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.executeUpdate();
    }
    
    public void updatearAutor(Author author) throws SQLException{
        
       String insert = "update into author set name=?, surname=?, country=? where ida="+author.getIda();
       PreparedStatement ps = conexion.prepareStatement(insert);
       
       ps.setString(1, author.getName());
       ps.setString(2, author.getSurnamje());
       ps.setString(3, author.getCountry());
        
       ps.executeUpdate();
    }
    
    public void insertUser(User user)throws SQLException{
        
        String insert = "insert into user values (?,?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        
        ps.setString(1, user.getName());
        ps.setString(2, user.getPass());
        
        ps.executeUpdate();
    }
    
    public void deleteUser(User user)throws SQLException {
        
       String insert = "delete * from user where name="+user.getName();
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.executeUpdate(); 
    }
    
    public void updateUser(User user, String name)throws SQLException {
        
       String insert = "update into user set name=?, pass=? where name="+name;
       PreparedStatement ps = conexion.prepareStatement(insert);
       
       ps.setString(1, user.getName());
       ps.setString(2, user.getPass());
       
       ps.executeUpdate();
    }
    
    public List<Author> todosAutores() throws SQLException{
        
        List<Author> list = new ArrayList<>();

        String obtener = "select * from author";
        Statement consulta = conexion.createStatement();

        ResultSet ps = consulta.executeQuery(obtener);
        
        while(ps.next()){
            list.add(obtenerAutor(ps));
        }
        return list;
    }
    
    public Author unAutor(String nombre) throws SQLException {
        
        String obtener = "select * from author where ida ="+nombre;
        Statement consulta = conexion.createStatement();

        ResultSet ps = consulta.executeQuery(obtener);
        
        return obtenerAutor(ps);
    }
    
    public List<Book> todosLibros() throws SQLException {
        
        List<Book> list = new ArrayList<>();
        
        String obtener = "select * from book";
        Statement consulta = conexion.createStatement();

        ResultSet ps = consulta.executeQuery(obtener);
        
        while(ps.next()){
            list.add(obtenerLibro(ps));
        }
        return list;
    }
    public List<Book> librosAutor(String name) throws SQLException {
       
        List<Book> list = new ArrayList<>();
        
        String obtener = "select * from book where author="+name;
        Statement consulta = conexion.createStatement();

        ResultSet ps = consulta.executeQuery(obtener);
        
        while(ps.next()){
            list.add(obtenerLibro(ps));
        }
        return list;
    }
    
    public List<Book> librosGenero(String name) throws SQLException {
       
        List<Book> list = new ArrayList<>();
        
        String obtener = "select * from book where genre="+name;
        Statement consulta = conexion.createStatement();

        ResultSet ps = consulta.executeQuery(obtener);
        
        while(ps.next()){
            list.add(obtenerLibro(ps));
        }
        return list;
    }
    
    public List<Book> librosAutorGenero(String name, String author) throws SQLException {
       
        List<Book> list = new ArrayList<>();
        
        String obtener = "select * from book where genre="+name+" and author ="+author;
        Statement consulta = conexion.createStatement();

        ResultSet ps = consulta.executeQuery(obtener);
        
        while(ps.next()){
            list.add(obtenerLibro(ps));
        }
        return list;
    }
    
}