/* 
 * The ASCIIConverter class converts an image file into a greyscale ASCII replication.
 * 
 * Eric Wang
 * July 25, 2022
 *  
 */

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.FileWriter;
import java.lang.StringBuilder;

public class ASCIIConverter {
    File inputFile;
    BufferedImage img;
    final char[] asciiTable = {'$', '@', '%', '&', '#', 'A', 'V', 'F', 'j', '7', '{', 'i', '?', '*', 'L', '<', '(', '/', '^', '!', '~', ':', '-', '.', ' '}; // 25 characters
    final char[] asciiTableInverted = {' ', '.', '-', ':', '~', '!', '^', '/', '(', '<', 'L', '*', '?', 'i', '{', '7', 'j', 'F', 'V', 'A', '#', '&', '%', '@', '$'};
    final int asciiKey = (256 / asciiTable.length) + 1; // RBG color / asciiKey = corresponding index in asciiTable

    public ASCIIConverter(){
        inputFile = null;
        img = null;
    }

    // Select an image by giving the ASCIIConverter a file or the location to the file in directory
    public void selectImage(File f) throws IOException {
        try{
            inputFile = f;
            img = ImageIO.read(inputFile);
            System.out.println("Image selected!");
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public void selectImage(String fileLocation) throws IOException {
        try{
            inputFile = new File(fileLocation);
            img = ImageIO.read(inputFile);
            System.out.println("Image selected!");
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public void writeImage(){
        if(img == null){
            System.out.println("No image selected!");
            return;
        }

        try{
            File newFile = new File("Copy of " + inputFile.getName());
            ImageIO.write(img, "jpg", newFile);
            System.out.println("Image created!");

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // Converts the selected image to a greyscale version.
    public void greyscale(){
        if(img == null){
            System.out.println("No image selected!");
            return;
        }

        for(int r = 0; r < img.getHeight(); r++){
            for(int c = 0; c < img.getWidth(); c++){
                int color = img.getRGB(c, r);
                int red = (color >> 16) & 0xff;
                int grn = (color >> 8) & 0xff;
                int blu = (color) & 0xff;
                int avg = (red + grn + blu) / 3;

                color = color & 0xff000000;
                color = color | (avg << 16) | (avg << 8) | (avg);

                img.setRGB(c, r, color);
            }
        }

        System.out.println("Greyscale complete!");
    }

    // Converts the selected image to an ASCII image, prints it out, and saves the ASCII text to a new file.
    public void toASCII(){
        if(img == null){
            System.out.println("No image selected!");
            return;
        }

        StringBuilder s = new StringBuilder();

        // Condense full image down to a reasonable size (approximately 100px by 100px)
        // If one dimension is larger than the other, then the smaller dimension becomes 100px 
        // and the larger dimension is shortened to maintain aspect ratio
        int scale;

        if(img.getHeight() < 100 || img.getWidth() < 100){
            // If either dimension is <100px, do not condense
            scale = 1;
        } else if(img.getHeight() > img.getWidth()){
            scale = (int) Math.round(img.getWidth() / 100.0);
        } else {
            scale = (int) Math.round(img.getHeight() / 100.0);
        }

        for(int y = 0; y < img.getHeight(); y+=scale){
            for(int x = 0; x < img.getWidth(); x+=scale){
                int color = img.getRGB(x, y);
                int red = (color >> 16) & 0xff;
                int grn = (color >> 8) & 0xff;
                int blu = (color) & 0xff;
                int avg = (red + grn + blu) / 3;

                s.append(asciiTable[avg/asciiKey]);
                s.append("  ");
            }
            s.append("\n");
        }

        try {
            FileWriter newFile = new FileWriter("ASCII of image");
            newFile.write(s.toString());
            newFile.close();
            System.out.println("ASCII created!");
        } catch (IOException e){
            System.out.println(e);
        }

    }

    // Converts the selected image to an ASCII image, prints it out, and saves the ASCII text to a new file.
    // This version inverts the greyscale (light to dark, dark to light)
    public void toASCIIInverted(){
        if(img == null){
            System.out.println("No image selected!");
            return;
        }

        StringBuilder s = new StringBuilder();

        // Condense full image down to a reasonable size (approximately 100px by 100px)
        // If one dimension is larger than the other, then the smaller dimension becomes 100px 
        // and the larger dimension is shortened to maintain aspect ratio
        int scale;

        if(img.getHeight() < 100 || img.getWidth() < 100){
            // If either dimension is <100px, do not condense
            scale = 1;
        } else if(img.getHeight() > img.getWidth()){
            scale = (int) Math.round(img.getWidth() / 100.0);
        } else {
            scale = (int) Math.round(img.getHeight() / 100.0);
        }

        for(int y = 0; y < img.getHeight(); y+=scale){
            for(int x = 0; x < img.getWidth(); x+=scale){
                int color = img.getRGB(x, y);
                int red = (color >> 16) & 0xff;
                int grn = (color >> 8) & 0xff;
                int blu = (color) & 0xff;
                int avg = (red + grn + blu) / 3;

                s.append(asciiTableInverted[avg/asciiKey]);
                s.append("  ");
            }
            s.append("\n");
        }

        try {
            FileWriter newFile = new FileWriter("ASCII of image (Inverted)");
            newFile.write(s.toString());
            newFile.close();
            System.out.println("ASCII created!");
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
