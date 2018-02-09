/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric_authentication_keystroke;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author acer
 */
public class KeyPattern implements Serializable{
   //hash map to keep the average time to press each key 
   private HashMap<Integer, Long> average_key_delay;
   //hash map to store the average time to press consecutive keys.
   private HashMap<TwoKey, Long> average_two_key_delay;
   

   
   

    public KeyPattern() {
        this.average_key_delay = new HashMap<>();
        this.average_two_key_delay = new HashMap<>();
    }
    
    //set getters and setters to access the varaibles.
    public HashMap<Integer, Long> get_average_key_delay() {
        return average_key_delay;
    }
    public void set_average_key_delay(HashMap<Integer, Long> average_key_delay) {
        this.average_key_delay = average_key_delay;
    }
    public HashMap<TwoKey, Long> get_average_two_key_delay() {
       return average_two_key_delay;
    }
    public void set_average_two_key_delay(HashMap<TwoKey, Long> average_two_key_delay) {
        this.average_two_key_delay = average_two_key_delay;
    }


  
    
}
