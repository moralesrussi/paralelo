package eParallel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

public class ConversationListener extends Thread {
	private Connection conn;
	private PGConnection pgConn;

	public ConversationListener(Connection conn) throws SQLException {
		this.conn = conn;
		this.pgConn = (PGConnection) conn;
		Statement listenStatement = conn.createStatement();
		listenStatement.execute("LISTEN mymessage");
		listenStatement.close();
	}

	@Override
	public void run() {
		while (true) {
			try {
				// issue a dummy query to contact the backend
				// and receive any pending notifications.
				System.out.println("Notificador iniciado........ ");
				Statement selectStatement = conn.createStatement();
				ResultSet rs = selectStatement.executeQuery("SELECT 1");
				rs.close();
				selectStatement.close();

				PGNotification notifications[] = pgConn.getNotifications();

				if (notifications != null) {
					for (PGNotification pgNotification : notifications) {
						System.out.println("Got notification: " + pgNotification.getName() + " with payload: "
								+ pgNotification.getParameter());
					}
				}

				// wait a while before checking again
				Thread.sleep(500);
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
}