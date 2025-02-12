package com.infominez.catalog.brand.tenant;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest httpServletRequest) {
            String tenantId = httpServletRequest.getHeader("X-Tenant-ID"); // Extract tenantId from header
            System.out.println("Tenant ID : " + tenantId);
            if (tenantId != null && !tenantId.isBlank()) {
                TenantContextHolder.setTenantId(tenantId);
            }
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);

        } finally {
            TenantContextHolder.clear(); // Clear the tenantId after the request processing
        }
    }
}
