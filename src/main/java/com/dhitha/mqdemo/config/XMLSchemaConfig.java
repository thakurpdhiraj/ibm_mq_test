package com.dhitha.mqdemo.config;

import java.util.Objects;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;

/**
 * @author Dhiraj
 */
@Configuration
public class XMLSchemaConfig {

  @Bean("xmlSchema")
  public Schema schema() throws SAXException {
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//    schemaFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//    schemaFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
//    schemaFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    return schemaFactory.newSchema(
        new Source[] {
            new StreamSource(
                Objects.requireNonNull(getClass().getClassLoader().getResource("doc.xsd")).getFile())
        });
  }
}
