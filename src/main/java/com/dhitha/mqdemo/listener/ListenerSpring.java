package com.dhitha.mqdemo.listener;

import com.dhitha.mqdemo.config.ListenerClass;
import com.dhitha.mqdemo.processor.MessageProcessor;
import com.ibm.msg.client.jms.JmsConstants;
import javax.jms.JMSException;
import javax.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author Dhiraj
 */
@Component
@RequiredArgsConstructor
@Slf4j
@ListenerClass
public class ListenerSpring {

  private final MessageProcessor messageProcessor;

  @JmsListener(
      destination = "${queueName}",
      containerFactory = "defaultJmsListenerContainerFactory1")
  @SendTo("${replyQueue}")
  public String listener(
      Message message,
      @Header(JmsConstants.JMSX_DELIVERY_COUNT) int deliveryCount,
      @Header(JmsHeaders.MESSAGE_ID) String messageId)
      throws JMSException {
    log.info("Received message {}, delivery {}", messageId, deliveryCount);
    int totalRetries = 3;
    if (deliveryCount == totalRetries) {
      // message.acknowledge();
      log.error("Delivery exceeded {} attempts for message {}", totalRetries, messageId);
      return "Delivery exceeded " + totalRetries + " attempts for message " + messageId;
    } else if (deliveryCount > totalRetries) {
      log.error(
          "Delivery exceeded {} attempts for message {} and the API could not send the reply back. Request message is consumed ",
          totalRetries,
          messageId);
      return null;
    }

    return messageProcessor.process(message, messageId);
  }

  @JmsListener(
      destination = "${queueName}",
      containerFactory = "defaultJmsListenerContainerFactory2")
  @SendTo("${replyQueue}")
  public String listener2(
      Message message,
      @Header(JmsConstants.JMSX_DELIVERY_COUNT) int deliveryCount,
      @Header(JmsHeaders.MESSAGE_ID) String messageId)
      throws JMSException {

    log.info(
        "defaultJmsListenerContainerFactory2: Received message {}, delivery {}",
        messageId,
        deliveryCount);
    int totalRetries = 3;
    if (deliveryCount == totalRetries) {
      // message.acknowledge();
      log.error("Delivery exceeded {} attempts for message {}", totalRetries, messageId);
      return "Delivery exceeded " + totalRetries + " attempts for message " + messageId;
    } else if (deliveryCount > totalRetries) {
      log.error(
          "Delivery exceeded {} attempts for message {} and the API could not send the reply back. Request message is consumed ",
          totalRetries,
          messageId);
      return null;
    }

    return messageProcessor.process(message, messageId);
  }
}
