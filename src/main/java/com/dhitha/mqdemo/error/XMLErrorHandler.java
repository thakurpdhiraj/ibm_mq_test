package com.dhitha.mqdemo.error;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Dhiraj
 */
public class XMLErrorHandler implements org.xml.sax.ErrorHandler {

  private final List<String> exceptions;

  public XMLErrorHandler() {
    this.exceptions = new ArrayList<>();
  }

  public List<String> getExceptions() {
    return List.copyOf(this.exceptions);
  }

  @Override
  public void warning(SAXParseException exception) throws SAXException {
    addErrorMessage(exception);
  }

  @Override
  public void error(SAXParseException exception) throws SAXException {
    addErrorMessage(exception);
  }

  @Override
  public void fatalError(SAXParseException exception) throws SAXException {
    addErrorMessage(exception);
  }

  private void addErrorMessage(SAXParseException exception) {
    exceptions.add(
        "Error: "
            + exception.getMessage()
            + " at line: "
            + exception.getLineNumber()
            + " and column: "
            + exception.getColumnNumber());
  }
}
