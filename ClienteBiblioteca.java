package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteBiblioteca {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Selecione o tipo de consulta (1: Livros, 2: Alunos, 3: Empréstimos, 0: Sair): ");
            int opcao = scanner.nextInt();
            if (opcao == 0) break;

            System.out.println("Digite o ID para consulta: ");
            int id = scanner.nextInt();

            String resposta = "";
            try {
                switch (opcao) {
                    case 1 -> resposta = consultaServidor("localhost", 5000, String.valueOf(id));
                    case 2 -> resposta = consultaServidor("localhost", 5001, String.valueOf(id));
                    case 3 -> resposta = consultaServidor("localhost", 5002, String.valueOf(id));
                }
                System.out.println("Resposta do Servidor: " + resposta);
            } catch (IOException e) {
                System.out.println("Erro ao conectar ao servidor.");
            }
        }
        scanner.close();
    }

    private static String consultaServidor(String host, int porta, String mensagem) throws IOException {
        try (Socket socket = new Socket(host, porta);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(mensagem);
            return in.readLine();
        }
    }
}
