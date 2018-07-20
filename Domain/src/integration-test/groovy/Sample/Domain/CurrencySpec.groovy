package Sample.Domain

import Sample.Core.Auth.SampleUser
import grails.test.mixin.TestFor
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification
import javax.transaction.Transactional

/**
 * Integration test for currency
 */
@Integration
@Rollback
@TestFor(Currency)
class CurrencySpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    @Transactional
    void "currency is created with impersonated account"() {
        when:
            SampleUser.impersonate(accountId, SampleUser.AdminRoles).withCloseable {
                Currency currency = new Currency(
                        currencyId: currencyId,
                        description: description
                )
                currency.save(flush: true, failOnError: true)
            }
        then:
            SampleUser.impersonate(accountId, SampleUser.AdminRoles).withCloseable {
                Currency currency = Currency.get(currencyId)
                currency.description == description
            }
        where:
            accountId | currencyId | description
            "account1" | "USD" | "US Dollar"
            "account1" | "MXN" | "Peso Mexicano"
    }
}
