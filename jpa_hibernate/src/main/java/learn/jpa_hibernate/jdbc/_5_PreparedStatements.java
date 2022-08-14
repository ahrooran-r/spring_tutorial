package learn.jpa_hibernate.jdbc;

import java.sql.*;

/**
 * @noinspection DuplicatedCode
 */
public class _5_PreparedStatements {

    private static final String PATH = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "spring_jpa";
    private static final String USER = "root";
    private static final String PASSWORD = "ahroo";

    public static void main(String[] args) {

        try (
                // 1. Get connection to database
                Connection connection = DriverManager.getConnection(PATH + DB_NAME, USER, PASSWORD);

                // 2. Create a prepared statement
                PreparedStatement selectStatement = connection.prepareStatement(
                        "select * from employees where salary > ? and department = ?"
                )
        ) {

            // 3. Set parameter values for type and position
            //      -> param positions are 1 based from left->right (NOT 0 based)
            selectStatement.setDouble(1, 80_000);
            selectStatement.setString(2, "Legal");

            // 4. Execute query
            try (ResultSet queryResult = selectStatement.executeQuery()) {

                // 5. Process the result set
                while (queryResult.next()) {

                    int id = queryResult.getInt("id");
                    String lastName = queryResult.getString("last_name");
                    String firstName = queryResult.getString("first_name");
                    String email = queryResult.getString("email");
                    String department = queryResult.getString("department");
                    double salary = queryResult.getInt("salary");

                    // print it
                    System.out.printf("%5d%10s%10s%25s%10s%15.2f\n", id, firstName, lastName, email, department, salary);
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
