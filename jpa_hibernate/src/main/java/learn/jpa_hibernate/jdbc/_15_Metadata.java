package learn.jpa_hibernate.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @noinspection DuplicatedCode
 */
public class _15_Metadata {

    private static final String PATH = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "spring_jpa";
    private static final String USER = "root";
    private static final String PASSWORD = "ahroo";

    public static void main(String[] args) {

        try (
                // 1. Get connection to database
                Connection connection = DriverManager.getConnection(PATH + DB_NAME, USER, PASSWORD);
        ) {

            // 2. get Metadata
            DatabaseMetaData metaData = connection.getMetaData();

            // 3. Info about database
            System.out.println("Product name: " + metaData.getDatabaseProductName());
            System.out.println("Product version: " + metaData.getDatabaseProductVersion());

            // 4. Info about driver
            System.out.println("JDBC driver name: " + metaData.getDriverName());
            System.out.println("driver version: " + metaData.getDriverVersion());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
