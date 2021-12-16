package com.example.demo.config;

import com.example.demo.service.ParentService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class ParentSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private ParentService parentService;
    private Object filterObject;
    private Object returnObject;

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public ParentSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }


    public boolean isParentOrAdmin(Long id) {
        String username = currentUsername();

        if (username != null) {
            return parentService.isParentOrAdmin(username, id);
        }

        return false;
    }

    public boolean isAdmin(){
        Authentication authentication = getAuthentication();

        List<String> collect = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return collect.stream().anyMatch(a->a.equals("ROLE_ADMIN"));
    }

    public String currentUsername() {
        Authentication authentication = getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }


    public void setParentService(ParentService parentService) {
        this.parentService = parentService;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
