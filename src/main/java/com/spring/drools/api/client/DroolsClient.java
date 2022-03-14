package com.spring.drools.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.drools.api.interfaces.json.Score;
import com.spring.drools.api.interfaces.json.ScoreResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.GetObjectsCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
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

  private static final String CONTAINER_ID = "MVP_Orquestrador_1.0.0-SNAPSHOT"; // TODO: será por parceiro
  private static final String SCORE_IDENTIFIER = "Score";

  @Value("${rest.api.drools-server.uri}")
  private String uri;

  @Value("${rest.api.drools-server.username}")
  private String username;

  @Value("${rest.api.drools-server.password}")
  private String password;

  private final KieServices kieServices;
  private final RuleServicesClient rulesClient;

  @SneakyThrows
  public Score validateScore(Score score) {
    BatchExecutionCommand command = buildCommands(score, SCORE_IDENTIFIER);
    ServiceResponse<ExecutionResults> response = rulesClient.executeCommandsWithResults(CONTAINER_ID, command);

    ExecutionResults results = response.getResult();
    return (Score) results.getValue(SCORE_IDENTIFIER);
  }

  private <T>BatchExecutionCommand buildCommands(T object, String identifier) {
    KieCommands commandFactory = kieServices.getCommands();

    List<Command<?>> commands = new ArrayList<>();
    commands.add(commandFactory.newInsert(object, identifier));
    commands.add(commandFactory.newFireAllRules());

    return commandFactory.newBatchExecution(commands);
  }
}
