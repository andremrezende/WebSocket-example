package br.com.websocket;

import java.io.IOException;
import java.util.Date;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

/**
 * https://github.com/TakahikoKawasaki/nv-websocket-client
 * @author Rezende
 *
 */
public class EchoClient {
	/**
	 * The echo server on websocket.org.
	 */
	private static final String SERVER = "ws://localhost:8443/cms/app/echo";

	/**
	 * The timeout value in milliseconds for socket connection.
	 */
	private static final int TIMEOUT = 5000;

	/**
	 * The entry point of this command line application.
	 */
	public static void main(String[] args) throws Exception {
		// Connect to the echo server.

		EchoClient echoClient = new EchoClient();
		WebSocket ws = null;
		try {
			ws = echoClient.connect();
			ws.sendText(new Date() + " teste->1");
		} finally {
			// Close the WebSocket.
			ws.disconnect();
		}
	}

	/**
	 * Connect to the server.
	 */
	public WebSocket connect() throws IOException, WebSocketException {
		return new WebSocketFactory().setConnectionTimeout(TIMEOUT).createSocket(SERVER)
				.addListener(new WebSocketAdapter() {
					// A text message arrived from the server.
					public void onTextMessage(WebSocket websocket, String message) {
						System.out.println(message);
					}
				}).addExtension(WebSocketExtension.PERMESSAGE_DEFLATE).connect();
	}
}