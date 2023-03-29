import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickerGenerator {

    public void CreateImage(InputStream inputStream, String archiveName) throws Exception{

        // Leitura da imagem
        // InputStream inputStream = new FileInputStream(new File("src/images/entry/TopMovies_3.jpg"));
        // InputStream inputStream = 
        //     new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_3.jpg")
        //         .openStream();
        BufferedImage originalImage = ImageIO.read(inputStream);

        // Cria nova imagem em memória com transparência e com tamanho novo
        int largura = originalImage.getWidth();
        int altura = originalImage.getHeight();
        int novaAltura = altura + 200;
        BufferedImage newImage = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // Copiar a imagem original para noa imagem (em memória)
        Graphics2D graphics = (Graphics2D)newImage.getGraphics();
        graphics.drawImage(originalImage, 0, 0, null);

        // Configurar fonte
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 128);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        // Escrever uma frase na nova imagem
        graphics.drawString("BEGINS", 465, novaAltura - 50);

        // Escrever a nova imagem em um arquivo
        ImageIO.write(newImage, "png", new File("src/images/output/" + archiveName + ".png"));
    }
}
