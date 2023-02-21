package com.dhitha.mqdemo.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Dhiraj
 */
@Data
@Component
public class ListenerProperties {

  @Value("${connectionName}")
  private String connectionName;

  @Value("${channel}")
  private String channel;

  @Value("${queueManager}")
  private String queueManager;

  @Value("${queueName}")
  private String queueName;

  @Value("${userName}")
  private String userName;

  @Value("${password}")
  private String password;

  @Value("${reconnectionTimeout}")
  private Integer reconnectionTimeout;
}
