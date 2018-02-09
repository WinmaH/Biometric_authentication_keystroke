/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric_authentication_keystroke;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author acer
 */
public class FileHandler {
    
    
    public static void saveKeyPattern(KeyPattern keyPattern) {
        try (
                FileOutputStream file = new FileOutputStream("pattern.ser");
                ObjectOutputStream obj = new ObjectOutputStream(file)) {
            
                obj.writeObject(keyPattern);
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
    
    
        public static KeyPattern loadKeyPattern() {
        try (
                FileInputStream file = new FileInputStream("pattern.ser");
                ObjectInputStream obj = new ObjectInputStream(file)) {
            
            KeyPattern pattern = (KeyPattern) obj.readObject();
            return pattern;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
        
        
    public static boolean first_file() {
        File f = new File("pattern.ser");
        return !(f.exists() && !f.isDirectory());
    }
    
}
