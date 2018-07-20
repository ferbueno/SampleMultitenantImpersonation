package Sample.Core.Auth

import grails.plugin.springsecurity.SpringSecurityService
import org.codehaus.groovy.reflection.ReflectionUtils
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class SampleUser extends User {

    private String accountId

    String getAccountId() {
        this.accountId
    }

    void setAccountId(String accountId) {
        this.accountId = accountId
    }

    SampleUser(String username, String password, String accountId, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities)
        this.accountId = accountId
    }

    static SampleUser getCurrent(){
        try {
            SpringSecurityService springSecurityService = grails.util.Holders.findApplicationContext()?.getBean('springSecurityService') as SpringSecurityService
            if (springSecurityService)
                return springSecurityService?.principal instanceof SampleUser ? springSecurityService.principal : null
        } catch (NoSuchBeanDefinitionException ex){
            return null
        }
    }

    static String[] AdminRoles = ['ROLE_SUPERADMIN', 'ROLE_ADMIN']
    static ImpersonationContext impersonate(String accountId, ArrayList<String> roles){
        return new ImpersonationContext(ReflectionUtils.getCallingClass().name, accountId, roles)
    }

    static ImpersonationContext impersonate(String accountId, String[] roles){
        return new ImpersonationContext(ReflectionUtils.getCallingClass().name, accountId, roles)
    }

}
