package socketdemoclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketDemoClient {
    public static void main(String[] args) {
        try {
            // cliente cria uma conexao pro servidor com a classe Socket,
            // caracterizado por uma máquina (localhost) e uma porta (10000)
            Socket s = new Socket("localhost", 10000);
            BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
            String str = "Olá mundaooooo!";
            while (true) {
                for (int i=0; i<str.length(); i++) {
                    // método charAt pega o caractere na posição indicada
                    byte b = (byte)str.charAt(i);
                    // escreve algo no socket
                    bos.write(b);
                    bos.flush();
                    // lê o que foi retornado pelo servidor
                    char ch = (char)bis.read();
                    System.out.println("recebido no client " + ch);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
