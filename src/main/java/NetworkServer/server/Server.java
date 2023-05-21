package NetworkServer.server;

import NetworkServer.server.FileIO;
import java.io.*;
import java.net.*;

public class Server {

    static FileIO files = new FileIO();

    public static void main(String[] args) {
        try {
            int port = 12345;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("서버가 시작되었습니다.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                int portNum = clientSocket.getPort();
                System.out.println("클라이언트가 접속하였습니다. " + portNum);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                // 클라이언트와의 통신을 위한 작업 수행
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    // 수신한 데이터를 문자열로 변환
                    String receivedMessage = new String(buffer, 0, bytesRead);
                    System.out.println("클라이언트로부터 메시지를 수신했습니다: " + receivedMessage);

                    char startchar = receivedMessage.charAt(0); // 타입 확인할 문자
                    String MessageToFile = clientSocket.getPort() + " " + receivedMessage.substring(2); // 문자를 제외한 문자열
                    if(startchar == 'A')
                    {
                        files.SaleFileWrite(MessageToFile, true);
                    }
                    else if(startchar == 'B')
                    {
                        files.SoldOutWrite(MessageToFile, true);
                    }
                    // 수신 받은 데이터를 포트 번호와 함께 파일로 입력


                    // 받은 데이터를 클라이언트에게 다시 전송
                    String responseMessage = "서버에서 응답: " + MessageToFile;
                    byte[] responseBytes = MessageToFile.getBytes();
                    outputStream.write(responseBytes);
                    outputStream.flush();
                    System.out.println("클라이언트로 메시지를 전송했습니다: " + MessageToFile);
                }

                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
