package learn.jpa_hibernate.jdbc;

import java.sql.*;

public class _2_Read {

    private static final String PATH = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "spring_jpa";
    private static final String USER = "root";
    private static final String PASSWORD = "ahroo";

    public static void main(String[] args) {

        try (
                // 1. Get connection to database
                Connection connection = DriverManager.getConnection(PATH + DB_NAME, USER, PASSWORD);

                // 2. Create a statement
                Statement readStatement = connection.createStatement()
        ) {

            // 3. Execute SQL query
            final String query = "select * from students";
            try (ResultSet queryResult = readStatement.executeQuery(query)) {

                // 4. Process the result set

                // students table has id, name and passport_id
                // id -> int
                // name  -> String
                // passport_id -> int
                while (queryResult.next()) {

                    // retrieves the current row number
                    int row = queryResult.getRow();

                    // We can either use column index / column label -> https://stackoverflow.com/q/186799/10582056
                    // NOTE: In mysql indices start with 1 (NOT 0)
                    int id = queryResult.getInt(1);
                    String name = queryResult.getString("name");
                    int passportId = queryResult.getInt("passport_id");

                    // print it
                    System.out.printf("%10d%10d%20s%20d\n", row, id, name, passportId);
                }

            }

        } catch (SQLException sqlException) {

            // DriverManager#getConnection throws this exception
            sqlException.printStackTrace();
        }
    }
}
