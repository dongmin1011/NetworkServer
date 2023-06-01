package NetworkServer.server;

import java.util.*;
class Log_collect_day { // 일 매출 클래스
    private int day; // 일을 적음
    private int sum; // 그 일의 매출

    public Log_collect_day(int day, int sum) {
        this.day = day; // 처음 만들어질때 이 일을 넣음
        this.sum = sum; // 처음 만들어질때 매출을 적음
    }

    // 일 매출 추가 함수
    public void add_sum(int sum) {
        this.sum += sum; // 같은 일이 중복되면 새로 만들지 않고 매출만 추가
    }

    // 몇일인지 알리는 함수
    public int get_day() {
        return day; // 몇일인지 알려줌
    }

    // 일 매출 반환 함수
    public int get_day_money() {
        return sum; // 합산된 일 매출 반환
    }
}

class Log_collect_month { // 월 매출 클레스

    private int month; // 월을 넣는 변수
    ArrayList<Log_collect_day> day; // 월의 일을 동적 배열로 생성

    public Log_collect_month(int month) {
        this.month = month; // 처음 만들어질때 몇 월인지 입력하고
        day = new ArrayList<Log_collect_day>(); // 동적 배열의 객채 생성
    }

    // 몇 월인지 반환하는 함수
    public int get_month() {
        return month; // 월 반환
    }

    // 월 매출 반환 함수
    public int get_month_money() {
        int i = 0; // 매출을 담는 변수

        for (Log_collect_day temp : day) { // 일 동적 배열을 끝까지 돌음
            i += temp.get_day_money(); // 일 동적 배열의 일 매출을 모두 더함
        }

        return i; // 더한 값을 반환
    }

}

class Log_collect_year { // 년도 클래스

    private int year; // 년도를 담는 변수
    ArrayList<Log_collect_month> month; // 월을 동적 배열로 만듬

    public Log_collect_year(int year) {
        this.year = year; // 처음 만들어질시 년도를 적음
        month = new ArrayList<Log_collect_month>(); // 동적 배열에 객체 만들어줌
    }

    // 몇 년도 인지 반환하는 함수
    public int get_year() {
        return year; // 몇 년도인지 반환
    }

    public int get_year_money() {
        int i = 0; // 매출을 담는 변수

        for (Log_collect_month temp : month) { // 일 동적 배열을 끝까지 돌음
            i += temp.get_month_money(); // 일 동적 배열의 일 매출을 모두 더함
        }

        return i; // 더한 값을 반환
    }
}

class Log_collect_name { // 음료 클래스
    private String name; // 음료 이름을 담는 문자열
    ArrayList<Log_collect_year> year; // 년도 클래스 동적 배열

    public Log_collect_name(String name) {
        this.name = name; // 초기에 만들어질 시 이름을 담음
        year = new ArrayList<Log_collect_year>(); // 동적 배열에 객체 생성
    }

    // 이름을 반환하는 함수
    public String get_name() {
        return name; // 음료 이름 반환
    }

    public int get_name_money() {
        int i = 0; // 매출을 담는 변수

        for (Log_collect_year temp : year) { // 일 동적 배열을 끝까지 돌음
            i += temp.get_year_money(); // 일 동적 배열의 일 매출을 모두 더함
        }

        return i; // 더한 값을 반환
    }
}

class Log_collect_port { // 포트 클래스
    private int port; // 포트를 담는 이름
    ArrayList<Log_collect_name> drink; // 년도 클래스 동적 배열

    public Log_collect_port(int port) {
        this.port = port; // 초기에 만들어질 시 이름을 담음
        drink = new ArrayList<Log_collect_name>(); // 동적 배열에 객체 생성
    }

    // 이름을 반환하는 함수
    public int get_port() {
        return port; // 음료 이름 반환
    }

    public int get_port_money() {
        int i = 0; // 매출을 담는 변수

        for (Log_collect_name temp : drink) { // 일 동적 배열을 끝까지 돌음
            i += temp.get_name_money(); // 일 동적 배열의 일 매출을 모두 더함
        }

        return i; // 더한 값을 반환
    }
}

