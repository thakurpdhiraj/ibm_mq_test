package com.dhitha.mqdemo.listener;

import com.dhitha.mqdemo.exception.ApiException;
import com.dhitha.mqdemo.processor.MessageProcessor;
import com.ibm.jms.JMSTextMessage;
import com.ibm.msg.client.jms.JmsConstants;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author Dhiraj
 */
//@Component
@Slf4j
@RequiredArgsConstructor
public class Listener implements SessionAwareMessageListener<Message> {

  private final MessageProcessor messageProcessor;

  @Override
  public void onMessage(Message message, Session session) throws JMSException {
    try {
      log.info(
          "\nMessage received: {}, attempt: {}",
          message.getJMSMessageID(),
          message.getIntProperty(JmsConstants.JMSX_DELIVERY_COUNT));
      if (message instanceof JMSTextMessage
          && !ObjectUtils.isEmpty(message)
          && !ObjectUtils.isEmpty(((JMSTextMessage) message).getText())) {
        String text = ((JMSTextMessage) message).getText();

        String messageId = message.getJMSMessageID();
        log.info("Message: {}, messageId: {}", text, messageId);
        String result = messageProcessor.process(text, messageId);
        sendMessage(message, session, result);
      } else {
        throw new ApiException();
      }
    } catch (ApiException ex) {
      int deliveryCount = message.getIntProperty(JmsConstants.JMSX_DELIVERY_COUNT);
      int totalRetries = 3;
      if (deliveryCount >= totalRetries) {
        log.error(
            "Delivery exceeded {} attempts for message {}",
            totalRetries,
            message.getJMSMessageID());
        session.commit();
      } else {
        log.error(
            "Error in message {}, delivery count {}, attempting to retry",
            message.getJMSMessageID(),
            deliveryCount);
        session.rollback();
      }
    }
  }

  private void sendMessage(Message message, Session session, String result) {}
}
