/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagementsystem;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author NALDO
 */
public class LibraryManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        DBConnector.initDBConnection();
        
        System.out.println("====================================================");
        System.out.println("+            Welcome To Ilkomp Library             +");
        System.out.println("====================================================");

        // Login process
        boolean isLoggedIn = false;
        do {
            System.out.println("Please choose login as \n[1]. Member \n[2]. Admin:");
            System.out.print("Enter : ");
          
            String choice = scanner.nextLine(); // Consume the newline character

            if (choice.equals("1")) {
                System.out.print("\033[H\033[2J");
                System.out.println("---------------------------");
                System.out.println("Login as Member");
                System.out.println("---------------------------\n");
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                
                try {
         
                    Statement conn = DBConnector.connection.createStatement();
                    ResultSet rslt;
                    rslt = conn.executeQuery("SELECT * FROM member WHERE username='"+username+"' AND password='"+password+"'");

                    if (rslt.next())
                    {
                        isLoggedIn = true;
                        System.out.println("Member login successful!");
                        
                        boolean isMemberMenuActive = true;
                        while (isMemberMenuActive) {
                            System.out.println("====================================================");
                            System.out.println("+                   Member Menu                    +");
                            System.out.println("----------------------------------------------------");
                            System.out.println("[1]. Lihat Koleksi Buku");
                            System.out.println("[2]. Pinjam Buku");
                            System.out.println("[3]. Kembalikan Buku");
                            System.out.println("[4]. Exit");
                            System.out.println("----------------------------------------------------");
                            System.out.print("Enter choice: ");
                            String menuChoice = scanner.nextLine();

                            switch (menuChoice) {
                                case "1":
                                    System.out.println("Lihat Koleksi Buku");
                                    List<Book> books = BookManager.getAllBooks();
                                    for (Book book : books) {
                                        System.out.println(book);
                                    }
                                    System.out.println("\nSeluruh buku telah ditampilkan.");
                                    System.out.println("Tekan Enter untuk kembali ke menu member...");
                                    scanner.nextLine(); // Menunggu input Enter dari pengguna
                                    break;
                                case "2":
                                    System.out.println("Pinjam Buku");
                                    System.out.print("Masukkan ID buku yang ingin dipinjam: ");
                                    int bookId = scanner.nextInt();
                                    scanner.nextLine(); // Membersihkan newline character

                                    boolean isBorrowed = BookManager.borrowBook(bookId, username);
                                    if (isBorrowed) {
                                        System.out.println("Peminjaman berhasil.");
                                        System.out.println("Buku dengan ID " + bookId + " telah dipinjam oleh " + username);
                                    } else {
                                        System.out.println("Peminjaman gagal.");
                                    }
                                    break;
                                case "3":
                                    System.out.println("Kembalikan Buku");
                                    
                                    // Menampilkan daftar buku yang dipinjam oleh member
                                    System.out.println("Buku yang dipinjam oleh " + username + ":");
                                    BookManager.displayBorrowedBooks(username);

                                    System.out.print("Masukkan ID buku yang akan dikembalikan: ");
                                    int returnBookId = scanner.nextInt();
                                    scanner.nextLine(); // Membersihkan newline character

                                    boolean isReturned = BookManager.returnBook(returnBookId);
                                    if (isReturned) {
                                        System.out.println("Pengembalian berhasil.");
                                    } else {
                                        System.out.println("Pengembalian gagal.");
                                    }
                                    break;
                                case "4":
                                    isMemberMenuActive = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Invalid username or password. Please try again.");
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (choice.equals("2")) {
                System.out.print("\033[H\033[2J");
                System.out.println("---------------------------");
                System.out.println("Login as Admin");
                System.out.println("---------------------------\n");
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                try {
         
                    Statement conn = DBConnector.connection.createStatement();
                    ResultSet rslt;
                    rslt = conn.executeQuery("SELECT * FROM admin WHERE username='"+username+"' AND password='"+password+"'");

                    if (rslt.next())
                    {
                        isLoggedIn = true;
                        System.out.println("Admin login successful!");
                        
                        boolean isAdminMenuActive = true;
                        while (isAdminMenuActive) {
                            System.out.println("====================================================");
                            System.out.println("+                    Admin Menu                    +");
                            System.out.println("----------------------------------------------------");
                            System.out.println("[1]. Lihat Member");
                            System.out.println("[2]. Tambah Member");
                            System.out.println("[3]. Hapus Member");
                            System.out.println("[4]. Lihat Koleksi Buku");
                            System.out.println("[5]. Tambah Buku");
                            System.out.println("[6]. Hapus Buku");
                            System.out.println("[7]. Exit");
                            System.out.println("----------------------------------------------------");
                            System.out.print("Enter choice: ");
                            String adminMenuChoice = scanner.nextLine();

                            switch (adminMenuChoice) {
                                case "1":
                                    System.out.println("Lihat Member\n");
                                    
                                    List<Member> members = MemberManager.getAllMembers();
                                    for (Member member : members) {
                                        System.out.println(member);
                                    }
                                    System.out.println("\nSeluruh member telah ditampilkan.");
                                    System.out.println("Tekan Enter untuk kembali ke menu admin...");
                                    scanner.nextLine(); // Menunggu input Enter dari pengguna
                                    break;
                                case "2":
                                    System.out.println("Tambah Member\n");
                                    
                                    System.out.print("Masukkan username member baru: ");
                                    String newUsername = scanner.nextLine();
                                    System.out.print("Masukkan password member baru: ");
                                    String newPassword = scanner.nextLine();

                                    boolean isMemberAdded = MemberManager.addMember(newUsername, newPassword);
                                    if (isMemberAdded) {
                                        System.out.println("Member berhasil ditambahkan!");
                                    } else {
                                        System.out.println("Gagal menambahkan member.");
                                    }
                                    break;
                                case "3":
                                    System.out.println("Hapus Member\n");
                                    System.out.print("Masukkan username member yang ingin dihapus: ");
                                    String deleteUsername = scanner.nextLine();

                                    boolean isMemberDeleted = MemberManager.deleteMember(deleteUsername);
                                    if (isMemberDeleted) {
                                        System.out.println("Member dengan username " + deleteUsername + " telah dihapus.");
                                    } else {
                                        System.out.println("Gagal menghapus member. Periksa kembali username yang dimasukkan.");
                                    }
                                    break;
                                case "4":
                                    System.out.println("Lihat Koleksi Buku\n");

                                    List<Book> booksWithStatus = BookManager.getAllBooks();
                                    for (Book book : booksWithStatus) {
                                        System.out.println(book);
                                    }
                                    System.out.println("\nSeluruh koleksi buku telah ditampilkan.");
                                    System.out.println("Tekan Enter untuk kembali ke menu admin...");
                                    scanner.nextLine(); // Menunggu input Enter dari pengguna
                                    break;
                                case "5":
                                    System.out.println("Tambah Buku\n");
                                    System.out.print("Masukkan judul buku baru: ");
                                    String newTitle = scanner.nextLine();
                                    System.out.print("Masukkan penulis buku baru: ");
                                    String newAuthor = scanner.nextLine();

                                    boolean isBookAdded = BookManager.addBook(newTitle, newAuthor);
                                    if (isBookAdded) {
                                        System.out.println("Buku berhasil ditambahkan!");
                                    } else {
                                        System.out.println("Gagal menambahkan buku.");
                                    }
                                    break;
                                case "6":
                                    System.out.println("Hapus Buku\n");
                                    System.out.print("Masukkan ID buku yang ingin dihapus: ");
                                    int deleteBookId = scanner.nextInt();
                                    scanner.nextLine(); // Membersihkan newline character

                                    boolean isBookDeleted = BookManager.deleteBook(deleteBookId);
                                    if (isBookDeleted) {
                                        System.out.println("Buku dengan ID " + deleteBookId + " telah dihapus.");
                                    } else {
                                        System.out.println("Gagal menghapus buku. Periksa kembali ID buku yang dimasukkan.\n");
                                    }
                                    break;
                                case "7":
                                    isAdminMenuActive = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.\n");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Invalid username or password. Please try again.\n");
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
                
            } else {
                System.out.println("Invalid choice. Please try again.\n");
            }
        } while (!isLoggedIn);
        
    // End of the program
    System.out.println("Thank you for using my Library Project! - Naldo");
    
    }
}
