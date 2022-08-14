package learn.jpa_hibernate.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @noinspection DuplicatedCode
 */
public class _3_Insert_Update_Delete {

    private static final String PATH = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "spring_jpa";
    private static final String USER = "root";
    private static final String PASSWORD = "ahroo";

    public static void main(String[] args) {

        try (
                // 1. Get connection to database
                Connection connection = DriverManager.getConnection(PATH + DB_NAME, USER, PASSWORD);

                // 2. Create a statement
                Statement insertStatement = connection.createStatement()
        ) {

            // 3. Execute SQL query
            final String query = "insert into person(name, location, birth_date) value ('Bill', 'Singapore', sysdate())";

            // Closing executeUpdate:  https://stackoverflow.com/q/37155866/10582056

            /*
             * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement
             * or an SQL statement that returns nothing, such as an SQL DDL statement.
             * See docs for more info
             * */
            int result = insertStatement.executeUpdate(query);

            // result -> returns # of rows affected by the query
            System.out.printf("insert status: %d\n", result);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
