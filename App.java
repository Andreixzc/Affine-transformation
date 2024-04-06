import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class App {

    public static void main(String[] args) {
        String imagePath = "Arby_o.jpg";
        int[][] pixelMatrix = imageToMatrix(imagePath);
        matrixToImage(translate(pixelMatrix, 300, 300), "transladada.jpg");

    }

    public static int[][] imageToMatrix(String imagePath) {
        try {
            // Read the image from the given path
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);

            // Get the dimensions of the image
            int width = image.getWidth();
            int height = image.getHeight();

            // Create a 2D integer matrix to store pixel values
            int[][] matrix = new int[height][width];

            // Iterate through each pixel of the image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get the RGB value of the pixel
                    int rgb = image.getRGB(x, y);

                    // Extract individual color components (red, green, blue)
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Convert RGB to grayscale (if needed) and store in the matrix
                    // For simplicity, averaging the color components
                    int grayscale = (red + green + blue) / 3;

                    // Store the grayscale value in the matrix
                    matrix[y][x] = grayscale;
                }
            }

            return matrix;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if there's an error reading the image
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

    }

    public static void matrixToImage(int[][] matrix, String outputPath) {
        try {
            int height = matrix.length;
            int width = matrix[0].length;

            // Create a BufferedImage of TYPE_INT_RGB
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Set pixel values based on the input matrix
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Convert grayscale value to RGB color
                    int grayscale = matrix[y][x];
                    int rgb = (grayscale << 16) | (grayscale << 8) | grayscale;

                    // Set the pixel value in the image
                    image.setRGB(x, y, rgb);
                }
            }

            // Write the BufferedImage to a file
            File outputImageFile = new File(outputPath);
            ImageIO.write(image, "jpg", outputImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
