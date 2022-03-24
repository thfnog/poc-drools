package com.spring.drools.api.services;

import com.spring.drools.api.interfaces.json.Score;
import com.spring.drools.api.interfaces.json.User;

public interface ScoreService {

  Score verifyScore(User user, Integer containerId);
}
