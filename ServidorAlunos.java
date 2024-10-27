package org.example;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ServidorAlunos {
    private static HashMap<Integer, String> registrosAlunos = new HashMap<>();

    public static void main(String[] args) {

        registrosAlunos.put(1001, "Aluno: Valeria Soares");
        registrosAlunos.put(1002, "Aluno: Thiago Souza");

        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            System.out.println("Servidor de Alunos rodando na porta 5001...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    int idAluno = Integer.parseInt(inputLine);
                    String dadosAluno = registrosAlunos.getOrDefault(idAluno, "Aluno não encontrado.");
                    out.println(dadosAluno);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
