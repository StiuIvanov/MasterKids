package com.example.demo.config;

import com.example.demo.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends GlobalMethodSecurityConfiguration {

    @Autowired
    private MasterKidsMethodSecurityExpressionHandler masterKidsMethodSecurityExpressionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return super.createExpressionHandler();
    }

    @Bean
    public MasterKidsMethodSecurityExpressionHandler createExpressionHandler(ParentService parentService){
        return new MasterKidsMethodSecurityExpressionHandler(parentService);
    }
}
