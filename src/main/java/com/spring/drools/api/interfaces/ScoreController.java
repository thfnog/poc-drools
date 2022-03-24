package com.spring.drools.api.interfaces;

import com.spring.drools.api.interfaces.json.Score;
import com.spring.drools.api.interfaces.json.User;
import com.spring.drools.api.services.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScoreController {

	private final ScoreService scoreService;

	@PostMapping("/{partnerId}/score")
	public Score verifyScore(
			@PathVariable Integer partnerId,
			@RequestBody User user
	) {
		return scoreService.verifyScore(user, partnerId);
	}

}
