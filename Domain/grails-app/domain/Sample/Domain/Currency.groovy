package Sample.Domain

import Sample.Core.Auth.SampleUser
import grails.gorm.MultiTenant
import grails.util.Environment

class Currency implements MultiTenant<Currency> {

    String currencyId
    String description
    String accountId

    Currency() {
        if(!Environment.isInitializing()){
            accountId = SampleUser.current?.accountId
        }
    }

    def beforeInsert() {
        accountId = SampleUser.current.accountId
    }

    static constraints = {
        currencyId nullable: false, blank: false
        description nullable: false, blank: false
        accountId nullable: false, blank: false
    }

    static mapping = {
        id name: 'currencyId', generator: 'assigned'
        tenantId name: 'accountId'
    }
}
