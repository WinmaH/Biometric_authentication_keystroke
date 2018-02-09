/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric_authentication_keystroke;

import javax.swing.JOptionPane;

/**
 *
 * @author acer
 */
public class MatchFeature {
   
    public static final double DEFINED_MATCH=0.65;
    public static final int DEFINED_KEY_DELAY =25;
    public static final int DEFINED_TWO_KEY_DELAY = 30;
    
    

    public static boolean match( KeyPattern recent,KeyPattern original) {
        
        
        
        int single_key_match = 0;
        int single_key_mismatch = 0;

        int two_key_match = 0;
        int two_key_mismatch = 0;

        for (int key : recent.get_average_key_delay().keySet()) {
           // System.out.println(key);
                if (original.get_average_key_delay().containsKey(key)) {
               
                if (Math.abs(original.get_average_key_delay().get(key) - recent.get_average_key_delay().get(key)) < DEFINED_KEY_DELAY) {
                    single_key_match++;
                } else {
                    single_key_mismatch++;
                }
            } 
        }
        

        for (TwoKey two_key : recent.get_average_two_key_delay().keySet()) {
            
           // System.out.println(original.get_average_two_key_delay().keySet());
            if (original.get_average_two_key_delay().containsKey(two_key)) {
               // System.out.println("contains");
                if (Math.abs(original.get_average_two_key_delay().get(two_key) - recent.get_average_two_key_delay().get(two_key)) < DEFINED_TWO_KEY_DELAY) {
                    two_key_match++;
                } else {
                    two_key_mismatch++;
                }
            } 
        }
        
        //System.out.println(single_key_match);
        //System.out.println(single_key_mismatch);
        //System.out.println(two_key_match);
        //System.out.println(two_key_mismatch);
        
        //algorithm

        double match_level = 1.5 * ((single_key_match / (single_key_mismatch * 1.0 + single_key_match))
                + (two_key_match / (two_key_match * 1.0 + two_key_mismatch))) / 2.5;
        

         boolean match= match_level >= DEFINED_MATCH;
         System.out.println("Your match level");
         System.out.println(match_level);
          System.out.println("Expected match level");
          System.out.println(DEFINED_MATCH);
          
        // boolean details=(recent.getPassword() == null ? original.getPassword() == null : recent.getPassword().equals(original.getPassword())) && (recent.getUsername() == null ? original.getUsername() == null : recent.getUsername().equals(original.getUsername()));
         //System.out.println(match_level);
         //System.out.println(match);
         //System.out.println(DEFINED_MATCH);
         return match;
    }
    
}
