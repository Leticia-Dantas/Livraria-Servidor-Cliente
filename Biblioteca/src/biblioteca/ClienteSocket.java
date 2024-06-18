package biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteSocket {
    public static void main(String[] args) {
        try {
            // Criação do socket e conexão com o servidor.
            Socket socket = new Socket("localhost", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Exibe as opções para o usuário
            System.out.println("O que deseja fazer? Escolha um número e pressione Enter:");
            System.out.println("1 - Listagem de livros");
            System.out.println("2 - Alugar livro");
            System.out.println("3 - Devolver livro");
            System.out.println("4 - Cadastrar livro");

            // Lê a escolha do usuário
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String input = userInput.readLine();

            // Envia a escolha para o servidor
            out.println(input);

            switch (input) {
                case "1":
                    // Recebe e imprime a listagem de livros enviada pelo servidor
                    String linha;
                    while ((linha = in.readLine()) != null) {
                        System.out.println(linha);
                    }
                    break;
                case "2":
                    // Alugar livro
                    System.out.println(in.readLine()); 
                    String nomeLivro = userInput.readLine();
                    out.println(nomeLivro); 
                    // Recebe a resposta do servidor
                    String respostaAluguel = in.readLine();
                    System.out.println("Resposta do servidor: " + respostaAluguel);
                    break;
                case "3":
                    // Devolver livro
                    System.out.println(in.readLine()); 
                    String nomeLivroDevolvido = userInput.readLine();
                    out.println(nomeLivroDevolvido); 

                    // Recebe a resposta do servidor
                    String respostaDevolucao = in.readLine();
                    System.out.println("Resposta do servidor: " + respostaDevolucao);
                    break;
                case "4":
                    // Cadastrar livro
                    System.out.println("Digite o título do novo livro:");
                    String tituloNovoLivro = userInput.readLine();
                    out.println(tituloNovoLivro);

                    System.out.println("Digite o autor do novo livro:");
                    String autorNovoLivro = userInput.readLine();
                    out.println(autorNovoLivro);

                    System.out.println("Digite o gênero do novo livro:");
                    String generoNovoLivro = userInput.readLine();
                    out.println(generoNovoLivro);

                    System.out.println("Digite o número de exemplares do novo livro:");
                    String exemplaresNovoLivro = userInput.readLine();
                    out.println(exemplaresNovoLivro);
                    
                    System.out.println("Livro cadastrado.");
                    System.out.println("Olhe ele no arquivo Json.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            // Fecha a conexão com o servidor
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
