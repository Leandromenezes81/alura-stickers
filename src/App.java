import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Consumo de Dados do iMDB");

        // Consumo de Dados do iMDB

        // Fazer uma conexão HTTP para buscar os Top 250 Filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        
        URI adress = URI.create(url);

        var client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(adress).GET().build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        String body = response.body();

        // Extrair os dados necessários (Título, Poster, Classificação)

        // Método para ssociação de chave - valor

        JsonParser parser = new JsonParser();
        List<Map<String, String>> listOfMovies = parser.parse(body);
        
        // Exibir e manipular os dados.

        System.out.println("Os " + listOfMovies.size() + " mais assistidos:");
        
        var stickerGenerator = new StickerGenerator();

        for (Map<String,String> film : listOfMovies) {
            String imageUrl = film.get("image");
            String title = film.get("title");

            InputStream inputStream = new URL(imageUrl).openStream();
            String archiveName = title;
            stickerGenerator.CreateImage(inputStream, archiveName);

            System.out.println("\u001b[31m\u001b[1m Title:\u001b[m " + title);
            System.out.println("\u001b[31m\u001b[1m Poster:\u001b[m " + imageUrl);
            System.out.println("\u001b[31m\u001b[1m Ranking:\u001b[m " + film.get("imDbRating"));
            
            double ranking = Double.parseDouble(film.get("imDbRating"));
            int numberOfStars = (int) ranking;            
            for (int i = 1; i <= numberOfStars; i++) {
                System.out.print("\u2B50");
            }

            System.out.println("\n");
        }
    }
}
