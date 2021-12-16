package com.example.demo.config;

import com.example.demo.service.ParentService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class MasterKidsMethodSecurityExpressionHandler
        extends DefaultMethodSecurityExpressionHandler {

    private final ParentService parentService;

    public MasterKidsMethodSecurityExpressionHandler(ParentService parentService) {
        this.parentService = parentService;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
                                                                              MethodInvocation invocation) {
        ParentSecurityExpressionRoot root = new ParentSecurityExpressionRoot(authentication);


        root.setParentService(parentService);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(new AuthenticationTrustResolverImpl());
        root.setRoleHierarchy(getRoleHierarchy());

        return root;
    }
}
