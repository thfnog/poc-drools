package com.spring.drools.api.interfaces.json;

import java.io.Serializable;
import lombok.Data;

@Data
public class Integration implements Serializable {

  private static final long serialVersionUID = 1L;

  private String serviceName;

}
