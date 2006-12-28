/*
 * CommandProcessor.java - created Sep 1, 2006 12:53:48 AM
 * $Id$
 */
package com.ourbabywolf.apples2apples.jabber;

/**
 * Generic interface for dealing with commands received through
 * jabber.
 * 
 * @author joe@ourbabywolf.com
 *
 */
public interface CommandProcessor {

    /**
     * Processes the given command from the given user.
     * 
     * @param jabberId
     * @param command
     * @param client
     */
    public void process(String jabberId, String command, HostingClient client);
    
}
