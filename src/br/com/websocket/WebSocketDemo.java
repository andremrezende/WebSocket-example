package br.com.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint gives the relative name for the end point This will be
 *                 accessed via ws://localhost:8080/webSocket-example/echo Where "localhost"
 *                 is the address of the host, "webSocket-example" is the name of the
 *                 package and "echo" is the address to access this class from
 *                 the server
 */
@ServerEndpoint("/echo")
public class WebSocketDemo {
	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(final Session session) {
		// add the new session to the set
		sessions.add(session);
	}

	@OnMessage
	public String echo(Session currentSession, String message) {
		synchronized (sessions) {
			for (Session session : sessions) {
				if (session.isOpen() && !session.equals(currentSession)) {
					try {
						// send the article summary to all the connected clients
						session.getBasicRemote().sendText(message);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return message;
	}

	@OnError
	public void onError(Session session, Throwable thr) {
		thr.printStackTrace();
	}

	@OnClose
	public void onClose(final Session session) {
		// remove the session from the set
		sessions.remove(session);
	}

}
