package biblioteca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ServidorSocket {
    private static final String ARQUIVO_JSON = "livros.json";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor aguardando conexões...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    //Recebe o que o cliente escolheu 
                    String opcao = in.readLine();

                    // Envia a resposta para cliente
                    if ("1".equals(opcao)) {
                        List<Livros> livros = lerLivrosDoJSON();
                        enviarLivrosParaCliente(livros, out);
                    } else if ("2".equals(opcao)) {
                        out.println("Digite o nome do livro que deseja alugar:");
                        String livroAlugado = in.readLine(); // Lê o nome do livro do cliente

                        JSONObject livroAlugadoObj = null;
                        int indiceLivro = -1;

                        // Procura o livro no arquivo JSON
                        JSONParser parser = new JSONParser();
                        try {
                            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(ARQUIVO_JSON));
                            JSONArray livros = (JSONArray) jsonObject.get("livros");

                            // Encontra o livro pelo nome
                            for (int i = 0; i < livros.size(); i++) {
                                JSONObject livro = (JSONObject) livros.get(i);
                                String titulo = (String) livro.get("titulo");
                                if (titulo.equalsIgnoreCase(livroAlugado)) {
                                    livroAlugadoObj = livro;
                                    indiceLivro = i;
                                    break;
                                }
                            }

                            if (livroAlugadoObj != null) {
                                // Atualiza o número de exemplares se tiver o livro 
                                int exemplares = Integer.parseInt(livroAlugadoObj.get("exemplares").toString());
                                if (exemplares > 0) {
                                    exemplares--; // Diminui a quantidade de exemplares
                                    livroAlugadoObj.put("exemplares", exemplares); // Atualiza no JSON(Objeto Json)

                                    // Coloca a nova informação no arquivo JSON
                                    livros.set(indiceLivro, livroAlugadoObj);
                                    jsonObject.put("livros", livros);

                                    FileWriter fileWriter = new FileWriter(ARQUIVO_JSON);
                                    fileWriter.write(jsonObject.toJSONString());
                                    fileWriter.flush();
                                    fileWriter.close();

                                    out.println("Livro alugado! Total de exemplares restante: " + exemplares);
                                } else {
                                    out.println("Desculpe, não há exemplares disponíveis desse livro.");
                                }
                            } else {
                                out.println("Livro não encontrado.");
                            }

                        } catch (IOException | ParseException e) {
                            e.printStackTrace();
                        }
                    } else if ("3".equals(opcao)) {
                        out.println("Digite o nome do livro que deseja devolver:");
                        String livroDevolvido = in.readLine(); // Lê o nome do livro do cliente

                        JSONObject livroDevolvidoObj = null;
                        int indiceLivro = -1;

                        // Procura o livro no arquivo JSON
                        JSONParser parser = new JSONParser();
                        try {
                            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(ARQUIVO_JSON));
                            JSONArray livros = (JSONArray) jsonObject.get("livros");

                            // Encontra o livro pelo nome
                            for (int i = 0; i < livros.size(); i++) {
                                JSONObject livro = (JSONObject) livros.get(i);
                                String titulo = (String) livro.get("titulo");
                                if (titulo.equalsIgnoreCase(livroDevolvido)) {
                                    livroDevolvidoObj = livro;
                                    indiceLivro = i;
                                    break;
                                }
                            }

                            if (livroDevolvidoObj != null) {
                                // Adiciona o número de exemplar no livro
                                int exemplares = Integer.parseInt(livroDevolvidoObj.get("exemplares").toString());
                                exemplares++; // Aumenta a quantidade de exemplares
                                livroDevolvidoObj.put("exemplares", exemplares); // Atualiza no JSON (JSON objeto)

                                // Coloca a nova informação no arquivo JSON
                                livros.set(indiceLivro, livroDevolvidoObj);
                                jsonObject.put("livros", livros);

                                FileWriter fileWriter = new FileWriter(ARQUIVO_JSON);
                                fileWriter.write(jsonObject.toJSONString());
                                fileWriter.flush();
                                fileWriter.close();

                                out.println("Livro devolvido! Total de exemplares atualizado: " + exemplares);
                            } else {
                                out.println("Livro não encontrado.");
                            }

                        } catch (IOException | ParseException e) {
                            e.printStackTrace();
                        }
                    } else if ("4".equals(opcao)) {
                        // Pede ao cliente os dados do novo livro
                        out.println("Digite o título do livro:");
                        String titulo = in.readLine();
                        out.println("Digite o autor do livro:");
                        String autor = in.readLine();
                        out.println("Digite o gênero do livro:");
                        String genero = in.readLine();
                        out.println("Digite o número de exemplares do livro:");
                        int exemplares = Integer.parseInt(in.readLine());

                        // Cria um novo objeto Livros com os dados fornecidos
                        Livros novoLivro = new Livros(titulo, autor, genero, exemplares);

                        // Adiciona o novo livro ao arquivo JSON
                        adicionarLivroAoJSON(novoLivro);

                        out.println("Livro adicionado com sucesso:");
                        out.println(novoLivro.toString());
                    } else {
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

    private static List<Livros> lerLivrosDoJSON() {
        List<Livros> listaLivros = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray livrosJSON = (JSONArray) jsonObject.get("livros");

            for (Object obj : livrosJSON) {
                JSONObject livroJSON = (JSONObject) obj;
                String titulo = (String) livroJSON.get("titulo");
                String autor = (String) livroJSON.get("autor");
                String genero = (String) livroJSON.get("genero");
                long exemplares = (long) livroJSON.get("exemplares");

                Livros livro = new Livros(titulo, autor, genero, (int) exemplares);
                listaLivros.add(livro);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return listaLivros;
    }

    private static void enviarLivrosParaCliente(List<Livros> livros, PrintWriter out) {
        for (Livros livro : livros) {
            out.println("Título: " + livro.getTitulo());
            out.println("Autor: " + livro.getAutor());
            out.println("Gênero: " + livro.getGenero());
            out.println("Exemplares: " + livro.getExemplares());
            out.println("-----------------------------------------");
        }
    }

    private static void adicionarLivroAoJSON(Livros livro) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(ARQUIVO_JSON));
            JSONArray livros = (JSONArray) jsonObject.get("livros");

            // Cria um novo objeto JSON para o novo livro
            JSONObject novoLivroJSON = new JSONObject();
            novoLivroJSON.put("titulo", livro.getTitulo());
            novoLivroJSON.put("autor", livro.getAutor());
            novoLivroJSON.put("genero", livro.getGenero());
            novoLivroJSON.put("exemplares", livro.getExemplares());

            // Adiciona o novo livro ao array de livros
            livros.add(novoLivroJSON);

            // Atualiza o arquivo JSON com o novo livro
            FileWriter fileWriter = new FileWriter(ARQUIVO_JSON);
            jsonObject.put("livros", livros);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
