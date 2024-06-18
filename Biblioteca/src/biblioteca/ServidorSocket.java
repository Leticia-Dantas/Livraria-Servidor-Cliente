package teste;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class ServidorSocket {
    public static void main(String[] args) throws org.json.simple.parser.ParseException {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor aguardando conexões...");

            while (true) {
                
				try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
         
                    // Ver a opção do cliente
                    String opcao = in.readLine();

                    // Envia a resposta da solicitação do cliente
                    if ("1".equals(opcao)) {
                        out.println("obrigada");
                        JSONObject jSONObject;
                        JSONParser parser = new JSONParser();
                  
                        try {
                            jSONObject = (JSONObject) parser.parse(new FileReader("livros.json"));
                        	JSONArray livros = (JSONArray) jSONObject.get("livros");
                        	
                        	int indice = 0; // Inicialize o índice aqui

                            while (indice < livros.size()) {
                                JSONObject livro = (JSONObject) livros.get(indice);
                                System.out.println("-----------------------------------------");
                                System.out.println("Livro " + (indice + 1));
                                System.out.println("Nome do autor: " + livro.get("autor"));
                                System.out.println("Nome do livro: " + livro.get("titulo"));
                                System.out.println("Genero do livro: " + livro.get("genero"));
                                System.out.println("Numero de exemplares: " + livro.get("exemplares"));
                                System.out.println("-----------------------------------------");
                                indice++; // Incrementa o índice para o próximo livro
                            }
//                        	
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                
                    } else if ("2".equals(opcao)) {
                        out.println("Funcionou2");
                    } else if ("3".equals(opcao)){
                        out.println("Funcionou3");
                    }else if ("4".equals(opcao)) {
                        out.println("Funcionou4");
                    }else {
                        out.println("Opção inválida.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
