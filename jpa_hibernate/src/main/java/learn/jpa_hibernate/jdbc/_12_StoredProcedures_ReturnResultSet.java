package learn.jpa_hibernate.jdbc;

import java.sql.*;

/**
 * @noinspection DuplicatedCode
 */
public class _12_StoredProcedures_ReturnResultSet {

    private static final String PATH = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "spring_jpa";
    private static final String USER = "root";
    private static final String PASSWORD = "ahroo";

    public static void main(String[] args) {

        try (
                // 1. Get connection to database
                Connection connection = DriverManager.getConnection(PATH + DB_NAME, USER, PASSWORD);

                // 2. Create a CALLABLE statement
                CallableStatement callableStatement = connection.prepareCall(
                        "{call get_employees_for_department(?)}"
                )
        ) {

            // 3. Set IN parameter values for type and position
            callableStatement.setString(1, "Engineering");

            // 4. Execute query
            callableStatement.execute();

            // 4. Get the result set
            try (ResultSet callResult = callableStatement.getResultSet()) {

                // 6. Now get the output
                while (callResult.next()) {

                    int id = callResult.getInt("id");
                    String lastName = callResult.getString("last_name");
                    String firstName = callResult.getString("first_name");
                    String email = callResult.getString("email");
                    String department = callResult.getString("department");
                    double salary = callResult.getInt("salary");

                    // print it
                    System.out.printf("%5d%10s%10s%25s%15s%15.2f\n", id, firstName, lastName, email, department, salary);
                }

            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
