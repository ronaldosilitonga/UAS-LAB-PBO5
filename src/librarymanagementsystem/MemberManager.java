/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;

/**
 *
 * @author NALDO
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {

    public static List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();

        try {
            Statement stmt = DBConnector.connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM member");

            while (resultSet.next()) {
                int memberId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                Member member = new Member(memberId, username, password);
                members.add(member);
            }

            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }
    
    public static boolean addMember(String username, String password) {
        try {
            PreparedStatement pstmt = DBConnector.connection.prepareStatement("INSERT INTO member (username, password) VALUES (?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public static boolean deleteMember(String username) {
        try {
            PreparedStatement pstmt = DBConnector.connection.prepareStatement(
                "DELETE FROM member WHERE username = ?"
            );
            pstmt.setString(1, username);

            int rowsAffected = pstmt.executeUpdate();

            pstmt.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
