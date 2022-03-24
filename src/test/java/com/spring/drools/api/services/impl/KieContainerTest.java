package com.spring.drools.api.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

import com.spring.drools.api.config.DroolsConfig;
import com.spring.drools.api.interfaces.json.Score;
import com.spring.drools.api.interfaces.json.response.ScoreResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.mockito.MockitoAnnotations;

public class KieContainerTest {

  private static final String GLOBAL_RESPONSE_IDENTIFIER = "scoreResponse";
  private static final String AGENDA_GROUP_A = "a";
  private static final String AGENDA_GROUP_C = "c";
  private static final String AGENDA_GROUP_B = "b";

  private KieContainer kieContainer;

  @SneakyThrows
  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    kieContainer = spy(new DroolsConfig().getKieContainer());
  }

  @Test
  public void ShouldScoreApprovedWithoutGroupFocusTest() {
    ScoreResponse scoreResponse = ScoreResponse.builder().build();
    Score score = Score.builder()
        .value(91.0)
        .build();

    KieSession kieSession = kieContainer.newKieSession();
    kieSession.insert(score);
    kieSession.setGlobal(GLOBAL_RESPONSE_IDENTIFIER, scoreResponse);
    kieSession.fireAllRules();
    kieSession.destroy();

    assertTrue(scoreResponse.getApproved());
    assertEquals(91.0, scoreResponse.getValue());
    assertEquals("Score Equals 91 - Without Group", scoreResponse.getMessage());
  }

  @Test
  public void ShouldAnotherScoreApprovedWithoutGroupFocusTest() {
    ScoreResponse scoreResponse = ScoreResponse.builder().build();
    Score score = Score.builder()
        .value(92.0)
        .build();

    KieSession kieSession = kieContainer.newKieSession();
    kieSession.insert(score);
    kieSession.setGlobal(GLOBAL_RESPONSE_IDENTIFIER, scoreResponse);
    kieSession.fireAllRules();
    kieSession.destroy();

    assertTrue(scoreResponse.getApproved());
    assertEquals(92.0, scoreResponse.getValue());
    assertNull(scoreResponse.getMessage());
  }

  @Test
  public void ShouldScoreRuleRunningInGroupATest() {
    ScoreResponse scoreResponse = ScoreResponse.builder().build();
    Score score = Score.builder()
        .value(90.0)
        .build();

    KieSession kieSession = kieContainer.newKieSession();
    kieSession.getAgenda().getAgendaGroup(AGENDA_GROUP_A).setFocus();
    kieSession.insert(score);
    kieSession.setGlobal(GLOBAL_RESPONSE_IDENTIFIER, scoreResponse);
    kieSession.fireAllRules();
    kieSession.destroy();

    assertTrue(scoreResponse.getApproved());
    assertEquals(90.0, scoreResponse.getValue());
  }

  @Test
  public void ShouldScoreRuleRunningInGroupBTest() {
    ScoreResponse scoreResponse = ScoreResponse.builder().build();
    Score score = Score.builder()
        .value(79.0)
        .build();

    KieSession kieSession = kieContainer.newKieSession();
    kieSession.getAgenda().getAgendaGroup(AGENDA_GROUP_B).setFocus();
    kieSession.insert(score);
    kieSession.setGlobal(GLOBAL_RESPONSE_IDENTIFIER, scoreResponse);
    kieSession.fireAllRules();
    kieSession.destroy();

    assertTrue(scoreResponse.getApproved());
    assertEquals(79.0, scoreResponse.getValue());
  }

  @Test
  public void ShouldScoreRuleRunningInGroupCTest() {
    ScoreResponse scoreResponse = ScoreResponse.builder().build();
    Score score = Score.builder()
        .value(81.0)
        .build();

    KieSession kieSession = kieContainer.newKieSession();
    kieSession.getAgenda().getAgendaGroup(AGENDA_GROUP_B).setFocus();
    kieSession.insert(score);
    kieSession.setGlobal(GLOBAL_RESPONSE_IDENTIFIER, scoreResponse);
    kieSession.fireAllRules();
    kieSession.destroy();

    assertNull(scoreResponse.getApproved());
  }

  @Test
  public void ShouldScoreNotApprovedRunningGroupCTest() {
    ScoreResponse scoreResponse = ScoreResponse.builder().build();
    Score score = Score.builder()
        .value(79.0)
        .build();

    KieSession kieSession = kieContainer.newKieSession();
    kieSession.getAgenda().getAgendaGroup(AGENDA_GROUP_C).setFocus();
    kieSession.insert(score);
    kieSession.setGlobal(GLOBAL_RESPONSE_IDENTIFIER, scoreResponse);
    kieSession.fireAllRules();
    kieSession.destroy();

    assertFalse(scoreResponse.getApproved());
    assertEquals(79.0, scoreResponse.getValue());
    assertEquals("Score below 80.0% - Group C", scoreResponse.getMessage());
  }

}
