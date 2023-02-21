package com.dhitha.mqdemo.processor;

import com.dhitha.mqdemo.error.XMLErrorHandler;
import com.dhitha.mqdemo.exception.ApiException;
import com.dhitha.mqdemo.model.Shiporder;
import com.ibm.jms.JMSTextMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.xml.sax.SAXException;

/**
 * @author Dhiraj
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProcessor {

  private final Schema xmlSchema;

  public String process(Message message, String messageId) {
    try {
      if (message instanceof JMSTextMessage
          && !ObjectUtils.isEmpty(message)
          && !ObjectUtils.isEmpty(((JMSTextMessage) message).getText())) {
        String text = ((JMSTextMessage) message).getText();
        String validationResult = validateRequest(text);
        if (validationResult != null) {
          return validationResult;
        }
        JAXBContext requestContext = JAXBContext.newInstance(Shiporder.class);
        Unmarshaller requestUnmarshal = requestContext.createUnmarshaller();
        requestUnmarshal.setSchema(xmlSchema);
        log.info("Attempting to unmarshal request, Message Id {}", messageId);
        Shiporder request = (Shiporder) requestUnmarshal.unmarshal(new StringReader(text));
        log.info("Request unmarshalled {}", Integer.parseInt(request.getOrderid()));
        return request.toString();
      } else {
        throw new ApiException();
      }
    } catch (JMSException | JAXBException e) {
      log.error("Exception : {}", messageId, e);
      throw new ApiException();
    }
  }

  public String process(String text, String messageId) {
    try {
      validateRequest(text);
      JAXBContext requestContext = JAXBContext.newInstance(Shiporder.class);
      Unmarshaller requestUnmarshal = requestContext.createUnmarshaller();
      requestUnmarshal.setSchema(xmlSchema);
      log.info("Attempting to unmarshal request, Message Id {}, Request {}", messageId, text);
      Shiporder request = (Shiporder) requestUnmarshal.unmarshal(new StringReader(text));
      log.info("Request unmarshalled {}", request);
      return request.toString();
    } catch (JAXBException e) {
      throw new ApiException();
    }
  }

  private String validateRequest(String text) {
    XMLErrorHandler XMLErrorHandler = new XMLErrorHandler();
    try {

      Validator validator = xmlSchema.newValidator();
      validator.setErrorHandler(XMLErrorHandler);
      validator.validate(
          new StreamSource(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));

    } catch (IOException | SAXException e) {
      log.error("validation failure", e);
    }

    List<String> validationErrors = XMLErrorHandler.getExceptions();
    if (!validationErrors.isEmpty()) {
      return validationErrors.toString();
    }
    return null;
  }
}
