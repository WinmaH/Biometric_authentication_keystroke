/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric_authentication_keystroke;

import java.util.HashMap;

/**
 *
 * @author acer
 */
public class GetFeature {
    
    
     public static KeyPattern extractFeatures(GetEvent get_event) {
        HashMap<Integer, Long> average_key_duration = new HashMap<>();
        
        //
        HashMap<TwoKey, Long> average_two_key_delay = new HashMap<>();

        
        //take all the key value pairs 
        for (int key : get_event.get_all_keys_count().keySet()) {
            //System.out.println(key);
            average_key_duration.put(key, get_event.get_all_keys_time().get(key) / get_event.get_all_keys_count().get(key));
        }
        for (int key1 : get_event.get_two_key_count().keySet()) {
           // System.out.println(key1);
            for (Integer key2 : get_event.get_two_key_count().get(key1).keySet()) {
               // System.out.println(key2);
                TwoKey tw = new TwoKey(key1, key2);
              
                average_two_key_delay.put(tw, get_event.get_two_key_delay().get(key1).get(key2) / get_event.get_two_key_count().get(key1).get(key2));
            }
        }
        

        KeyPattern pattern = new KeyPattern();
        pattern.set_average_two_key_delay(average_two_key_delay);
        pattern.set_average_key_delay(average_key_duration);
        return pattern;
    }
    
}
