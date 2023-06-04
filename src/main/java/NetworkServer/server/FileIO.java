package NetworkServer.server;

import java.io.*;
import java.util.*;
import NetworkServer.server.*;

    public class FileIO {

        private File file;
        private BufferedWriter bw;
        private FileReader fr;
        private BufferedReader br;

        public void SaleFileWrite(String str, boolean write) {    //sale텍스트 파일에 글을 씀 false가 입력되면 처음부터 다시 입력받고 true가 입력되면 뒤에 이어서 씀
            try {
                file = new File("sale.txt");                    //sale.txt파일 오픈
                bw = new BufferedWriter(new FileWriter(file, write));    //bufferedWriter를 이용하면 빠른 입출력이 가능하다.

                if (file.isFile() && file.canWrite()) {        //파일이 존재하고 쓸 수 있다면

                    bw.write(str);                            //입력받은 str문자열을 파일에 추가
                    if (!str.equals("")) bw.newLine();

                    bw.flush();                                //남아있는 데이터를 모두 출력
                    bw.close();                                //스트림을 닫음
                }
            } catch (IOException e) {
                //	e.printStackTrace();
            }
        }

        public void SoldOutWrite(String str, boolean write) {    //soldout 텍스트 파일에 글을 씀 false가 입력되면 처음부터 다시 입력받고 true가 입력되면 뒤에 이어서 씀
            try {
                file = new File("Soldout.txt");                    //sale.txt파일 오픈
                bw = new BufferedWriter(new FileWriter(file, write));    //bufferedWriter를 이용하면 빠른 입출력이 가능하다.

                if (file.isFile() && file.canWrite()) {        //파일이 존재하고 쓸 수 있다면

                    bw.write(str);                            //입력받은 str문자열을 파일에 추가
                    if (!str.equals("")) bw.newLine();

                    bw.flush();                                //남아있는 데이터를 모두 출력
                    bw.close();                                //스트림을 닫음
                }
            } catch (IOException e) {
                //	e.printStackTrace();
            }
        }

        void get_Drink_info(ArrayList<Log_collect_port> logArray) {

            ArrayList<Log_collect_port> logfile = logArray; // 포트->음료->년도->월->일의 탐색이 가능한 클래스 동적 배열 생성

            try (FileReader read_log = new FileReader("sale.txt")) { // 프로젝트 폴더 안에 있는 sale 파일을 읽어옴
                BufferedReader read = new BufferedReader(read_log); // 문자열 처리 스트림에 읽어온 파일을 추가함
                while (true) { // 계속 반복하는 반복문
                    String log = read.readLine(); // 파일에서 한 줄의 문자열을 가져옴
                    if (log == null) // 파일이 끝나면
                        break; // 반복문 종료
                    UpdateLogFile(log, logfile);
                }
            } catch (IOException e) { // 파일을 읽어올 수 없으면
                //information.setText("파일이 없습니다! 오류!"); // 텍스트 구역에 없다고 알림
            }
        }

        void get_withoutDrink_info(ArrayList<Log_collect_port_without_drink> logArray) {

            ArrayList<Log_collect_port_without_drink> logfile = logArray; // 포트->음료->년도->월->일의 탐색이 가능한 클래스 동적 배열 생성

            try (FileReader read_log = new FileReader("sale.txt")) { // 프로젝트 폴더 안에 있는 sale 파일을 읽어옴
                BufferedReader read = new BufferedReader(read_log); // 문자열 처리 스트림에 읽어온 파일을 추가함
                while (true) { // 계속 반복하는 반복문
                    String log = read.readLine(); // 파일에서 한 줄의 문자열을 가져옴
                    if (log == null) // 파일이 끝나면
                        break; // 반복문 종료
                    UpdateLogFileWithoutDrink(log, logfile);
                }
            } catch (IOException e) { // 파일을 읽어올 수 없으면
                //information.setText("파일이 없습니다! 오류!"); // 텍스트 구역에 없다고 알림
            }
        }

        void UpdateLogFile(String log, ArrayList<Log_collect_port> logfile) {
            try {
                StringTokenizer st = new StringTokenizer(log, " 원 일 년 월"); // 띄어쓰기와 원을 구분자로 문자열을 나눔

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

                int flag_port = 0;
                int flag_name = 0;
                int flag_year = 0;
                int flag_month = 0;
                int flag_day = 0; // 동적 배열에 존재하는 이름, 년, 월, 일인지 체크하는 변수
                int year_index = 0;
                int month_index = 0;
                int name_index = 0; // 이미 동적 배열에 존재하는 년, 월, 일 인덱스를 담는 변수
                int port_index = 0;
                if (logfile.isEmpty()) { // 처음 동적 배열에 넣으면
                    Log_collect_port array_port = new Log_collect_port(port);
                    Log_collect_name array_name = new Log_collect_name(name); // 해당 이름 클래스 생성
                    Log_collect_year array_year = new Log_collect_year(year); // 해당 년도 클래스 생성
                    Log_collect_month array_month = new Log_collect_month(month); // 해당 달 클래스 생성
                    Log_collect_day array_day = new Log_collect_day(day, price); // 해당 일 클래스 생성
                    logfile.add(array_port);
                    logfile.get(0).drink.add(array_name);
                    logfile.get(0).drink.get(0).year.add(array_year);
                    logfile.get(0).drink.get(0).year.get(0).month.add(array_month);
                    logfile.get(0).drink.get(0).year.get(0).month.get(0).day.add(array_day); // 동적 배열에 포트->이름->년->월->일 순서대로 추가
                } else { // 아니면
                    for (int i1 = 0; i1 < logfile.size(); i1++) { // 먼저 포트 동적 배열을 탐색
                        if (logfile.get(i1).get_port() == port) { // 같은 포트가 있으면
                            flag_port = 1; // 배열에 포트 번호가 존재하면 존재 한다고 체크
                            port_index = i1; // 그 포트 인덱스를 담음
                            break; // 반복문을 나감
                        }
                    }
                    if (flag_port == 1) {
                        for (int i2 = 0; i2 < logfile.size(); i2++) { // 먼저 음료 동적 배열을 탐색
                            if (logfile.get(port_index).drink.get(i2).get_name().equals(name)) { // 같은 이름이 있으면
                                flag_name = 1; // 배열에 음료 이름이 존재하면 존재 한다고 체크
                                name_index = i2; // 그 음료 인덱스를 담음
                                break; // 반복문을 나감
                            }
                        }
                        if (flag_name == 1) { // 동적 배열에 음료 이름이 존재하면

                            for (int i3 = 0; i3 < logfile.get(port_index).drink.get(name_index).year.size(); i3++) { // 그 음료 인덱스 안의 년도 배열을 탐색
                                if (logfile.get(port_index).drink.get(name_index).year.get(i3).get_year() == year) { // 같은 년도가 있으면
                                    flag_year = 1; // 배열에 년도가 존재 한다고 체크
                                    year_index = i3; // 그 년도 인덱스를 담음
                                    break; // 반복문을 나감
                                }
                            }
                            if (flag_year == 1) { // 해당 년도가 해당 음료에 존재하면

                                for (int i4 = 0; i4 < logfile.get(port_index).drink.get(name_index).year.get(year_index).month.size(); i4++) {
                                    // 해당 음료의 해당 년도의 달 배열을 탐색
                                    if (logfile.get(port_index).drink.get(name_index).year.get(year_index).month.get(i4).get_month() == month) { // 같은 달이 있으면
                                        flag_month = 1; // 배열에 달이 존재 한다고 체크
                                        month_index = i4; // 그 달의 인덱스를 담음
                                        break; // 반복문을 나감
                                    }
                                }
                                if (flag_month == 1) { // 해당 음료에 해당 년도에 해당 달이 있으면
                                    for (int i5 = 0; i5 < logfile.get(port_index).drink.get(name_index).year.get(year_index).month
                                            .get(month_index).day.size(); i5++) { // 해당 음료의 해당 년도의 해당 달의 일 탐색
                                        if (logfile.get(port_index).drink.get(name_index).year.get(year_index).month
                                                .get(month_index).day.get(i5).get_day() == day) { // 만약 일이 있다면
                                            flag_day = 1; // 해당 일이 존재 한다고 체크
                                            logfile.get(port_index).drink.get(name_index).year.get(year_index).month
                                                    .get(month_index).day.get(i5).add_sum(price); // 해당 일에 가격을 더해 해당 일의 매출을 반영
                                            break; // 반복문을 나옴
                                        }
                                    }
                                    if (flag_day == 0) { // 해당 일이 없을 때
                                        Log_collect_day array_day = new Log_collect_day(day, price); // 해당 일 클래스 생성
                                        logfile.get(port_index).drink.get(name_index).year.get(year_index).month
                                                .get(month_index).day.add(array_day); // 해당 달에 해당 일 추가
                                        // 해당 음료의 해당 년도 해당 월 배열에 일 추가
                                    }
                                    flag_day = 0; // 다시 같은 일을 동적 배열에서 찾기 위하여 다시 0을 담음
                                } else { // 해당 달이 없으면
                                    Log_collect_month array_month = new Log_collect_month(month); // 해당 달 클래스 생성
                                    Log_collect_day array_day = new Log_collect_day(day, price); // 해당 일 클래스 생성
                                    logfile.get(port_index).drink.get(name_index).year.get(year_index).month.add(array_month);
                                    logfile.get(port_index).drink.get(name_index).year.get(year_index).month
                                            .get(logfile.get(port_index).drink.get(name_index).year.get(year_index).month.size() - 1).day
                                            .add(array_day);
                                    // 해당 음료의 년도 배열에 월->일 순서대로 추가
                                }
                                flag_month = 0; // 다시 같은 달을 동적 배열에서 찾기 위하여 다시 0을 담음
                            } else { // 해당 년도가 없으면
                                Log_collect_year array_year = new Log_collect_year(year); // 해당 년도 클래스 생성
                                Log_collect_month array_month = new Log_collect_month(month); // 해당 달 클래스 생성
                                Log_collect_day array_day = new Log_collect_day(day, price); // 해당 일 클래스 생성
                                logfile.get(port_index).drink.get(name_index).year.add(array_year);
                                logfile.get(port_index).drink.get(name_index).year.get(logfile.get(port_index).drink.get(name_index).year.size() - 1).month
                                        .add(array_month);
                                logfile.get(port_index).drink.get(name_index).year.get(logfile.get(port_index).drink.get(name_index).year.size() - 1).month
                                        .get(0).day.add(array_day);
                                // 해당 음료 배열에 년->월->일 순서대로 추가
                            }
                            flag_year = 0; // 다시 같은 년을 동적 배열에서 찾기 위하여 다시 0을 담음
                        } else { // 같은 음료 이름이 없으면
                            Log_collect_name array_name = new Log_collect_name(name); // 해당 음료 이름 클래스 생성
                            Log_collect_year array_year = new Log_collect_year(year); // 해당 년도 클래스 생성
                            Log_collect_month array_month = new Log_collect_month(month); // 해당 달 클래스 생성
                            Log_collect_day array_day = new Log_collect_day(day, price); // 헤당 날 클래스 생성
                            logfile.get(port_index).drink.add(array_name);
                            logfile.get(port_index).drink.get(logfile.get(port_index).drink.size() - 1).year.add(array_year);
                            logfile.get(port_index).drink.get(logfile.get(port_index).drink.size() - 1).year.get(0).month.add(array_month);
                            logfile.get(port_index).drink.get(logfile.get(port_index).drink.size() - 1).year.get(0).month.get(0).day.add(array_day);
                            // 동적 배열에 이름->년->월->일 순서대로 추가
                        }
                        flag_name = 0; // 다시 음료 이름을 동적 배열에서 찾기 위하여 다시 0을 담음
                    } else {
                        Log_collect_port array_port = new Log_collect_port(port); // 해당 음료 이름 클래스 생성
                        Log_collect_name array_name = new Log_collect_name(name); // 해당 음료 이름 클래스 생성
                        Log_collect_year array_year = new Log_collect_year(year); // 해당 년도 클래스 생성
                        Log_collect_month array_month = new Log_collect_month(month); // 해당 달 클래스 생성
                        Log_collect_day array_day = new Log_collect_day(day, price); // 헤당 날 클래스 생성
                        logfile.add(array_port);
                        logfile.get(logfile.size() - 1).drink.add(array_name);
                        logfile.get(logfile.size() - 1).drink.get(0).year.add(array_year);
                        logfile.get(logfile.size() - 1).drink.get(0).year.get(0).month.add(array_month);
                        logfile.get(logfile.size() - 1).drink.get(0).year.get(0).month.get(0).day.add(array_day);
                        // 동적 배열에 이름->년->월->일 순서대로 추가
                    }
                    flag_port = 0;
                }
            } catch (NumberFormatException e) { // 숫자를 불러오지 못하면
                //information.setText("숫자를 불러오지 못했습니다! 에러!"); // 입력 창에 숫자를 불러오지 못했다고 알림
            }
        }

        void UpdateLogFileWithoutDrink(String log, ArrayList<Log_collect_port_without_drink> logfile) {
            try {
                StringTokenizer st = new StringTokenizer(log, " 원 일 년 월"); // 띄어쓰기와 원을 구분자로 문자열을 나눔

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

                int flag_port = 0;
                int flag_name = 0;
                int flag_year = 0;
                int flag_month = 0;
                int flag_day = 0; // 동적 배열에 존재하는 이름, 년, 월, 일인지 체크하는 변수
                int year_index = 0;
                int month_index = 0;
                int name_index = 0; // 이미 동적 배열에 존재하는 년, 월, 일 인덱스를 담는 변수
                int port_index = 0;
                if (logfile.isEmpty()) { // 처음 동적 배열에 넣으면
                    Log_collect_port_without_drink array_port = new Log_collect_port_without_drink(port);
                    Log_collect_year array_year = new Log_collect_year(year); // 해당 년도 클래스 생성
                    Log_collect_month array_month = new Log_collect_month(month); // 해당 달 클래스 생성
                    Log_collect_day array_day = new Log_collect_day(day, price); // 해당 일 클래스 생성
                    logfile.add(array_port);
                    logfile.get(0).year.add(array_year);
                    logfile.get(0).year.get(0).month.add(array_month);
                    logfile.get(0).year.get(0).month.get(0).day.add(array_day); // 동적 배열에 포트->이름->년->월->일 순서대로 추가
                } else { // 아니면
                    for (int i1 = 0; i1 < logfile.size(); i1++) { // 먼저 포트 동적 배열을 탐색
                        if (logfile.get(i1).get_port() == port) { // 같은 포트가 있으면
                            flag_port = 1; // 배열에 포트 번호가 존재하면 존재 한다고 체크
                            port_index = i1; // 그 포트 인덱스를 담음
                            break; // 반복문을 나감
                        }
                    }
                    if (flag_port == 1) {
                        for (int i2 = 0; i2 < logfile.get(port_index).year.size(); i2++) { // 그 음료 인덱스 안의 년도 배열을 탐색
                            if (logfile.get(port_index).year.get(i2).get_year() == year) { // 같은 년도가 있으면
                                flag_year = 1; // 배열에 년도가 존재 한다고 체크
                                year_index = i2; // 그 년도 인덱스를 담음
                                break; // 반복문을 나감
                            }
                        }
                        if (flag_year == 1) { // 동적 배열에 음료 이름이 존재하면

                            for (int i3 = 0; i3 < logfile.get(port_index).year.get(year_index).month.size(); i3++) {
                                // 해당 음료의 해당 년도의 달 배열을 탐색
                                if (logfile.get(port_index).year.get(year_index).month.get(i3).get_month() == month) { // 같은 달이 있으면
                                    flag_month = 1; // 배열에 달이 존재 한다고 체크
                                    month_index = i3; // 그 달의 인덱스를 담음
                                    break; // 반복문을 나감
                                }
                            }
                            if (flag_month == 1) { // 해당 년도가 해당 음료에 존재하면

                                for (int i5 = 0; i5 < logfile.get(port_index).year.get(year_index).month
                                        .get(month_index).day.size(); i5++) { // 해당 음료의 해당 년도의 해당 달의 일 탐색
                                    if (logfile.get(port_index).year.get(year_index).month
                                            .get(month_index).day.get(i5).get_day() == day) { // 만약 일이 있다면
                                        flag_day = 1; // 해당 일이 존재 한다고 체크
                                        logfile.get(port_index).year.get(year_index).month
                                                .get(month_index).day.get(i5).add_sum(price); // 해당 일에 가격을 더해 해당 일의 매출을 반영
                                        break; // 반복문을 나옴
                                    }
                                }
                                if (flag_day == 0) { // 해당 일이 없을 때
                                    Log_collect_day array_day = new Log_collect_day(day, price); // 해당 일 클래스 생성
                                    logfile.get(port_index).year.get(year_index).month
                                            .get(month_index).day.add(array_day); // 해당 달에 해당 일 추가
                                    // 해당 음료의 해당 년도 해당 월 배열에 일 추가
                                }
                                flag_day = 0; // 다시 같은 일을 동적 배열에서 찾기 위하여 다시 0을 담음
                            } else { // 해당 달이 없으면
                                Log_collect_month array_month = new Log_collect_month(month); // 해당 달 클래스 생성
                                Log_collect_day array_day = new Log_collect_day(day, price); // 해당 일 클래스 생성
                                logfile.get(port_index).year.get(year_index).month.add(array_month);
                                logfile.get(port_index).year.get(year_index).month
                                        .get(logfile.get(port_index).year.get(year_index).month.size() - 1).day
                                        .add(array_day);
                                // 해당 음료의 년도 배열에 월->일 순서대로 추가
                            }
                            flag_month = 0; // 다시 같은 달을 동적 배열에서 찾기 위하여 다시 0을 담음
                        } else { // 해당 년도가 없으면
                            Log_collect_year array_year = new Log_collect_year(year); // 해당 년도 클래스 생성
                            Log_collect_month array_month = new Log_collect_month(month); // 해당 달 클래스 생성
                            Log_collect_day array_day = new Log_collect_day(day, price); // 해당 일 클래스 생성
                            logfile.get(port_index).year.add(array_year);
                            logfile.get(port_index).year.get(logfile.get(port_index).year.size() - 1).month
                                    .add(array_month);
                            logfile.get(port_index).year.get(logfile.get(port_index).year.size() - 1).month
                                    .get(0).day.add(array_day);
                            // 해당 음료 배열에 년->월->일 순서대로 추가
                        }
                        flag_year = 0; // 다시 같은 년을 동적 배열에서 찾기 위하여 다시 0을 담음
                    } else {
                        Log_collect_port_without_drink array_port = new Log_collect_port_without_drink(port); // 해당 음료 이름 클래스 생성
                        Log_collect_year array_year = new Log_collect_year(year); // 해당 년도 클래스 생성
                        Log_collect_month array_month = new Log_collect_month(month); // 해당 달 클래스 생성
                        Log_collect_day array_day = new Log_collect_day(day, price); // 헤당 날 클래스 생성
                        logfile.add(array_port);
                        logfile.get(logfile.size() - 1).year.add(array_year);
                        logfile.get(logfile.size() - 1).year.get(0).month.add(array_month);
                        logfile.get(logfile.size() - 1).year.get(0).month.get(0).day.add(array_day);
                        // 동적 배열에 이름->년->월->일 순서대로 추가
                    }
                    flag_port = 0;
                }
            } catch (NumberFormatException e) { // 숫자를 불러오지 못하면
                //information.setText("숫자를 불러오지 못했습니다! 에러!"); // 입력 창에 숫자를 불러오지 못했다고 알림
            }
        }

        void get_Drink_month_day_money(ArrayList<Log_collect_port> logfile) {

            for (int i = 0; i < logfile.size(); i++) { // 자판기 배열 탐색

                for (int j = 0; j < logfile.get(i).drink.size(); j++) { // 해당 자판기 배열의 이름 배열 탐색

                    for (int d = 0; d < logfile.get(i).drink.get(j).year.size(); d++) { // 해당 자판기, 이름의 년 배열 출력

                        for (int f = 0; f < logfile.get(i).drink.get(j).year.get(d).month.size(); f++) { // 해당 자판기, 이름의 년, 월 출력

                            for (int z = 0; f < logfile.get(i).drink.get(j).year.get(d).month.get(f).day.size(); z++) { // 해당 자판기, 이름의 년, 월, 일 출력

                            }
                        }
                    }
                }
            }
        }

        void get_Drink_month_day_money_without_drink(ArrayList<Log_collect_port_without_drink> logfile) {

            for (int i = 0; i < logfile.size(); i++) { // 자판기 배열 탐색

                    for (int d = 0; d < logfile.get(i).year.size(); d++) { // 자판기, 년도 배열 출력

                        for (int f = 0; f < logfile.get(i).year.get(d).month.size(); f++) { // 자판기, 년도, 월 배열 출력

                            for (int z = 0; f < logfile.get(i).year.get(d).month.get(f).day.size(); z++) { // 자판기, 년도, 월, 일 배열 출력

                            }
                        }
                    }
                }
        }
    }

