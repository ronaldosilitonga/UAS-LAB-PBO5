/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;

/**
 *
 * @author NALDO
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String status;

    public Book(int id, String title, String author, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "[Id =" + id + ", Judul = " + title + ", Penulis = " + author + ", Status = " + status + "]";
    }
}


