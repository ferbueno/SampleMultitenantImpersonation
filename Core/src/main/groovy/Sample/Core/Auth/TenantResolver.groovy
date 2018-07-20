package Sample.Core.Auth

import grails.core.GrailsApplication
import grails.plugin.springsecurity.SpringSecurityService
import org.grails.datastore.mapping.multitenancy.AllTenantsResolver
import org.grails.datastore.mapping.multitenancy.exceptions.TenantNotFoundException
import org.springframework.beans.factory.annotation.Autowired

class TenantResolver implements AllTenantsResolver {

    @Autowired
    GrailsApplication grailsApplication


//Doesn't work. Fails because it creates a cycle with Hibernate
//    @Autowired
//    @Lazy
//    SpringSecurityService springSecurityService

    @Override
    Iterable<Serializable> resolveTenantIds() {
        return ['account1']
    }

    @Override
    Serializable resolveTenantIdentifier() throws TenantNotFoundException {
        String accountId = loggedAccount()

        if (accountId) {
            return accountId
        }

        throw new TenantNotFoundException("Tenant could not be resolved")
    }

    String loggedAccount() {
        SpringSecurityService springSecurityService = grailsApplication.mainContext.getBean('springSecurityService') as SpringSecurityService
        if (springSecurityService.principal instanceof String) {
            return springSecurityService.principal
        }

        if (springSecurityService.principal instanceof SampleUser) {
            return ((SampleUser) springSecurityService.principal).accountId
        }
        null
    }


}
