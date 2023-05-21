package NetworkServer.server;

import java.io.*;

public class FileIO {

    private File file;
    private BufferedWriter bw;
    private FileReader fr;
    private BufferedReader br;

    public void SaleFileWrite(String str, boolean write) { 	//sale텍스트 파일에 글을 씀 false가 입력되면 처음부터 다시 입력받고 true가 입력되면 뒤에 이어서 씀
        try {
            file = new File("sale.txt");					//sale.txt파일 오픈
            bw = new BufferedWriter(new FileWriter(file, write));	//bufferedWriter를 이용하면 빠른 입출력이 가능하다.

            if(file.isFile() && file.canWrite()) {		//파일이 존재하고 쓸 수 있다면

                bw.write(str);							//입력받은 str문자열을 파일에 추가
                if(!str.equals(""))	bw.newLine();

                bw.flush();								//남아있는 데이터를 모두 출력
                bw.close();								//스트림을 닫음
            }
        } catch (IOException e) {
            //	e.printStackTrace();
        }
    }

    public void SoldOutWrite(String str, boolean write) { 	//soldout 텍스트 파일에 글을 씀 false가 입력되면 처음부터 다시 입력받고 true가 입력되면 뒤에 이어서 씀
        try {
            file = new File("Soldout.txt");					//sale.txt파일 오픈
            bw = new BufferedWriter(new FileWriter(file, write));	//bufferedWriter를 이용하면 빠른 입출력이 가능하다.

            if(file.isFile() && file.canWrite()) {		//파일이 존재하고 쓸 수 있다면

                bw.write(str);							//입력받은 str문자열을 파일에 추가
                if(!str.equals(""))	bw.newLine();

                bw.flush();								//남아있는 데이터를 모두 출력
                bw.close();								//스트림을 닫음
            }
        } catch (IOException e) {
            //	e.printStackTrace();
        }
    }
}
