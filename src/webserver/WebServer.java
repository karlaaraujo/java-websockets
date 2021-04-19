package webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(10000);
            while (true) {
                Socket s = ss.accept();
                Service servico = new Service(s);
                // starta uma thread de Servico
                servico.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // criando classe Service para separar a funcionalidade da conexao com o cliente, podendo atender
    // a mais de uma solicitação
    private static class Service extends Thread {
        Socket socket = null;

        public Service (Socket s) {
            socket = s;
        } // construtor

        @Override
        public void run() {
            try {
                BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

                String str = "";

                // contador para registrar fim de linha
                int recEndLine = 0;

                // header http da resposta
                String status = "HTTP/1.1 200 OK\r\n";
                String at1 = "Content-Type: text/html\r\n";
                String at2 = "Server: java\r\n";
                String blank = "\r\n";
                String html = "<html><body>web server vivo</body></html>";
                String response = status + at1 + at2 + blank + html + blank + blank;

                while (true) {
                    // lê os dados. read retorna um numero ascii e -1 quando não há mais nada para ser lido
                    int ch = bis.read();

                    // transforma o numero ascii em caractere e add em 'str'
                    str += (char)ch;

                    // toda linha que o browser manda é terminada pelo código ascii 10 (nova linha) e 13 (enter)
                    if ((ch==10) || (ch==13)) {
                        recEndLine++;
                        // duas linhas em branco marcam o final do request e da response
                        if (recEndLine==3){
                            bos.write(response.getBytes());
                            bos.flush();
                            socket.close();
                            return;
                        }
                        System.out.println(str);
                        str = "";

                    } else {
                        recEndLine=0;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
