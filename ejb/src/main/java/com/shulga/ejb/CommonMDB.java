package com.shulga.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "shulga/jms/queue/demo_queue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class CommonMDB implements MessageListener {
    @PostConstruct
    public void setUp() {
        LOGGER.info("POSTCONSTRACT");
    }

    private final static Logger LOGGER = Logger.getLogger(CommonMDB.class
            .toString());

    @Override
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        LOGGER.info("Received delevered to MDB");
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                LOGGER.info("Received Message: " + msg.getText());
            } else {
                LOGGER.warning("Message of wrong type: "
                        + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}
