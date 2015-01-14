package com.shulga.common;

import com.shulga.ejb.CommonMDB;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.logging.Logger;

public class Notifier {
    private final static Logger LOGGER = Logger.getLogger(CommonMDB.class
            .toString());

    @Resource(mappedName = "shulga/jms/queue/factory")
    private ConnectionFactory factory;
    @Resource(mappedName = "shulga/jms/queue/demo_queue")
    private Queue dest;
    Connection connection;


    public void sendMessage(String text) {
        Session session;
        try {
            connection = factory.createConnection();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(dest);
            connection.start();
            Message message = session.createTextMessage();
            message.setStringProperty("name", text);
            producer.send(dest, message);
            producer.close();
            LOGGER.info("Jms message sent");
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                    connection.close();
                    LOGGER.info("Connection closed");
                } catch (JMSException e) {
                    LOGGER.info("Coonection closing error");
                }
        }
    }
}
