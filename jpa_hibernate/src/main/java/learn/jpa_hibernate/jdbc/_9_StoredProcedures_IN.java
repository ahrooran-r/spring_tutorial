package learn.jpa_hibernate.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @noinspection DuplicatedCode
 */
public class _9_StoredProcedures_IN {

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
                        "{call increase_salaries_for_department(?, ?)}"
                )
        ) {

            // 3. Set parameter values for type and position
            callableStatement.setString(1, "Engineering");
            callableStatement.setDouble(2, 10_000);

            // 4. Execute query
            callableStatement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
