package pl.brzezins.store;

import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;

@Dependent
public class DBConnector {
    @Inject
    private Logger logger;

    @Resource(name = "mysqlds")
    private DataSource dataSource;

    private DataSource getDataSource() {
        return this.dataSource;
    }

    public void execute(String query) {
        logger.info("Executing update: " + query);

        try (Connection connection = this.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            boolean result = statement.execute(query);

            logger.info("Query {} result was: {}", query, result);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public boolean executeQuery(String query) {
        logger.info("Executing query: " + query);

        try (Connection connection = this.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            boolean result = resultSet.first();

            if (result) {
                logger.info("Query {} returned a result", query);
            } else {
                logger.info("Query {} did not return a result", query);
            }

            return result;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return false;
    }
}
