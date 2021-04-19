package socketdemoserver;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDemoServer {

    public static void main(String[] args) {
        try {
            // instanciando um ServerSocket na porta 10000
            ServerSocket ss = new ServerSocket(10000);

            // criando um loop para o servidor ficar sempre rodando - faça para sempre
            while(true) {
                // ao receber conexão de um cliente, método accept retorna um socket
                Socket s = ss.accept();
                // canais (buferizados) de transferencia de dados de entrada e de saida
                BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
                BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
                // inicio do processamento dos dados pelo servidor
                while (true) {
                    // lê os dados. read retorna um caractere ou -1 quando não há mais nada para ser lido
                    int ch = bis.read();
                    // transforma ch em char no print line
                    System.out.println("recebido no servidor " + (char)ch);
                    // servidor faz apenas um echo dos dados recebidos com método write do output stream
                    bos.write((byte)ch);
                    // flush para empurrar os dados para o cliente
                    bos.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
