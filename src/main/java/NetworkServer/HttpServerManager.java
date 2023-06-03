package NetworkServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpServerManager {
        private final String HOSTNAME = "0.0.0.0";
        private final int BACKLOG = 0;
        private HttpServer server = null;

        String [] drinkName = new String [5];
        int [] drinkPrice = new int [5];
        int [] drinkStock = new int [5];


        public HttpServerManager(String host, int port) throws IOException {
                createServer(host, port);
                for(int i=0; i<5; i++){
                        drinkName[i] = "음료"+i;
                        drinkPrice[i] = i;
                }
        }

        private void createServer(String host, int port) throws IOException {
                this.server = HttpServer.create(new InetSocketAddress(host, port), BACKLOG);
                server.createContext("/", new RootHandler());
                server.createContext("/sales", new SalesHandler());
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
        public void setDrink(String[] names, int[] prices, int[] stocks){
                drinkName = names;
                drinkPrice = prices;
                drinkStock = stocks;

                for (int i = 0; i < 5; i++) {
                        String name = drinkName[i];
                        int price = drinkPrice[i];

                        System.out.println("Name: " + name + ", Price: " + price);
                        // 각 이름과 가격에 대한 추가 처리 로직 구현
                }
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


        private class SalesHandler implements HttpHandler {

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
                        response.append("       <button onclick=\"buttonClicked('/')\">Go Back</button><br>");
                        response.append("       <button onclick=\"showDaySales()\">날짜별 매출현황</button>");
                        response.append("       <button onclick=\"showDrinkNameChange()\">음료이름 변경</button>");
                        response.append("       <button onclick=\"showDrinkStockChange()\">음료재고 현황</button>");

                        response.append("       <div id=\"output\"></div>");

                        response.append("       <script>");
                        response.append("           function buttonClicked(url) {");
                        response.append("               window.location.href = url;");
                        response.append("           }");
                        response.append("           function showDaySales() {");
                        response.append("               document.getElementById('output').innerHTML = '';");
                        response.append("               var scrollContainer = document.createElement('div');");
                        response.append("               scrollContainer.id = 'scrollContainer';");
                        response.append("               scrollContainer.style.overflow = 'scroll';");
                        response.append("               scrollContainer.style.height = '300px';");
                        response.append("               scrollContainer.style.border = '1px solid #ccc';");

                        response.append("               var scrollContent = document.createElement('div');");
                        response.append("               scrollContent.style.width = '1000px';");
                        response.append("               scrollContent.style.height = '200px';");
//                        response.append("               scrollContent.style.background = '#f7f7f7';");
//                        response.append("               scrollContent.style.padding = '10px';");
//                        response.append("               scrollContent.innerHTML = '스크롤 페이지 내용입니다.';");

                        response.append("               scrollContainer.appendChild(scrollContent);");
                        response.append("               document.getElementById('output').appendChild(scrollContainer);");
                        response.append("           }");
                        response.append("           function showDrinkNameChange() {");
                        response.append("               var matrix = [");
                        response.append("                   " + generateDrinkNameArray() + ",");
                        response.append("                   ['<button onclick=\"changeDrinkName(0)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkName(1)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkName(2)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkName(3)\">변경</button>',");
                        response.append("                    '<button onclick=\"changeDrinkName(4)\">변경</button>']");
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

                        response.append("    var drinks = " + generateDrinkNameArray() + ";"); // 음료수의 이름 배열

                        response.append("    var prices = " + generateDrinkStockArray() + ";"); // 음료수의 가격 배열

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
                        response.append("           function changeDrinkName(index) {");
                        response.append("               var newName = prompt('변경할 음료 이름을 입력하세요:');");
                        response.append("               if (newName) {");
                        response.append("                   document.getElementById('output').innerHTML = '음료 ' + (index + 1) + '의 이름이 ' + newName + '으로 변경되었습니다.';");
                        response.append("               } else {");
                        response.append("                   document.getElementById('output').innerHTML = '음료 이름 변경이 취소되었습니다.';");
                        response.append("               }");
                        response.append("               hideScrollContainer();");
                        response.append("           }");
                        response.append("       </script>");
                        response.append("   </body>");
                        response.append("</html>");

                        sendResponse(exchange, response.toString(), "text/html;charset=UTF-8");
                }
        }
        private String generateDrinkNameArray() {
                StringBuilder arrayBuilder = new StringBuilder();
                arrayBuilder.append("[");
                for (int i = 0; i < drinkName.length; i++) {
                        String name = drinkName[i];
                        arrayBuilder.append("'").append(name).append("'");
                        if (i < drinkName.length - 1) {
                                arrayBuilder.append(", ");
                        }
                }
                arrayBuilder.append("]");
                return arrayBuilder.toString();
        }
        private String generateDrinkPriceArray() {
                StringBuilder arrayBuilder = new StringBuilder();
                arrayBuilder.append("[");
                for (int i = 0; i < drinkPrice.length; i++) {
                        int price = drinkPrice[i];
                        arrayBuilder.append("'").append(price).append("'");
                        if (i < drinkName.length - 1) {
                                arrayBuilder.append(", ");
                        }
                }
                arrayBuilder.append("]");
                return arrayBuilder.toString();
        }
        private String generateDrinkStockArray() {
                StringBuilder arrayBuilder = new StringBuilder();
                arrayBuilder.append("[");
                for (int i = 0; i < drinkStock.length; i++) {
                        int stock = drinkStock[i];
                        arrayBuilder.append("'").append(stock).append("'");
                        if (i < drinkName.length - 1) {
                                arrayBuilder.append(", ");
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
