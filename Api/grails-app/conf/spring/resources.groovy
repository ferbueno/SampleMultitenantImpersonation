import Sample.Core.Auth.ImpersonationContext
import Sample.Core.Auth.TenantResolver

// Place your Spring DSL code here
beans = {
    tenantResolver(TenantResolver)
}
