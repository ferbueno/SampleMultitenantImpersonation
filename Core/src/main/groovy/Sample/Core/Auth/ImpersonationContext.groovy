package Sample.Core.Auth

import groovy.util.logging.Slf4j
import org.codehaus.groovy.reflection.ReflectionUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder

@Slf4j
class ImpersonationContext implements AutoCloseable, Closeable {
    private def oldAuthentication

    ImpersonationContext(String caller, String accountId, ArrayList<String> roles){
        oldAuthentication = SecurityContextHolder.getContext().authentication
        impersonate(caller,accountId, roles)
    }

    ImpersonationContext(String caller, String accountId, String[] roles){
        oldAuthentication = SecurityContextHolder.getContext().authentication
        impersonate(caller,accountId, roles)
    }

    private static void impersonate(String accountId, ArrayList<String> roles){
        impersonate(ReflectionUtils.getCallingClass().name, accountId, (String[])roles.toArray())
    }

    private static void impersonate(String accountId, String[] roles){
        impersonate(ReflectionUtils.getCallingClass().name, accountId, roles)
    }

    private static void impersonate(String caller, String accountId, ArrayList<String> roles){
        impersonate(caller, accountId, (String[])roles.toArray())
    }

    private static void impersonate(String caller, String accountId, String[] roles){
        def user = new SampleUser("system-${caller}", 'N/A', accountId,
                AuthorityUtils.createAuthorityList(roles))

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                AuthorityUtils.createAuthorityList(roles))
        SecurityContextHolder.getContext().setAuthentication(authentication)
        log.debug("IMPERSONATING user for ${accountId} ${SecurityContextHolder.getContext().authentication?.principal?.properties}")
    }

    @Override
    void close() throws Exception {
        log.debug("Stops impersonating user for ${SecurityContextHolder.getContext().authentication?.principal?.properties}")
        SecurityContextHolder.getContext().setAuthentication(oldAuthentication)
    }
}
