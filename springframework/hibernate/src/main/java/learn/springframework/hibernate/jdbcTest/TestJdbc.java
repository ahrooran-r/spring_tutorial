package learn.springframework.hibernate.jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/hb_student_tracker";
        String user = "hbstudent";
        String pwd = "hbstudent";
        try {
            Connection myConn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Conn success");
        } catch (Exception e) {
            System.out.println("Conn fail");
            e.printStackTrace();
        }
    }
}
