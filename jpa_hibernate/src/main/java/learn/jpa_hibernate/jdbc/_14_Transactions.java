package learn.jpa_hibernate.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @noinspection ALL
 */
public class _14_Transactions {

    private static final String PATH = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "spring_jpa";
    private static final String USER = "root";
    private static final String PASSWORD = "ahroo";

    public static void main(String[] args) {

        try (
                // 1. Get connection to database
                Connection connection = DriverManager.getConnection(PATH + DB_NAME, USER, PASSWORD);

                // 2. Create a statement
                Statement deleteStatement = connection.createStatement()
        ) {

            // Set auto commit to false
            connection.setAutoCommit(false);

            // 3. Execute SQL query
            final String query = "delete from employees where department = 'Engineering'";
            int result = deleteStatement.executeUpdate(query);
            System.out.printf("delete status: %d\n", result);

            // Now time to COMMIT or ROLLBACK
            if (askFromUSer()) connection.commit();
            else connection.rollback();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private static boolean askFromUSer() {
        // Always going to return false -> So we can observe whether table is deleted or not
        return false;
    }
}
