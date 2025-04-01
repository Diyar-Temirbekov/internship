package mn.partners.runtime.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceConfig {

    private static final String NAMESPACE_URI = "http://partners.mn/runtime/soap";
    private static final String PORT_TYPE_NAME = "RoomPricesPort";
    private static final String SCHEMA_PATH = "schemas/room-prices.xsd";

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "roomPrices")
    public DefaultWsdl11Definition roomPricesWsdl(XsdSchema roomPricesSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName(PORT_TYPE_NAME);
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace(NAMESPACE_URI);
        wsdl.setSchema(roomPricesSchema);

        return wsdl;
    }

    @Bean
    public XsdSchema roomPricesSchema() {
        return new SimpleXsdSchema(new ClassPathResource(SCHEMA_PATH));
    }
}