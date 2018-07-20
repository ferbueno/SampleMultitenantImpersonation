grails.plugin.console.enabled = true
grails.plugin.springsecurity.rejectIfNoRule = true
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.controllerAnnotations.staticRules =
        [
                [pattern: '/login/auth', access: ['permitAll']],
                [pattern: '/error/**', access: ['permitAll']],
                [pattern: '/j_spring_security_check', access: ['permitAll']],
                [pattern: '/j_spring_security_logout', access: ['permitAll']],
                [pattern: '/grails-errorhandler', access: ['permitAll']],
                [pattern: '/assets/**', access: ['permitAll']],
                [pattern: '/**', access: ['permitAll']],

                //[pattern: '/hello/**',       access: ['ROLE_ANONYMOUS', 'ROLE_ADMIN']],

        ]

grails.plugin.springsecurity.filterChain.chainMap = [
        //Stateless chain
        [
                pattern: '/**',
                filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
        ]/*,

  //Traditional chain
  [
    pattern: '/**',
    filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
  ]*/
]