import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickerGenerator {

    public void CreateImage(InputStream imageFilm, String archiveName, String stickerText, InputStream stickerImageRating) throws Exception{

        // Leitura da imagem
        // InputStream inputStream = new FileInputStream(new File("src/images/entry/TopMovies_3.jpg"));
        // InputStream inputStream = 
        //     new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_3.jpg")
        //         .openStream();
        BufferedImage originalImage = ImageIO.read(imageFilm);
        BufferedImage overlayImage = ImageIO.read(stickerImageRating);

        // Cria nova imagem em memória com transparência e com tamanho novo
        int largura = originalImage.getWidth();
        int altura = originalImage.getHeight();
        int novaAltura = altura + 200;
        BufferedImage newImage = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // Copiar a imagem original para noa imagem (em memória)
        Graphics2D graphics = (Graphics2D)newImage.getGraphics();
        graphics.drawImage(originalImage, 0, 0, null);

        int overlayImagePosition = novaAltura - overlayImage.getHeight();
        graphics.drawImage(overlayImage, 0, overlayImagePosition, null);

        // Configurar fonte
        var font = new Font("Impact", Font.BOLD, 80);
        graphics.setColor(Color.WHITE);
        graphics.setFont(font);

        // Escrever uma frase na nova imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D rectangle2d = fontMetrics.getStringBounds(stickerText, graphics);
        int widthText = (int)rectangle2d.getWidth();
        int textPositionX = (largura - widthText)/2;
        int heightText = (int)rectangle2d.getHeight();
        int textPositionY = novaAltura - (100 - (heightText/2));
        graphics.drawString(stickerText, textPositionX, textPositionY);

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(stickerText, font, fontRenderContext);
        
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(textPositionX, textPositionY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);

        // Escrever a nova imagem em um arquivo
        ImageIO.write(newImage, "png", new File(archiveName + ".png"));
    }
}
