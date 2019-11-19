package pl.brzezins.store;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
public class CredentialStorage {
    @Inject
    private Logger logger;

    @Inject
    private DBConnector connector;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        logger.info("Dropping old tables if exist");

        connector.execute("DROP TABLE IF EXISTS users");
        connector.execute("DROP TABLE IF EXISTS user_groups");

        logger.info("Creating tables if not exist");

        connector.execute("CREATE TABLE IF NOT EXISTS users(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, user VARCHAR(255), password VARCHAR(255))");
        connector.execute("CREATE TABLE IF NOT EXISTS user_groups(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, user VARCHAR(255), group_name VARCHAR(255))");

        if (!connector.executeQuery("SELECT * FROM users WHERE user = 'user' OR user = 'admin'")) {
            logger.info("Inserting users...");

            connector.execute("INSERT INTO users VALUES(null, 'admin', '" + passwordHash.generate("pass".toCharArray()) + "')");
            connector.execute("INSERT INTO users VALUES(null, 'user', '" + passwordHash.generate("pass".toCharArray()) + "')");

            connector.execute("INSERT INTO user_groups VALUES(null, 'admin', 'admin_role')");
            connector.execute("INSERT INTO user_groups VALUES(null, 'admin', 'user_role')");
            connector.execute("INSERT INTO user_groups VALUES(null, 'user', 'user_role')");

            logger.info("All users created!");
        }
    }
}
