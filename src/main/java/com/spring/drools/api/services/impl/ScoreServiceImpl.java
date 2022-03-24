package com.spring.drools.api.services.impl;

import com.spring.drools.api.client.DroolsClient;
import com.spring.drools.api.interfaces.json.Integration;
import com.spring.drools.api.interfaces.json.Partner;
import com.spring.drools.api.interfaces.json.Score;
import com.spring.drools.api.interfaces.json.User;
import com.spring.drools.api.services.ScoreService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

  private final DroolsClient droolsClient;

  private final Map<String, Double> integrationsMap = Map.of("CLEAR_SALE", 70.0,
      "EMAIL_AGE", 90.0); // simula integrações das quais retornar valores de score distintas

  @Override
  public Score verifyScore(User user, Integer partnerId) {
    Partner partner = droolsClient.getPartner(partnerId);
    double totalScore = partner.getIntegrations().stream()
        .map(Integration::getServiceName)
        .map(integrationsMap::get) // faz a chamada em cada integração com cpf e obtem o score
        .mapToDouble(Double::doubleValue)
        .average() // obtem a média de score das chamadas
        .orElseThrow();
    return validateScore(totalScore, partner.getContainerId()); // valida a média do perceiro via drools
  }

  private Score validateScore(Double totalScore, String containerId) {
    return droolsClient.validateScore(totalScore, containerId);
  }
}
