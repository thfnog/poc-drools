package com.spring.drools.api.client;

import com.spring.drools.api.interfaces.json.Partner;
import com.spring.drools.api.interfaces.json.Score;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.RuleServicesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroolsClient {

  private static final String SCORE_IDENTIFIER = "score";
  private static final String PARTNER_IDENTIFIER = "partner";

  @Value("${rest.api.drools-server.uri}")
  private String uri;

  @Value("${rest.api.drools-server.username}")
  private String username;

  @Value("${rest.api.drools-server.password}")
  private String password;

  @Value("${rest.api.drools-server.containers.admin}")
  private String adminContainerId;

  private final KieServices kieServices;
  private final RuleServicesClient rulesClient;

  public Partner getPartner(Integer partnerId) {
    Partner partner = Partner.builder().id(partnerId).build();
    return (Partner) getResultValue(partner, adminContainerId, PARTNER_IDENTIFIER);
  }

  public Score validateScore(Double totalScore, String containerId) {
    Score score = Score.builder().value(totalScore).build();
    return (Score) getResultValue(score, containerId, SCORE_IDENTIFIER);
  }

  private <T>Object getResultValue(T object, String containerId, String identifier) {
    BatchExecutionCommand command = buildCommands(object, identifier);
    ServiceResponse<ExecutionResults> response = rulesClient.executeCommandsWithResults(containerId, command);

    ExecutionResults results = response.getResult();
    return results.getValue(identifier);
  }

  private <T>BatchExecutionCommand buildCommands(T object, String identifier) {
    KieCommands commandFactory = kieServices.getCommands();

    /*
      ! É possível rodar o camando para um group de rules específico utilizando o newAgendaGroupSetFocus("nome do group")
     */
    List<Command<?>> commands = new ArrayList<>();
    commands.add(commandFactory.newInsert(object, identifier));
    commands.add(commandFactory.newFireAllRules());

    return commandFactory.newBatchExecution(commands);
  }
}
