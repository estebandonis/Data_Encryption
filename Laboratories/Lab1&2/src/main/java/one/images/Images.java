package one.images;

import one.cypherFunctions.AsciiFunctions;
import one.cypherFunctions.Base64Functions;
import one.cypherFunctions.BinaryFunctions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class Images {
    /***
     * XOR an image with a key
     * @param imageFile File
     * @param key String
     * @param outputPath String
     * @throws IOException file handling exception
     */
    public static void xorOperationImageKey(File imageFile, String key, String outputPath) throws IOException {
        /* Transform Image to Bits */
        // Read the image file into a byte array
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        // Encode the byte array into a Base64 string
        String base64String = Base64.getEncoder().encodeToString(imageBytes);

        // Convert the Base64 string to a binary string
        String binaryImage = Base64Functions.base64ToBinary(base64String);

        /* Transform Key to Bits */
        // Convert the key to a binary string
        String binaryKey = AsciiFunctions.asciiToBinary(key);

        // Extend the key to match the length of the image
        String binaryKeyExtended = BinaryFunctions.fillString(binaryKey, binaryImage.length());

        // Perform XOR operation
        String imageXor = BinaryFunctions.xorBinary(binaryImage, binaryKeyExtended);

        assert imageXor != null;
        // Convert the XOR result to a Base64 string
        String base64ImageXor = Base64Functions.binaryToBase64(imageXor);

        // Decode the Base64 string to get the byte array
        byte[] imageBytes1 = Base64.getDecoder().decode(base64ImageXor);

        // Write the byte array to a PNG file
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            // Write the byte array to the file
            fos.write(imageBytes1);
            // Print success message
            System.out.println("PNG file created successfully.");
        } catch (IOException e) {
            // Print error message
            System.out.println("An error occurred." + e.getMessage());
        }
    }


    /***
     * XOR two images
     * @param imageFileOne File
     * @param imageFileTwo File
     * @param outputPath String
     * @throws IOException file handling exception
     */
    public static void xorOperationImages (File imageFileOne, File imageFileTwo, String outputPath) throws IOException {
        // Read the images
        BufferedImage imageOne = ImageIO.read(imageFileOne);
        BufferedImage imageTwo = ImageIO.read(imageFileTwo);

        // Verify that dimensions match
        if (imageOne.getWidth() != imageTwo.getWidth() || imageOne.getHeight() != imageTwo.getHeight()) {
            throw new IllegalArgumentException("Images must have the same dimensions");
        }

        // Get images dimensions
        int width = imageOne.getWidth();
        int height = imageOne.getHeight();

        // Create new image for result
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Perform XOR operation pixel by pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get RGB values for both images
                int pixelOne = imageOne.getRGB(x, y);
                int pixelTwo = imageTwo.getRGB(x, y);

                // Get the RGB components for each pixel in the first image
                int r1 = (pixelOne >> 16) & 0xff;
                String r1Binary = BinaryFunctions.numberToBinary(r1);
                if (r1Binary.length() < 8) {
                    r1Binary = "0".repeat(8 - r1Binary.length()) + r1Binary;
                }

                int g1 = (pixelOne >> 8) & 0xff;
                String g1Binary = BinaryFunctions.numberToBinary(g1);
                if (g1Binary.length() < 8) {
                    g1Binary = "0".repeat(8 - g1Binary.length()) + g1Binary;
                }

                int b1 = pixelOne & 0xff;
                String b1Binary = BinaryFunctions.numberToBinary(b1);
                if (b1Binary.length() < 8) {
                    b1Binary = "0".repeat(8 - b1Binary.length()) + b1Binary;
                }


                // Get the RGB components for each pixel in the second image
                int r2 = (pixelTwo >> 16) & 0xff;
                String r2Binary = BinaryFunctions.numberToBinary(r2);
                if (r2Binary.length() < 8) {
                    r2Binary = "0".repeat(8 - r2Binary.length()) + r2Binary;
                }

                int g2 = (pixelTwo >> 8) & 0xff;
                String g2Binary = BinaryFunctions.numberToBinary(g2);
                if (g2Binary.length() < 8) {
                    g2Binary = "0".repeat(8 - g2Binary.length()) + g2Binary;
                }

                int b2 = pixelTwo & 0xff;
                String b2Binary = BinaryFunctions.numberToBinary(b2);
                if (b2Binary.length() < 8) {
                    b2Binary = "0".repeat(8 - b2Binary.length()) + b2Binary;
                }

                // Perform XOR operation on RGB components and convert the resulting binary string to integer
                String rBinary = BinaryFunctions.xorBinary(r1Binary, r2Binary);
                int rBinaryInt = BinaryFunctions.binaryToNumber(rBinary);
                String gBinary = BinaryFunctions.xorBinary(g1Binary, g2Binary);
                int gBinaryInt = BinaryFunctions.binaryToNumber(gBinary);
                String bBinary = BinaryFunctions.xorBinary(b1Binary, b2Binary);
                int bBinaryInt = BinaryFunctions.binaryToNumber(bBinary);

                // Combine components back into RGB
                int resultPixel = (rBinaryInt << 16) | (gBinaryInt << 8) | bBinaryInt;
                // And we add them to the result image
                resultImage.setRGB(x, y, resultPixel);
            }
        }

        // Save the result
        ImageIO.write(resultImage, "jpg", new File(outputPath));
        System.out.println("Merged Image generated successfully as: " + outputPath);
    }
}
