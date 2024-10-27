package org.example;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ServidorLivros {
    private static HashMap<Integer, String> catalogoLivros = new HashMap<>();

    public static void main(String[] args) {
        catalogoLivros.put(1, "Java POO - Author: Thiago Souza - 2023");
        catalogoLivros.put(2, "Logica e Algoritimos - Author: Valeria Soares - 2022");

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor de Livros rodando na porta 5000...");
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
                    int idLivro = Integer.parseInt(inputLine);
                    String descricao = catalogoLivros.getOrDefault(idLivro, "Livro não encontrado.");
                    out.println(descricao);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
