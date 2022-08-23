/*
 * Testing code for the ASCIIConverter class.
 * 
 * Eric Wang
 * July 25, 2022
 * 
 */

import java.io.File;
import java.io.IOException;

public class ASCIITest {
    public static void main(String[] args){
        ASCIIConverter ascii = new ASCIIConverter();
        File f = new File("image.jpg"); // Replace with image to be converted
        //String s = "image.jpg";
        
        try {
            ascii.selectImage(f);
        } catch(IOException e){
            System.out.println(e);
        }
        
        // Alternate usage of selectImage()
        /*
        try {
            ascii.selectImage(s);
        } catch(IOException e){
            System.out.println(e);
        }
        */

        ascii.toASCII();
        ascii.toASCIIInverted();
    } 
}
