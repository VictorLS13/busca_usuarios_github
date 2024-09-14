import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        System.out.println("Digite um nome de um usuario: ");
        Scanner leitura = new Scanner(System.in);
        String usuario = leitura.nextLine();
        String endereco = "https://api.github.com/users/";


        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco + usuario))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                String mensagem = "Erro 404: Usuario não encontrado.";
                throw new ErroConsultaGitHubException(mensagem);
            }

            String json = response.body();
            System.out.println(json);

        } catch (IOException | InterruptedException e) {
            System.out.println("Opss… Houve um erro durante a consulta à API do GitHub.");
            e.printStackTrace();

        } catch (ErroConsultaGitHubException e){
            System.out.println(e);
        }
    }
}