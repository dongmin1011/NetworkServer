package NetworkServer.server;

import NetworkServer.drinkList;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Server {

    static FileIO files = new FileIO();


    static ArrayList<Log_collect_port> logArray = new ArrayList<>();

    static drinkList[] drinks= new drinkList[4];



    static HttpServerManager httpServerManager = null;
    public static void main(String[] args) {


        try {
            int port = 12345;
//            int webport = 2846;
            ServerSocket serverSocket = new ServerSocket(port);
//            ServerSocket WebSocket = new ServerSocket(port);
            System.out.println("서버가 시작되었습니다.");

            httpServerManager = new HttpServerManager("localhost", 3000);
            httpServerManager.start();

//            drinks[0] = new drinkList(0,null, null, null);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 접속하였습니다. ");




                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally {
//            httpServerManager.stop(0);
//        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        private Integer UniqueNum;


        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                String request = reader.readLine();
////                System.out.println("request. = " + request.getClass().getName());
//                if (request.contains("HTTP")) {
//                    System.out.println("request = " + request);
//
//                    InputStream inputStream = clientSocket.getInputStream();
//                    OutputStream outputStream = clientSocket.getOutputStream();
//
//                    // 클라이언트와의 통신을 위한 작업 수행
//                    byte[] buffer = new byte[1024];
//                    int bytesRead;
//
//                    bytesRead = inputStream.read(buffer);
//                    String initData = new String(buffer, 0, bytesRead);
//                    System.out.println("클라이언트로부터 메시지를 수신했습니다: " + initData);
//
//                    // JSON 데이터 출력
//                    System.out.println("전달받은 JSON 데이터: " + initData);
//
//                } else {

                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

// 클라이언트와의 통신을 위한 작업 수행
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                String initData = new String(buffer, 0, bytesRead);
                System.out.println("클라이언트로부터 메시지를 수신했습니다: " + initData);


                    int number = Character.getNumericValue(initData.charAt(0));
                    String drinkData = initData.substring(1);

                    String[] words = drinkData.split(" ");

                    String [] names = new String [5];
                    int [] prices = new int [5];
                    int [] stocks = new int [5];

                    for (int i = 0; i < words.length; i += 3) {
                        names[i / 3] = words[i];
                        prices[i / 3] = Integer.parseInt(words[i + 1]);
                        stocks[i / 3] = Integer.parseInt(words[i + 2]);
                    }
                    //잘 입력되었는지 테스트 코드
                    for (int i = 0; i < 5; i++) {
                        String name = names[i];
                        int price = prices[i];
                        System.out.println("Name: " + name + ", Price: " + price);
                        // 각 이름과 가격에 대한 추가 처리 로직 구현
                    }



                    drinks[number-1] = new drinkList(number, names, prices, stocks);

                    httpServerManager.setDrink(drinks);


                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        // 수신한 데이터를 문자열로 변환
                        String receivedMessage = new String(buffer, 0, bytesRead);
                        System.out.println("클라이언트로부터 메시지를 수신했습니다: " + receivedMessage);

                        char startchar = receivedMessage.charAt(0); // 타입 확인할 문자
                        String MessageToFile = receivedMessage.substring(2); // 문자를 제외한 문자열

                        if(startchar == 'A')
                        {
                            UniqueNum = Integer.getInteger(MessageToFile);
                        }
                        else if(startchar == 'B')
                        {
                            StringTokenizer st = new StringTokenizer(MessageToFile, " 원 일 년 월"); // 띄어쓰기와 원을 구분자로 문자열을 나눔
                            int port = Integer.parseInt(st.nextToken());
                            // 나눈 첫 부위는 포트이므로 문자열을 숫자로 변환하여 년도에 넣음
                            int year = Integer.parseInt(st.nextToken());
                            // 나눈 첫 부위는 년도이므로 문자열을 숫자로 변환하여 년도에 넣음
                            int month = Integer.parseInt(st.nextToken());
                            // 나눈 두번째 부위는 월이므로 문자열을 정수로 변환하여 달 숫자에 넣음
                            int day = Integer.parseInt(st.nextToken());
                            // 나눈 세번째 부위는 일이므로 문자열을 정수로 변환하여 달 숫자에 넣음
                            String name = st.nextToken();
                            // 나눈 네번째 부위는 이름이므로 문자열 넣음
                            int price = Integer.parseInt(st.nextToken());
                            // 나눈 다섯번째 부위는 가격이므로 문자열을 정수로 변환하여 달 숫자에 넣음

                            for(int i = 0; i < drinks.length; i++)
                            {
                                if(drinks[i] != null && drinks[i].getUniqueNum() == port)
                                {
                                    System.out.println(drinks[i].getDrinksName().length);
                                    for(int j = 0; j < drinks[i].getDrinksName().length; j++)
                                    {
                                        System.out.println(drinks[i].getDrinksNameNotArray(j).equals(name) + " " + drinks[i].getDrinksPriceNotArray(j));
                                        if(drinks[i].getDrinksNameNotArray(j).equals(name) && drinks[i].getDrinksPriceNotArray(j) == price)
                                        {
                                            System.out.println("재고: " + (drinks[i].getDrinksStockNotArray(j) - 1));
                                            drinks[i].setDrinkStockNotArray(j,drinks[i].getDrinksStockNotArray(j) - 1);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }

                            files.SaleFileWrite(MessageToFile, true);
                        }
                        else if(startchar == 'C')
                        {
                            files.SoldOutWrite(MessageToFile, true);
                        }
                         else if(startchar == 'D'){
                            StringTokenizer st = new StringTokenizer(MessageToFile, " "); // 띄어쓰기를구분자로 문자열을 나눔
                            int port = Integer.parseInt(st.nextToken());
                            // 나눈 첫 부위는 포트이므로 문자열을 숫자로 변환하여 년도에 넣음
                             for(int i = 0; i < drinks.length; i++) {
                                 if(drinks[i] != null && drinks[i].getUniqueNum() == port) {
                                     String drinkListString = drinks[i].getUniqueNum() + " ";
                                     for (int j = 0; j < 5; j++) {
                                         drinkListString += drinks[i].getDrinksNameNotArray(j) + " ";
                                         drinkListString += drinks[i].getDrinksPriceNotArray(j) + " ";
                                         drinkListString += drinks[i].getDrinksStockNotArray(j) + " ";
                                     } // 서버 상의 데이터와 비교
                                     if (drinkListString.equals(MessageToFile)) {
                                         MessageToFile = "Yes";
                                         break;
                                     } else {
                                         MessageToFile = "No";
                                         break;
                                     }
                                 }
                             }
                        } else if (startchar == 'E') {
                            StringTokenizer st = new StringTokenizer(MessageToFile, " ");
                            int port = Integer.parseInt(st.nextToken());
                            int index = Integer.parseInt(st.nextToken());
                            String name = st.nextToken();
                            int price = Integer.parseInt(st.nextToken());
                            int stock = Integer.parseInt(st.nextToken()); // 정보를 담음

                            for(int i = 0; i < drinks.length; i++)
                            {
                                if(drinks[i] != null && drinks[i].getUniqueNum() == port)
                                {
                                    drinks[i].setDrinksName(index, name);
                                    drinks[i].setDrinkPrice(index, price);
                                    drinks[i].setDrinkStockNotArray(index, stock);
                                }
                            }

                        }
                        // 수신 받은 데이터를 포트 번호와 함께 파일로 입력

                        httpServerManager.updateList();

                        String[] drinksName = drinks[number - 1].getDrinksName();
                        for(int i=0; i<drinksName.length; i++){
                            System.out.println("drinksName = " + drinksName[i]);
                        }


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
