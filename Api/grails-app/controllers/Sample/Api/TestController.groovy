package Sample.Api

import Sample.Core.Auth.SampleUser
import Sample.Domain.Currency
import grails.rest.*
import grails.converters.*

class TestController {
	static responseFormats = ['json', 'xml']
	
    def index() {
        String accountId = 'test'
        SampleUser.impersonate('account1', SampleUser.AdminRoles).withCloseable {
            accountId = SampleUser.current.accountId
        }
        render "Hello"
    }

    def show() {
        def currency
        SampleUser.impersonate('account1', SampleUser.AdminRoles).withCloseable {
            currency = Currency.get(params.id).properties.subMap(['currencyId', 'description'])
        }
        respond currency
    }

    def save() {
        SampleUser.impersonate('account1', SampleUser.AdminRoles).withCloseable {
            Currency.withTransaction {
                Currency currency = new Currency(
                        currencyId: request.JSON.currencyId,
                        description: request.JSON.description
                )
                currency.save(flush: true, failOnError: true)
            }
        }
        render request.JSON.currencyId
    }
}
