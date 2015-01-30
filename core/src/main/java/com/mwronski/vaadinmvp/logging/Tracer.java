package com.mwronski.vaadinmvp.logging;

import com.google.common.base.Joiner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for logging library to simplify and speed-up logging activities;
 * Tracer is based on multiton pattern what helps to avoid keeping loggers as attribute of classes.
 *
 * @author Michal Wronski
 * @date 05-03-2014
 */
public final class Tracer {

    private static final String LOG4J_PROPERTIES = "META-INF/log4j.properties";
    private static final Map<String, Tracer> INSTANCES = new HashMap<>();
    private final Logger log;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            PropertyConfigurator.configure(Tracer.class.getClassLoader().getResourceAsStream(LOG4J_PROPERTIES));
        } catch (Exception e) {
            System.err.println("Couldn't load log4j properties from: " + LOG4J_PROPERTIES);
            e.printStackTrace();
        }
    }

    /**
     * Convert given collection into simple string representation
     *
     * @param collection to be formatted
     * @param <T> types of elements kept in collection
     * @return non-null string representation
     */
    private <T> String toString(Collection<T> collection) {
        String collectionContext = Joiner.on(',').join(collection).toString();
        return "[" + collectionContext + "]";
    }

    /**
     * Log message and representation of given collection
     *
     * @param msg message to be formatted
     * @param collection that will be placed as single string in message
     */
    public <T> void debug(final String msg, final Collection<T> collection) {
        if (log.isDebugEnabled()) {
            log.debug(String.format(msg, toString(collection)));
        }
    }

    /**
     * Log message and representation of given objects
     *
     * @param msg message to be formatted
     * @param objects arguments of formatting
     */
    public void debug(final String msg, final Object... objects) {
        if (log.isDebugEnabled()) {
            log.debug(String.format(msg, objects));
        }
    }

    /**
     * Log message and representation of given objects
     *
     * @param msg message to be formatted
     * @param t error to be attached
     * @param objects arguments of formatting
     */
    public void debug(final String msg, final Throwable t, Object... objects) {
        if (log.isDebugEnabled()) {
            log.debug(String.format(msg, objects), t);
        }
    }

    /**
     * Log error information
     *
     * @param msg message to be formatted
     * @param objects that will be placed as single string in message
     */
    public void error(final String msg, Object... objects) {
        log.error(String.format(msg, objects));
    }

    public void error(final Throwable t) {
        log.error(t.getMessage(), t);
    }

    /**
     * Log error information
     *
     * @param msg message to be formatted
     * @param t error to be attached
     * @param objects that will be placed as single string in message
     */
    public void error(final String msg, final Throwable t, Object... objects) {
        log.error(String.format(msg, objects), t);
    }

    /**
     * Log warning
     *
     * @param msg message to be formatted
     * @param objects that will be placed as single string in message
     */
    public void warn(final String msg, Object... objects) {
        log.warn(String.format(msg, objects));
    }

    /**
     * Log warning
     *
     * @param msg message to be formatted
     * @param t error to be attached
     * @param objects that will be placed as single string in message
     */
    public void warn(final String msg, final Throwable t, Object... objects) {
        log.warn(String.format(msg, objects), t);
    }

    /**
     * Log message and representation of given collection
     *
     * @param msg message to be formatted
     * @param collection that will be placed as single string in message
     */
    public <T> void info(final String msg, final Collection<T> collection) {
        log.info(String.format(msg, toString(collection)));
    }

    /**
     * Log message and representation of given objects
     *
     * @param msg message to be formatted
     * @param objects arguments of formatting
     */
    public void info(final String msg, final Object... objects) {
        log.info(String.format(msg, objects));
    }

    public static Tracer tracer(final Class<?> clazz) {
        return tracer(clazz.getName());
    }

    public static Tracer tracer(final Object o) {
        return tracer(o.getClass().getName());
    }

    private static Tracer tracer(String name) {
        Tracer instance = INSTANCES.get(name);
        if (instance == null) {
            //lazy
            instance = new Tracer(Logger.getLogger(name));
            INSTANCES.put(name, instance);
        }
        return instance;
    }

    private Tracer(final Logger log) {
        this.log = log;
    }

}
