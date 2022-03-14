package com.spring.drools.api.interfaces;

import com.spring.drools.api.interfaces.json.Score;
import com.spring.drools.api.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScoreController {

	private final ScoreService scoreService;

	@PostMapping("/score")
	public Score verifyScore(@RequestBody Score score) {
		return scoreService.verifyScore(score);
	}

}
