# Grails Impersonated Multi Tenant Sample Project

This project contains a sample project which works with a Multi Tenant Domain and an impersonatable Tenant, available through 2 different plugins.

The idea is to be able to perform a `CRUD` operation on a `Domain` class, which in this case is only `Currency`. When *running*  the application, the `impersonation` and `MultiTenant` system works, whilst *testing* the app, it does not.

The original error was `NoSuchBeanDefinitionException`, but I was unable to replicate it. The problem now is that the `User impersonation` won't work, and thus giving a `ValidationException`.

### API

The main `Grails` application. It compiles both plugins in order to work. `TestController` works both for `GET` and `POST` requests.

`index` only impersonates the account and renders `"Hello $accountId"`.

`save` impersonates before storing the `Currency` and stores it into the Database.

Bean references are stored inside `resources.groovy`.

### Domain

The `Domain` plugin contains the domain classes to be tested. For now, it's only one: `Currency`.

### Core

The `Core` plugin contains every class necessary to authenticate and impersonate a `User` with a specific `accountId`.

