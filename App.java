import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class App {

    public static void main(String[] args) {
        String imagePath = "Arby_o.jpg";
        int[][] pixelMatrix = imageToMatrix(imagePath);
        matrixToImage(translate(pixelMatrix, 300, 300), "transladada.jpg");
        matrixToImage(resize(pixelMatrix, 1920, 1080), "resized.jpg");

    }

    public static int[][] imageToMatrix(String imagePath) {
        try {

            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);

            int width = image.getWidth();
            int height = image.getHeight();

            int[][] matrix = new int[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int rgb = image.getRGB(x, y);

                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    int grayscale = (red + green + blue) / 3;
                    matrix[y][x] = grayscale;
                }
            }

            return matrix;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[][] translate(int[][] originalMatrix, int x, int y) {
        int[][] translatedMatrix = new int[originalMatrix.length][originalMatrix[0].length];
        for (int i = 0; i < translatedMatrix.length; i++) {
            for (int j = 0; j < translatedMatrix.length; j++) {
                int newX = i + x;
                int newY = j + y;
                if (newX >= 0 && newX < originalMatrix.length && newY >= 0 && newY < originalMatrix[0].length) {
                    translatedMatrix[newX][newY] = originalMatrix[i][j];
                }
            }
        }
        return translatedMatrix;
    }

    public static int[][] resize(int[][] originalMatrix, int x, int y) {
        int originalHeight = originalMatrix.length;
        int originalWidth = originalMatrix[0].length;
        int[][] resizedMatrix = new int[y][x];

        double xScale = (double) originalWidth / x;
        double yScale = (double) originalHeight / y;
        for (int newY = 0; newY < y; newY++) {
            for (int newX = 0; newX < x; newX++) {
                int originalX = (int) Math.floor(newX * xScale);
                int originalY = (int) Math.floor(newY * yScale);
                originalX = Math.min(originalX, originalWidth - 1);
                originalY = Math.min(originalY, originalHeight - 1);
                resizedMatrix[newY][newX] = originalMatrix[originalY][originalX];
            }
        }

        return resizedMatrix;
    }

    public static void matrixToImage(int[][] matrix, String outputPath) {
        try {
            int height = matrix.length;
            int width = matrix[0].length;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int grayscale = matrix[y][x];
                    int rgb = (grayscale << 16) | (grayscale << 8) | grayscale;

                    image.setRGB(x, y, rgb);
                }
            }

            File outputImageFile = new File(outputPath);
            ImageIO.write(image, "jpg", outputImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
