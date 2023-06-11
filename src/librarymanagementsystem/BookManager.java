/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;

/**
 *
 * @author NALDO
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
    
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        try {
            Statement stmt = DBConnector.connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM buku");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String status = resultSet.getString("status");

                Book book = new Book(id, title, author, status);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
    
    public static boolean borrowBook(int bookId, String username) {
        try {
            Statement stmt = DBConnector.connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM buku WHERE id = " + bookId);

            if (resultSet.next()) {
                String status = resultSet.getString("status");
                if (status.equals("Available")) {
                    stmt.executeUpdate("UPDATE buku SET status = 'Borrowed', borrower = '" + username + "' WHERE id = " + bookId);
                    return true; // Peminjaman berhasil
                } else {
                    System.out.println("Buku sudah dipinjam.");
                }
            } else {
                System.out.println("Buku tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Peminjaman gagal
    }
    
    public static boolean returnBook(int bookId) {
        try {
            Statement stmt = DBConnector.connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM buku WHERE id = " + bookId);

            if (resultSet.next()) {
                String status = resultSet.getString("status");
                if (status.equals("Borrowed")) {
                    stmt.executeUpdate("UPDATE buku SET status = 'Available', borrower = NULL WHERE id = " + bookId);
                    return true; // Pengembalian berhasil
                } else {
                    System.out.println("Buku tidak sedang dipinjam.");
                }
            } else {
                System.out.println("Buku tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Pengembalian gagal
    }
    
    public static void displayBorrowedBooks(String username) {
        try {
            Statement stmt = DBConnector.connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM buku WHERE borrower = '" + username + "'");

            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");

                System.out.println("ID: " + bookId + ", Title: " + title + ", Author: " + author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean addBook(String title, String author) {
        try {
            PreparedStatement pstmt = DBConnector.connection.prepareStatement("INSERT INTO buku (title, author) VALUES (?, ?)");
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public static boolean deleteBook(int bookId) {
        try {
            PreparedStatement pstmt = DBConnector.connection.prepareStatement("DELETE FROM buku WHERE id = ?");
            pstmt.setInt(1, bookId);

            int rowsAffected = pstmt.executeUpdate();

            pstmt.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
}


