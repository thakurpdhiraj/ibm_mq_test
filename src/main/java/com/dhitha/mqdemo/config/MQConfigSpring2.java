package com.dhitha.mqdemo.config;

import com.dhitha.mqdemo.properties.ListenerProperties;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.common.CommonConstants;
import javax.jms.JMSException;
import javax.jms.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.util.ErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @author Dhiraj
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class MQConfigSpring2 {

  private final ListenerProperties listenerProperties;

  private final ErrorHandler listenerErrorHandler;

  // docker pull icr.io/ibm-messaging/mq:9.2.0.0-r1

  // docker run --env LICENSE=accept --env MQ_APP_PASSWORD=passw0rd --env MQ_QMGR_NAME=QM1
  // --publish 1414:1414 --publish 9443:9443 --detach
  // --name QM1 icr.io/ibm-messaging/mq:9.2.0.0-r1

  // docker exec -ti QM1 bash

  @Bean(name = "mqQueueConnectionFactory2")
  public MQQueueConnectionFactory mqQueueConnectionFactory2() throws JMSException {
    MQQueueConnectionFactory queueConnectionFactory = new MQQueueConnectionFactory();
    queueConnectionFactory.setConnectionNameList(listenerProperties.getConnectionName());
    queueConnectionFactory.setQueueManager(listenerProperties.getQueueManager());
    queueConnectionFactory.setTransportType(CommonConstants.WMQ_CM_CLIENT);
    queueConnectionFactory.setChannel(listenerProperties.getChannel());
    queueConnectionFactory.setClientReconnectTimeout(listenerProperties.getReconnectionTimeout());
    queueConnectionFactory.setClientReconnectOptions(CommonConstants.WMQ_CLIENT_RECONNECT_Q_MGR);
    //    queueConnectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, false);
    //    queueConnectionFactory.setStringProperty(WMQConstants.USERID,
    // listenerProperties.getUserName());
    //    queueConnectionFactory.setStringProperty(
    //        WMQConstants.PASSWORD, listenerProperties.getPassword());
    log.info("mqQueueConnectionFactory initialised");
    return queueConnectionFactory;
  }

  @Bean(name = "defaultJmsListenerContainerFactory2")
  public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory2(
      @Qualifier("mqQueueConnectionFactory2") MQQueueConnectionFactory mqQueueConnectionFactory2) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(mqQueueConnectionFactory2);
    //factory.setErrorHandler(listenerErrorHandler);
    factory.setConcurrency("1-4");
    factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
    factory.setSessionTransacted(true);
    factory.setAutoStartup(true);
    factory.setBackOff(new FixedBackOff(60000, 20));
    return factory;
  }
}
