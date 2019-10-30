package pl.brzezins.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.brzezins.app.Application;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.sql.DataSource;
import java.sql.*;

@Singleton
@Startup
public class CredentialStorage {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Resource(lookup = "java:/mysqlds")
    private DataSource dataSource;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @PostConstruct
    private void init() {
        logger.info("Creating tables if do not exist");

        executeUpdate("CREATE TABLE IF NOT EXISTS users(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, user VARCHAR(255), password VARCHAR(255))");
        executeUpdate("CREATE TABLE IF NOT EXISTS user_groups(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, user VARCHAR(255), group_name VARCHAR(255))");

        if (!execute("SELECT * FROM users WHERE user = 'user' OR user = 'admin'")) {
            logger.info("Inserting users...");

            executeUpdate("INSERT INTO users VALUES(null, 'admin', '" + passwordHash.generate("pass".toCharArray()) + "')");
            executeUpdate("INSERT INTO users VALUES(null, 'user', '" + passwordHash.generate("pass".toCharArray()) + "')");

            executeUpdate("INSERT INTO user_groups VALUES(null, 'admin', 'admin_role')");
            executeUpdate("INSERT INTO user_groups VALUES(null, 'admin', 'user_role')");
            executeUpdate("INSERT INTO user_groups VALUES(null, 'user', 'user_role')");

            logger.info("All users created!");
        }
    }

    private DataSource getDataSource() {
        return this.dataSource;
    }

    private void executeUpdate(String query) {
        logger.info("Executing: " + query);

        try (Connection connection = this.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                int result = statement.executeUpdate(query);
                logger.info("Query {} modified {} rows!", query, result);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean execute(String query) {
        logger.info("Executing: " + query);

        try (Connection connection = this.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);
            boolean result = resultSet.first();

            if (result) {
                logger.info("Query {} returned results!", query);
            } else {
                logger.info("Table {} did not return results!", query);
            }

            return result;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    private boolean checkIfTableExists(String tableName) {
        logger.info("Checking if table {} exists", tableName);

        try (Connection connection = this.getDataSource().getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, null);

            boolean result = resultSet.first();

            if (result) {
                logger.info("Table {} exists", tableName);
            } else {
                logger.info("Table {} does not exist", tableName);
            }

            return result;

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return false;
    }
}
