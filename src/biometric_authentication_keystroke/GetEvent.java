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
public class GetEvent {
    
    // get the details about the key pressed last and track the time it was released.
    
    private int final_key;
    private long final_key_release_time;
    
    //to check whether user overlaps keys when pressinga
    private boolean key_overlapped;
    //
    private int final_backup_key;
    
    //temporary track the currently pressed keys and the time durations.
    private final HashMap<Integer, Long> keys_pressed;
    
    //keeps track of the all pressed keys and their duration.
    private final HashMap<Integer, Long> all_keys_time;
    
    // keeps track of all the keys pressed.
    private final HashMap<Integer, Integer> all_keys_count;
     
    //keeps track of the delay between two keys
    private  final HashMap<Integer, HashMap<Integer, Long>> two_key_delay;
    
    //
    private final HashMap<Integer, HashMap<Integer, Integer>> two_key_count;
    
    
    
     public GetEvent() {
        final_key = -1;
        final_key_release_time = -1;
        key_overlapped = false;
        final_backup_key = -1;
        keys_pressed= new HashMap<>();
        all_keys_time = new HashMap<>();
        all_keys_count = new HashMap<>();
        two_key_delay = new HashMap<>();
        two_key_count = new HashMap<>();
    }
     
     public HashMap<Integer, Long> get_all_keys_time(){
         return all_keys_time;
     }
     
      public HashMap<Integer, Integer> get_all_keys_count(){
         return all_keys_count;
     }
      
      public HashMap<Integer,HashMap<Integer, Long>> get_two_key_delay(){
          return two_key_delay;
      }
      
      public HashMap<Integer, HashMap<Integer, Integer>> get_two_key_count(){
          return two_key_count;
      }
      
      
       public void key_press(java.awt.event.KeyEvent keyEvent) {
           
        //takes the current time of the computer to a variable
           
        Long pressed_time = System.currentTimeMillis();
        int new_key = keyEvent.getKeyCode();

        // System.out.println(keyEvent.paramString());
        
            // A key must be released to be pressed again; So,
            // pressedKeys will never have key code. 
            keys_pressed.put(new_key, pressed_time);

            // not the first time
            // if not the pressed keys contain the previous key value it is not released yet. 
            if (final_key != -1 && !keys_pressed.containsKey(final_key)) {
                //calculate the duration of pressing a key
                long duration = pressed_time - final_key_release_time;
               // System.out.println(duration);

                if (two_key_count.containsKey(final_key)) {
                    
                    if (two_key_count.get(final_key).containsKey(new_key)) {
                       two_key_delay.get(final_key).put(new_key, two_key_delay.get(final_key).get(new_key) + duration);
                       two_key_count.get(final_key).put(new_key, two_key_count.get(final_key).get(new_key) + 1);
                    } else {
                        two_key_delay.get(final_key).put(new_key, duration);
                        two_key_count.get(final_key).put(new_key, 1);
                    }
                } else {
                    HashMap<Integer, Long> secondGraphDelay = new HashMap<>();
                    secondGraphDelay.put(new_key, duration);
                    two_key_delay.put(final_key, secondGraphDelay);
                    HashMap<Integer, Integer> secondGraphCount = new HashMap<>();
                    secondGraphCount.put(new_key, 1);
                    two_key_count.put(final_key, secondGraphCount);
                }
            } else if (final_key != 1) {
                final_backup_key = final_key; 
                key_overlapped = true;
            }
            // set the last pressed key to the present key
            final_key = new_key;
        

    }
       
       
public void key_release(java.awt.event.KeyEvent keyEvent){
        Long time = System.currentTimeMillis();
        
        //get the asci value of the key pressed
        int new_key = keyEvent.getKeyCode();
        
        
        //System.out.println(new_key);

        
       
            // A key must be pressed in order to be released; so, 
            // the key code is guaranteed to exist. 
            long duration = time - keys_pressed.get(new_key);

            
            if (all_keys_count.containsKey(new_key)) {
                all_keys_count.put(new_key, all_keys_count.get(new_key) + 1);
                all_keys_time.put(new_key, duration + all_keys_time.get(new_key));
            } else {
                all_keys_count.put(new_key, 1);
                all_keys_time.put(new_key, duration);
            }

            if (key_overlapped) {
                
                // assume that the othe overlapping key is still pressed
                long delay = keys_pressed.get(final_key) - time; // minus value
                
                if (two_key_count.containsKey(final_backup_key)) {
                    
                    if (two_key_count.get(final_backup_key).containsKey(final_key)) {
                        two_key_delay.get(final_backup_key).put(final_key, two_key_delay.get(final_backup_key).get(final_key) + delay);
                        two_key_count.get(final_backup_key).put(final_key, two_key_count.get(final_backup_key).get(final_key) + 1);
                    } else {
                        two_key_delay.get(final_backup_key).put(final_key, delay);
                        two_key_count.get(final_backup_key).put(final_key, 1);
                    }
                } else {
                    HashMap<Integer, Long> secondGraphDelay = new HashMap<>();
                    secondGraphDelay.put(final_key, delay);
                    two_key_delay.put(final_backup_key, secondGraphDelay);
                    HashMap<Integer, Integer> secondGraphCount = new HashMap<>();
                    secondGraphCount.put(final_key, 1);
                    two_key_count.put(final_backup_key, secondGraphCount);
                }

                // reset overlap
                key_overlapped = false;
                final_backup_key = -1;
            }

            final_key_release_time = time;
            keys_pressed.remove(new_key);
        }


    
    
    
    
}
