package socketdemoservethreaded;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// Servidor com threads para atender múltiplas solicitações de clientes
public class SocketDemoServerThreaded {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
