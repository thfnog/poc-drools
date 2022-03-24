package com.spring.drools.api.interfaces.json;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String name;
	private String containerId;

	private List<Integration> integrations;

}
