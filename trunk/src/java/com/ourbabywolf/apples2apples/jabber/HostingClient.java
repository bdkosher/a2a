/*
 * HostingClient.java - created Aug 19, 2006 10:32:31 AM
 * $Id$
 */
package com.ourbabywolf.apples2apples.jabber;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.GoogleTalkConnection;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.OrFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * This class is the bot that hosts the game. It makes a connection to a jabber
 * server, processes messages received and controls the game.
 * 
 * @author joe@ourbabywolf.com
 * 
 */
public class HostingClient {

    private static final Logger log = Logger.getLogger(HostingClient.class
            .toString());

    /** The connection this client holds to the Jabber server. */
    protected XMPPConnection conn = null;

    /** A mapping of Jabber ids to their corresponding Chats. */
    private final Map<String, Chat> chatMap;

    /** The command processor. */
    private final CommandProcessor processor;

    /**
     * Creates a new HostingClient.
     */
    public HostingClient(final CommandProcessor processor) {
        if (processor == null) {
            throw new NullPointerException(
                    "CommandProcessor argument cannot be null.");
        }
        this.chatMap = new HashMap<String, Chat>();
        this.processor = processor;
    }

    /**
     * Causes this client to connect to the GoogleTalk server with the given
     * parameters.
     * 
     * @param user
     * @param pass
     */
    public void connect(String user, String pass) {
        connect(null, user, pass);
    }

    /**
     * Causes the client to connect to the jabber server with the given
     * parameters.
     * 
     * @param hostUrl
     * @param user
     * @param pass
     */
    public void connect(String hostUrl, String user, String pass) {
        connect(hostUrl, -1, user, pass);
    }

    /**
     * Causes the client to connect to the jabber server with the given
     * parameters. If a connection has been opened already with this client, the
     * current connection will be disconnected first.
     * 
     * @param hostUrl
     * @param port
     * @param user
     * @param pass
     */
    public void connect(String hostUrl, int port, String user, String pass) {
        if (conn != null) {
            disconnect();
        }
        try {
            conn = hostUrl == null ? new GoogleTalkConnection()
                    : port < 0 ? new XMPPConnection(hostUrl)
                            : new XMPPConnection(hostUrl, port);
            conn.login(user, pass);
        } catch (XMPPException e) {
            throw new RuntimeException("Unable to create connection/login.", e);
        }
        conn.addConnectionListener(new ConnectionListener() {
            public void connectionClosed() {
                log.info("Connection has been closed.");
            }

            public void connectionClosedOnError(Exception e) {
                log.severe("Connection closed erroneously: " + e.getMessage());
            }
        });
        final HostingClient client = this;
        conn.addPacketListener(new PacketListener() {
            public void processPacket(Packet packet) {
                Message message = (Message)packet;
                log.info("BODY: " + message.getBody());
                log.info("Full XML: " + message.toXML());
                processor.process(extractJabberId(message
                        .getFrom()), message.getBody(), client);
            }
        }, new OrFilter(new MessageTypeFilter(Message.Type.CHAT),
                new MessageTypeFilter(Message.Type.NORMAL)));
    }

    /**
     * Extracts the jabber id from the "from" string of the message.
     */
    protected String extractJabberId(String from) {
        return from.substring(0, from.indexOf('/'));
    }

    /**
     * Closes the connection this client holds.
     */
    public void disconnect() {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Sends a message to the player with the given jabberId.
     * 
     * @param jabberId
     * @param msg
     * @throws Exception
     */
    public void sendMessage(String jabberId, String msg) {
        if (jabberId == null || "".equals(jabberId.trim())) {
            throw new IllegalArgumentException("Invalid jabber id: " + jabberId);
        }
        Chat chat = getChat(jabberId);
        try {
            chat.sendMessage(msg);
        } catch (XMPPException e) {
            throw new RuntimeException("Unable to send message to " + jabberId,
                    e);
        }
    }

    /**
     * Retrieves a Chat object for the jabber id. Currently, this method caches
     * the chats. I'm not sure how much that buys you...maybe not much at all.
     * 
     * @param jabberId
     * @return
     */
    private Chat getChat(String jabberId) {
        Chat chat = null;
        if (!chatMap.keySet().contains(jabberId)) {
            chat = conn.createChat(jabberId);
            chatMap.put(jabberId, chat);
        } else {
            chat = chatMap.get(jabberId);
        }
        return chat;
    }
}
