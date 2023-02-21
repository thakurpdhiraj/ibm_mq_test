package com.dhitha.mqdemo.config;

import com.dhitha.mqdemo.listener.Listener;
import com.dhitha.mqdemo.properties.ListenerProperties;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.common.CommonConstants;
import java.util.Objects;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.util.backoff.FixedBackOff;
import org.xml.sax.SAXException;

/**
 * @author Dhiraj
 */
//@Configuration
@RequiredArgsConstructor
@Slf4j
public class MQConfig {
//
//  private final ListenerProperties listenerProperties;
//
//  private final Listener listener;
//
//  @Bean
//  public MQQueueConnectionFactory mqQueueConnectionFactory() throws JMSException {
//    MQQueueConnectionFactory queueConnectionFactory = new MQQueueConnectionFactory();
//    queueConnectionFactory.setConnectionNameList(listenerProperties.getConnectionName());
//    queueConnectionFactory.setQueueManager(listenerProperties.getQueueManager());
//    queueConnectionFactory.setTransportType(CommonConstants.WMQ_CM_CLIENT);
//    queueConnectionFactory.setChannel(listenerProperties.getChannel());
//    queueConnectionFactory.setClientReconnectTimeout(listenerProperties.getReconnectionTimeout());
//    queueConnectionFactory.setClientReconnectOptions(CommonConstants.WMQ_CLIENT_RECONNECT_Q_MGR);
//    //    queueConnectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, false);
//    //    queueConnectionFactory.setStringProperty(WMQConstants.USERID,
//    // listenerProperties.getUserName());
//    //    queueConnectionFactory.setStringProperty(WMQConstants.PASSWORD,
//    // listenerProperties.getPassword());
//    log.info("mqQueueConnectionFactory initialised");
//    return queueConnectionFactory;
//  }
//
//  @Bean
//  public CachingConnectionFactory cachingConnectionFactory(
//      MQQueueConnectionFactory mqQueueConnectionFactory) {
//    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//    cachingConnectionFactory.setTargetConnectionFactory(mqQueueConnectionFactory);
//    cachingConnectionFactory.resetConnection();
//    cachingConnectionFactory.setSessionCacheSize(2);
//    cachingConnectionFactory.setCacheConsumers(false);
//    cachingConnectionFactory.setCacheProducers(false);
//    cachingConnectionFactory.setReconnectOnException(true);
//    log.info("cachingConnectionFactory initialised");
//    return cachingConnectionFactory;
//  }
//
//  @Bean
//  public MessageListenerContainer messageListenerContainer(
//      MQQueueConnectionFactory mqQueueConnectionFactory) {
//    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
//    container.setConnectionFactory(mqQueueConnectionFactory);
//    container.setDestinationName(listenerProperties.getQueueName());
//    container.setMessageListener(listener);
//    container.setConcurrency("1-4");
//    container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
//    container.setBackOff(new FixedBackOff(600000, 5));
//    container.setSessionTransacted(true);
//    log.info("messageListenerContainer initialised");
//    //    try {
//    //      Connection connection =
//    //          Objects.requireNonNull(container.getConnectionFactory()).createConnection();
//    //      log.info("Connection made {}", mqQueueConnectionFactory.getHostName());
//    //      connection.close();
//    //      log.info("Connection closed");
//    //
//    //    } catch (Exception e) {
//    //      log.error("Error making connection", e);
//    //    }
//    return container;
//  }
}
