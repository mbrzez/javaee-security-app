package pl.brzezins.app;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@BasicAuthenticationMechanismDefinition(realmName = "userRealm")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:/mysqlds",
        callerQuery = "select password from users where user = ?",
        groupsQuery = "select group_name from user_groups where user = ?"
)
@ApplicationScoped
public class Application {
}
