package com.spring.drools.api.config;

import com.spring.drools.api.interfaces.json.Score;
import java.util.Collections;
import java.util.HashSet;
import org.kie.api.KieServices;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.credentials.EnteredCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KieServerConfig {

  @Value("${rest.api.drools-server.uri}")
  private String uri;

  @Value("${rest.api.drools-server.username}")
  private String username;

  @Value("${rest.api.drools-server.password}")
  private String password;

  @Bean
  public KieServices kieServices() {
    return KieServices.Factory.get();
  }

  @Bean
  public RuleServicesClient ruleServicesClient() {
    CredentialsProvider credentialsProvider = new EnteredCredentialsProvider(username, password);
    KieServicesConfiguration kieServicesConfig = KieServicesFactory
        .newRestConfiguration(uri, credentialsProvider);
    kieServicesConfig.setMarshallingFormat(MarshallingFormat.JSON);
    KieServicesClient kieServicesClient = KieServicesFactory
        .newKieServicesClient(kieServicesConfig);
    return kieServicesClient.getServicesClient(RuleServicesClient.class);
  }

}
