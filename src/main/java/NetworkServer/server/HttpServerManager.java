package NetworkServer.server;

import NetworkServer.drinkList;
import NetworkServer.server.FileIO;
import NetworkServer.server.Log_collect_port;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HttpServerManager {
        private final String HOSTNAME = "0.0.0.0";
        private final int BACKLOG = 0;
        private HttpServer server = null;
        static FileIO files = new FileIO();
        static ArrayList<Log_collect_port> logArray = new ArrayList<>();
        static ArrayList<Log_collect_port_without_drink> without_drinks = new ArrayList<>();
        drinkList[] drinks = new drinkList[4];
        List<List<String> > drinkMoneyList;
        List<List<String> > drinkDateList;

        public HttpServerManager(String host, int port) throws IOException {
                createServer(host, port);
//                for(int i=0; i<5; i++){
//                        drinkName[i] = "음료"+i;
//                        drinkPrice[i] = i;
//                }
        }

        private void createServer(String host, int port) throws IOException {
                this.server = HttpServer.create(new InetSocketAddress(host, port), BACKLOG);
                server.createContext("/", new RootHandler());
                server.createContext("/sales/1", new SalesHandler(0));
                server.createContext("/sales/2", new SalesHandler(1));
                server.createContext("/sales/3", new SalesHandler(2));
                server.createContext("/sales/4", new SalesHandler(3));
                server.createContext("/changePrice", new ChangePrice());
                server.createContext("/changeName", new ChangeName());
                updateList();


        }

        public void start() {
                server.start();
        }

        public void stop(int delay) {
                server.stop(delay);
        }

//        public static void main(String[] args) {
//                HttpServerManager httpServerManager = null;
//
//                try {
//
////                        httpServerManager = new HttpServerManager("localhost", 3000);
////                        httpServerManager.start();
//
//
//                } catch (IOException e) {
//                        e.printStackTrace();
//                        throw new RuntimeException(e);
//                } finally {
//                        httpServerManager.stop(0);
//                }
//        }
        public void setDrink(drinkList[] drink){
//                drinkName = names;
//                drinkPrice = prices;
//                drinkStock = stocks;

//                for (int i = 0; i < 5; i++) {
//                        String name = drinkName[i];
//                        int price = drinkPrice[i];
//
//                        System.out.println("Name: " + name + ", Price: " + price);
//                        // 각 이름과 가격에 대한 추가 처리 로직 구현
//                }
                drinks = drink;

//                System.out.println("generateDrinkNameArray() = " + generateDrinkNameArray(0));
        }

        public void updateList(){
                files.get_Drink_info(logArray);
                drinkMoneyList = files.get_Drink_month_day_money(logArray);
                System.out.println("drinkMoneyList = " + drinkMoneyList);


                files.get_withoutDrink_info(without_drinks);
                drinkDateList = files.get_Drink_month_day_money_without_drink(without_drinks);
        }



        private class RootHandler implements HttpHandler {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                        StringBuilder response = new StringBuilder();
                        response.append("<!DOCTYPE html>");
                        response.append("<html>");
                        response.append("   <head>");
                        response.append("       <meta charset=\"UTF-8\">");
                        response.append("       <meta name=\"author\" content=\"Dochi\">");
                        response.append("       <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                        response.append("       <title>자판기</title>");
                        response.append("   </head>");
                        response.append("   <body>");
                        response.append("       <h1>자판기 페이지</h1>");
                        response.append("       <button onclick=\"buttonClicked('/sales/1')\">자판기 1</button><br>");
                        response.append("       <button onclick=\"buttonClicked('/sales/2')\">자판기 2</button><br>");
                        response.append("       <button onclick=\"buttonClicked('/sales/3')\">자판기 3</button><br>");
                        response.append("       <button onclick=\"buttonClicked('/sales/4')\">자판기 4</button><br>");
                        response.append("       <script>");
                        response.append("           function buttonClicked(url) {");
                        response.append("               window.location.href = url;");
                        response.append("           }");
                        response.append("       </script>");
                        response.append("   </body>");
                        response.append("</html>");

                        sendResponse(exchange, response.toString(), "text/html;charset=UTF-8");
                }
        }
        private class ChangePrice implements HttpHandler {
                int index, price;
                int drinkIndex;
                @Override
                public void handle(HttpExchange exchange) throws IOException {

                        InputStream requestBodyStream = exchange.getRequestBody();
                        BufferedReader requestBodyReader = new BufferedReader(new InputStreamReader(requestBodyStream));
                        StringBuilder requestBodyBuilder = new StringBuilder();
                        String line;
                        while ((line = requestBodyReader.readLine()) != null) {
                                requestBodyBuilder.append(line);
                        }
                        String requestBody = requestBodyBuilder.toString();
//                        System.out.println("requestBody[] = " + json.requestBody);

                        try {
                                JSONObject json = new JSONObject(requestBody);
                                index = json.getInt("index");
                                price = json.getInt("newPrice");
                                drinkIndex = json.getInt("machineIndex");

                        } catch (JSONException e) {
                                throw new RuntimeException(e);
                        }
                        System.out.println("drinkIndex = " + drinkIndex);
                        System.out.println("index = " + index);
                        System.out.println("price = " + price);

                        drinks[drinkIndex].setDrinkPrice(index, price);

                        int[] drinkPrice = drinks[drinkIndex].getDrinkPrice();
                        System.out.println("drinkPrice[index] = " + drinkPrice[index]);

                        // 요청 처리 로직에 requestBody 활용
                        // ...

                        // 응답 보내기
                        String response = "가격수정완료";
                        exchange.sendResponseHeaders(200, response.getBytes().length);
                        exchange.getResponseBody().write(response.getBytes());
                        exchange.getResponseBody().close();
                }
        }
        private class ChangeName implements HttpHandler {
                int index;
                String name;
                int drinkIndex;
                @Override
                public void handle(HttpExchange exchange) throws IOException {

                        InputStream requestBodyStream = exchange.getRequestBody();
                        BufferedReader requestBodyReader = new BufferedReader(new InputStreamReader(requestBodyStream));
                        StringBuilder requestBodyBuilder = new StringBuilder();
                        String line;
                        while ((line = requestBodyReader.readLine()) != null) {
                                requestBodyBuilder.append(line);
                        }
                        String requestBody = requestBodyBuilder.toString();
//                        System.out.println("requestBody[] = " + json.requestBody);

                        try {
                                JSONObject json = new JSONObject(requestBody);
                                index = json.getInt("index");
                                name = json.getString("newName");
                                drinkIndex = json.getInt("machineIndex");

                        } catch (JSONException e) {
                                throw new RuntimeException(e);
                        }
                        System.out.println("drinkIndex = " + drinkIndex);
                        System.out.println("index = " + index);
                        System.out.println("price = " + name);

                        drinks[drinkIndex].setDrinksName(index, name);

                        String [] drinkName = drinks[drinkIndex].getDrinksName();
                        System.out.println("drinkName[index] = " + drinkName[index]);

                        // 요청 처리 로직에 requestBody 활용
                        // ...

                        // 응답 보내기
                        String response = "가격수정완료";
                        exchange.sendResponseHeaders(200, response.getBytes().length);
                        exchange.getResponseBody().write(response.getBytes());
                        exchange.getResponseBody().close();
                }
        }


        private class SalesHandler implements HttpHandler {
                private int drinkIndex;


                public SalesHandler(int drinkIndex) {
                        this.drinkIndex = drinkIndex;
                }





                @Override
                public void handle(HttpExchange exchange) throws IOException {
                        String path = exchange.getRequestURI().getPath();
                        String buttonNumber = path.substring(path.lastIndexOf('/') + 1);

                        StringBuilder response = new StringBuilder();
                        response.append("<!DOCTYPE html>");
                        response.append("<html>");
                        response.append("   <head>");
                        response.append("       <meta charset=\"UTF-8\">");
                        response.append("       <meta name=\"author\" content=\"Dochi\">");
                        response.append("       <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                        response.append("       <title>" + buttonNumber + "번 자판기</title>");
                        response.append("   </head>");
                        response.append("   <body>");
                        response.append("       <h1>" + buttonNumber + "번 자판기 정보를 표시합니다.</h1>");
//                        response.append("       <p>매출현황 정보를 표시합니다.</p>");
                        response.append("       <button onclick=\"buttonClicked('/')\">Go Back</button>");
                        response.append("       <button onclick=\"buttonRefresh('/')\">새로고침</button><br>");
                        response.append("       <button onclick=\"showDrinkMoneyList()\">음료수별 매출현황</button>");
                        response.append("       <button onclick=\"showDrinkNameChange()\">음료이름 변경</button>");
                        response.append("       <button onclick=\"showDrinkStockChange()\">음료재고 현황</button>");
                        response.append("       <button onclick=\"showDrinkDateList()\">날짜별 매출현황</button>");

                        response.append("       <div id=\"output\"></div>");

                        response.append("       <script>");
                        response.append("           function buttonClicked(url) {");
                        response.append("               window.location.href = url;");
                        response.append("           }");
                        response.append("function showDrinkMoneyList() {");
                        response.append("    document.getElementById('output').innerHTML = '';");  // 기존 출력 내용 초기화

                        response.append("    var scrollContainer = document.createElement('div');");
                        response.append("    scrollContainer.id = 'scrollContainer';");
                        response.append("    scrollContainer.style.overflow = 'scroll';");
                        response.append("    scrollContainer.style.height = '500px';");
                        response.append("    scrollContainer.style.border = '1px solid #ccc';");

                        response.append("    var scrollContent = document.createElement('div');");
                        response.append("    scrollContent.style.width = '1000px';");
                        response.append("    scrollContent.style.height = '100px';");

                        if(drinkMoneyList.get(drinkIndex)!=null) {
//                                System.out.println("\"...\" = " + "...");
                                for (String str : drinkMoneyList.get(drinkIndex)) {
                                        response.append("    var paragraph = document.createElement('p');");
                                        response.append("    paragraph.textContent = '" + str + "';");
                                        response.append("    scrollContent.appendChild(paragraph);");
                                }
                        }
                        else {
                                response.append("    var paragraph = document.createElement('p');");
                                response.append("    paragraph.textContent = '" + "no" + "';");
                                response.append("    scrollContent.appendChild(paragraph);");
                        }

                        response.append("    scrollContainer.appendChild(scrollContent);");
                        response.append("    document.getElementById('output').appendChild(scrollContainer);");
                        response.append("}");

                        response.append("function buttonRefresh() {");
                        response.append("              location.reload();");


                        response.append("}");

                        response.append("function showDrinkDateList() {");
                        response.append("    document.getElementById('output').innerHTML = '';");  // 기존 출력 내용 초기화

                        response.append("    var scrollContainer = document.createElement('div');");
                        response.append("    scrollContainer.id = 'scrollContainer';");
                        response.append("    scrollContainer.style.overflow = 'scroll';");
                        response.append("    scrollContainer.style.height = '500px';");
                        response.append("    scrollContainer.style.border = '1px solid #ccc';");

                        response.append("    var scrollContent = document.createElement('div');");
                        response.append("    scrollContent.style.width = '1000px';");
                        response.append("    scrollContent.style.height = '100px';");

                        if(drinkDateList.get(drinkIndex)!=null) {
//                                System.out.println("\"...\" = " + "...");
                                for (String str : drinkDateList.get(drinkIndex)) {
                                        response.append("    var paragraph = document.createElement('p');");
                                        response.append("    paragraph.textContent = '" + str + "';");
                                        response.append("    scrollContent.appendChild(paragraph);");
                                }
                        }
                        else {
                                response.append("    var paragraph = document.createElement('p');");
                                response.append("    paragraph.textContent = '" + "no" + "';");
                                response.append("    scrollContent.appendChild(paragraph);");
                        }

                        response.append("    scrollContainer.appendChild(scrollContent);");
                        response.append("    document.getElementById('output').appendChild(scrollContainer);");
                        response.append("}");


                        response.append("           function showDrinkNameChange() {");

                        response.append("               var matrix = [");
                        response.append("                   " +generateDrinkNameArray(drinkIndex)+ ",");
                        response.append("                   ['<button onclick=\"changeDrinkName(0)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkName(1)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkName(2)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkName(3)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkName(4)\">변경</button>'],");
                        response.append("                   " +  generateDrinkPriceArray(drinkIndex) +" ,");
                        response.append("                   ['<button onclick=\"changeDrinkPrice(0)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkPrice(1)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkPrice(2)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkPrice(3)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkPrice(4)\">변경</button>']");

                        response.append("               ];");
                        response.append("               var tableHTML = '<table>';");
                        // ...
//                        response.append("           }");

// 첫 번째 행에 텍스트 추가
                        response.append("               tableHTML += '<tr>';");
                        response.append("               for (var i = 0; i < matrix[0].length; i++) {");
                        response.append("                   tableHTML += '<td>' + matrix[0][i] + '</td>';");
                        response.append("               }");
                        response.append("               tableHTML += '</tr>';");

// 두 번째 행에 버튼 추가
                        response.append("               tableHTML += '<tr>';");
                        response.append("               for (var i = 0; i < matrix[1].length; i++) {");
                        response.append("                   tableHTML += '<td>' + matrix[1][i] + '</td>';");
                        response.append("               }");
                        response.append("               tableHTML += '</tr>';");


                        response.append("               tableHTML += '<tr>';");
                        response.append("               for (var i = 0; i < matrix[2].length; i++) {");
                        response.append("                   tableHTML += '<td>' + matrix[2][i] + '</td>';");
                        response.append("               }");
                        response.append("               tableHTML += '</tr>';");

// 네 번째 행에 버튼 추가
                        response.append("               tableHTML += '<tr>';");
                        response.append("               for (var i = 0; i < matrix[3].length; i++) {");
                        response.append("                   tableHTML += '<td>' + matrix[3][i] + '</td>';");
                        response.append("               }");
                        response.append("               tableHTML += '</tr>';");

                        response.append("               tableHTML += '</table>';");
                        response.append("               document.getElementById('output').innerHTML = tableHTML;");
                        response.append("           }");
                        response.append("           function hideScrollContainer() {");
                        response.append("               var scrollContainer = document.getElementById('scrollContainer');");
                        response.append("               if (scrollContainer) {");
                        response.append("                   scrollContainer.style.display = 'none';");
                        response.append("               }");
                        response.append("           }");
                        response.append("function showDrinkStockChange() {");
                        response.append("    document.getElementById('output').innerHTML = '';");
                        response.append("    var table = document.createElement('table');");
                        response.append("    table.style.border = '1px solid #ccc';");
                        response.append("    table.style.borderCollapse = 'collapse';");

                        response.append("    var drinks = " + generateDrinkNameArray(drinkIndex) + ";"); // 음료수의 이름 배열

                        response.append("    var prices = " + generateDrinkStockArray(drinkIndex) + ";"); // 음료수의 가격 배열

                        response.append("    for (var i = 0; i < 2; i++) {"); // 2개의 행으로 수정
                        response.append("        var row = document.createElement('tr');");
                        response.append("        for (var j = 0; j < 5; j++) {");
                        response.append("            var cell = document.createElement('td');");
                        response.append("            cell.style.border = '1px solid #ccc';");
                        response.append("            cell.style.padding = '10px';");
                        response.append("            var text = document.createTextNode('');");

                        response.append("            if (i === 0) {");
                        response.append("                text.nodeValue = drinks[j];"); // 음료수의 이름 출력
                        response.append("            } else if (i === 1) {");
                        response.append("                text.nodeValue = prices[j];"); // 음료수의 가격 출력
                        response.append("            }");

                        response.append("            cell.appendChild(text);");
                        response.append("            row.appendChild(cell);");
                        response.append("        }");
                        response.append("        table.appendChild(row);");
                        response.append("    }");

                        response.append("    document.getElementById('output').appendChild(table);");
                        response.append("}");

                        response.append("           function showDrinkSales() {");
                        response.append("               document.getElementById('output').innerHTML = '음료별 매출현황이 출력됩니다.';");
                        response.append("               hideScrollContainer();");
                        response.append("           }");
                        response.append("function changeDrinkName(index) {");
                        response.append("    var newName = prompt('변경할 음료 이름을 입력하세요:');");
                        response.append("    if (newName) {");
                        response.append("        var data = {");
                        response.append("            machineIndex: "+drinkIndex+",");
                        response.append("            index: index,");
                        response.append("            newName: newName");
                        response.append("        };");
                        response.append("        fetch('http://localhost:3000/changeName', {");
                        response.append("            method: 'POST',");
                        response.append("            body: JSON.stringify(data),");
                        response.append("            headers: { 'Content-Type': 'application/json' }");
                        response.append("        })");
                        response.append("        .then(function(response) {");
                        response.append("            return response.text();");
                        response.append("        })");
                        response.append("        .then(function(data) {");
                        response.append("              location.reload();");
                        response.append("            document.getElementById('output').innerHTML = data;");
                        response.append("        })");
                        response.append("        .catch(function(error) {");
                        response.append("            console.log('Error:', error);");
                        response.append("        });");


                        response.append("    } else {");
                        response.append("        document.getElementById('output').innerHTML = '음료 이름 변경이 취소되었습니다.';");
                        response.append("    }");
                        response.append("    hideScrollContainer();");
                        response.append("}");

                        response.append("function changeDrinkPrice(index) {");
                        response.append("    var newPrice = prompt('변경할 음료 가격을 입력하세요:');");
                        response.append("    if (newPrice) {");
                        response.append("        var data = {");
                        response.append("            machineIndex: "+drinkIndex+",");
                        response.append("            index: index,");
                        response.append("            newPrice: newPrice");
                        response.append("        };");
                        response.append("        fetch('http://localhost:3000/changePrice', {");
                        response.append("            method: 'POST',");
                        response.append("            body: JSON.stringify(data),");
                        response.append("            headers: { 'Content-Type': 'application/json' }");
                        response.append("        })");
                        response.append("        .then(function(response) {");
                        response.append("            return response.text();");
                        response.append("        })");
                        response.append("        .then(function(data) {");
                        response.append("              location.reload();");
                        response.append("            document.getElementById('output').innerHTML = data;");
                        response.append("        })");
                        response.append("        .catch(function(error) {");
                        response.append("            console.log('Error:', error);");
                        response.append("        });");
//                        response.append("        document.getElementById('output').innerHTML = data['newPrice'];");
//                        response.append("Console.log(data)");

                        response.append("    } else {");
                        response.append("        document.getElementById('output').innerHTML = '음료 가격 변경이 취소되었습니다.';");
                        response.append("    }");
                        response.append("    hideScrollContainer();");
                        response.append("}");


                        response.append("       </script>");
                        response.append("   </body>");
                        response.append("</html>");

                        sendResponse(exchange, response.toString(), "text/html;charset=UTF-8");
                }
        }
        private String generateDrinkNameArray(int i) {
                StringBuilder arrayBuilder = new StringBuilder();
                arrayBuilder.append("[");

                if(drinks[i]!=null) {
                        String[] name = drinks[i].getDrinksName();
                        for (int j = 0; j < 5; j++) {
                                System.out.println("name[j] = " + name[j]);
                                arrayBuilder.append("'").append(name[j]).append("'");
                                if (j < 4) {
                                        arrayBuilder.append(", ");
                                }
                        }
                }

                arrayBuilder.append("]");
                return arrayBuilder.toString();
        }
        private String generateDrinkPriceArray(int i) {
                StringBuilder arrayBuilder = new StringBuilder();
                arrayBuilder.append("[");

                if(drinks[i]!=null) {
                        int[] price = drinks[i].getDrinkPrice();
                        for (int j = 0; j < 5; j++) {
                                arrayBuilder.append("'").append(price[j]).append("'");
                                if (j < 4) {
                                        arrayBuilder.append(", ");
                                }
                        }
                }

                arrayBuilder.append("]");
                return arrayBuilder.toString();
        }
        private String generateDrinkStockArray(int i) {
                StringBuilder arrayBuilder = new StringBuilder();
                arrayBuilder.append("[");

                if(drinks[i]!=null) {
                        int[] stock = drinks[i].getDrinkStock();
                        for (int j = 0; j < 5; j++) {
                                arrayBuilder.append("'").append(stock[i]).append("'");
                                if (j < 4) {
                                        arrayBuilder.append(", ");
                                }
                        }
                }

                arrayBuilder.append("]");
                return arrayBuilder.toString();
        }



        private void sendResponse(HttpExchange exchange, String response, String contentType) throws IOException {
                exchange.getResponseHeaders().add("Content-Type", contentType);
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
        }
}
