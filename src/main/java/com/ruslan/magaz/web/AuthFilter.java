package com.ruslan.magaz.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(value="/list")
public class AuthFilter implements Filter {
    private final String role;
   
    public AuthFilter(String role) {
        this.role = role;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpSession session = httpReq.getSession(false);
        if (session != null && "user".equals(session.getAttribute("role"))) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendError(403);
        }

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
