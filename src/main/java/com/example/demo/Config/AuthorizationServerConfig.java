package com.example.demo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
@Configuration
@EnableAuthorizationServer // 必须
//@EnableResourceServer //必须

public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {
	
    @Autowired
    AuthenticationManager authenticationManager;
    
	@Autowired
	UserDetailsService userDetailsService ;
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients()./*tokenKeyAccess("permissAll()").*/checkTokenAccess("isAuthenticated()");
	}
	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		 clients
		 .inMemory()
         .withClient("client_id") // 配置默认的client
         .authorizedGrantTypes(
                 "password", "refresh_token","client_credentials")
         .scopes("read")
         .secret("secret")
         .autoApprove("read");

	}
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	    
	}
	@Bean
	TokenStore tokenStore(){
		return new InMemoryTokenStore();
	}
}
