package com.mwronski.vaadinmvp.lang;

import com.mwronski.vaadinmvp.exceptions.CoreRuntimeException;

import static com.mwronski.vaadinmvp.logging.Tracer.tracer;

/**
 * Factory for language messages
 * <p/>
 * TODO add user's translation
 * TODO add support for interface budles and proper code generation.
 * Note: jlibs (http://code.google.com/p/jlibs/wiki/Internationalization)
 * and gwt-i18n-server (https://github.com/lightoze/gwt-i18n-server, http://www.gwtproject.org/doc/latest/DevGuideI18n.html)
 * seem to have problems with Java 1.7 and diamonds.
 *
 * @author Michal Wronski
 * @date 05-03-2014
 */
public final class Lang {

    /**
     * Get language support for core system
     *
     * @param messageType set of messages to be taken
     * @param <M> type of messages
     * @return translator for chosen messages
     */
    public static <M> M system(Class<M> messageType) {
        try {
            return messageType.newInstance();
        } catch (Exception e) {
            throw new CoreRuntimeException("Couldn't create bundle for: " + messageType, e);
        }
    }

    /**
     * Get language support for user
     *
     * @param messageType set of messages to be taken
     * @param <M> type of messages
     * @return translator for chosen messages
     */
    public static <M> M user(Class<M> messageType) {
        tracer(Lang.class).warn("User's language not supported yet - returning default language for: %s", messageType.getName());
        try {
            return messageType.newInstance();
        } catch (Exception e) {
            throw new CoreRuntimeException("Couldn't create bundle for: " + messageType, e);
        }
    }

    private Lang() {
        //no instances
    }
}
