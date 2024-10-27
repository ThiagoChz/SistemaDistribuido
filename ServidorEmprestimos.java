package org.example;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ServidorEmprestimos {
    private static HashMap<Integer, String> registrosEmprestimos = new HashMap<>();

    public static void main(String[] args) {

        registrosEmprestimos.put(1, "Livro ID: 1 - Aluno ID: 1001 - Data: 01/10/2024");
        registrosEmprestimos.put(2, "Livro ID: 2 - Aluno ID: 1002 - Data: 15/10/2024");

        try (ServerSocket serverSocket = new ServerSocket(5002)) {
            System.out.println("Servidor de Empréstimos rodando na porta 5002...");
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
                    int idEmprestimo = Integer.parseInt(inputLine);
                    String dadosEmprestimo = registrosEmprestimos.getOrDefault(idEmprestimo, "Empréstimo não encontrado.");
                    out.println(dadosEmprestimo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
