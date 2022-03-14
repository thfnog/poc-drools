package com.spring.drools.api.service.impl;

import com.spring.drools.api.client.DroolsClient;
import com.spring.drools.api.interfaces.json.Score;
import com.spring.drools.api.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

//  private final KieSession kieSession;
  private final DroolsClient droolsClient;

  @Override
  public Score verifyScore(Score score) {
//    kieSession.insert(score);
//    kieSession.fireAllRules();
    return droolsClient.validateScore(score);
  }
}
