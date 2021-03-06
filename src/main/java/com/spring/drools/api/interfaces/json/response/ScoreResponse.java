package com.spring.drools.api.interfaces.json.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double value;
	private Boolean approved;
	private String message;

}
