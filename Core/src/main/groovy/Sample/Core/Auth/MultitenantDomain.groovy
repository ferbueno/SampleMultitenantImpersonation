package Sample.Core.Auth

import grails.gorm.MultiTenant
import grails.util.Environment

class MultitenantDomain<T> implements MultiTenant<T> {

    String accountId

    static mapping = {
        tenantId name: 'accountId'
    }

    static constraints = {
        accountId nullable: false, blank: false
    }

    MultitenantDomain() {
        if(!Environment.isInitializing()){
            accountId = SampleUser.current?.accountId
        }
    }

    def beforeInsert() {
        accountId = SampleUser.current.accountId
        return super.beforeInsert()
    }

}
