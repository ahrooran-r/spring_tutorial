package learn.jpa_hibernate.jdbc;

import java.sql.*;

/**
 * @noinspection DuplicatedCode
 */
public class _11_StoredProcedures_INOUT {

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
                        "{call greet_the_department(?)}"
                )
        ) {

            // 3. Set IN parameter values for type and position
            callableStatement.setString(1, "Engineering");

            // 4. Register OUT parameter
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            // NOTE: Same parameter in position 1 is used for both IN and OUT

            // 5. Execute query
            callableStatement.execute();

            // 6. Now get the output
            String result = callableStatement.getString(1);
            System.out.println("Result: " + result);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
