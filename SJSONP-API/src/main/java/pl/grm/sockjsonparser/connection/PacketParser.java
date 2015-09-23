package pl.grm.sockjsonparser.connection;

import java.io.*;
import java.net.Socket;

class PacketParser {

	public static void sendPacket(String str, Socket socket) throws IOException {
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.write(str.getBytes());
	}

	public static String receivePacket(Socket socket) throws IOException {
		DataInputStream in = new DataInputStream(socket.getInputStream());
		String str = "";
		byte[] msgB = new byte[1000];
		byte[] lenB = new byte[4];
		boolean isFinished = false;
		int read = 0;
		for (int i = 0; i < 4; i++) {
			lenB[i] = in.readByte();
		}
		int toRead = convertBytesToInt(lenB) - 4;
		while (!isFinished) {
			read = in.read(msgB);
			str += new String(msgB, 0, read);
			System.out.println(str.length() + "|" + toRead + " " + str);
			if (str.length() == toRead) {
				isFinished = true;
			}
		}
		return str;
	}

	private static int convertBytesToInt(byte[] lenBytes) {
		return ((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) | ((lenBytes[1] & 0xff) << 8)
				| (lenBytes[0] & 0xff);
	}
}
