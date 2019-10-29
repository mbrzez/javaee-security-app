package pl.brzezins.app;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "userRealm")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:/mysqlds",
        callerQuery = "select password from callers where name = ?",
        groupsQuery = "select group_name from caller_groups where caller_name = ?"
)
public class Application {

}
