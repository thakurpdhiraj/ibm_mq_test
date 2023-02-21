package com.dhitha.mqdemo.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.listener.adapter.ReplyFailureException;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * @author Dhiraj
 */
@Component
@Slf4j
public class ListenerErrorHandler implements ErrorHandler {

  @Override
  public void handleError(Throwable t) {
    if (t instanceof ReplyFailureException) {
      ReplyFailureException ex = (ReplyFailureException) t;
      log.error("Failed to send reply", ex);
    } else {
      log.error("Exception in ErrorHandler, throwing [{}]", t.getMessage());
    }
  }
}
