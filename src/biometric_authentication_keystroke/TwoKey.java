/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric_authentication_keystroke;

import java.io.Serializable;

/**
 *
 * @author acer
 */
public class TwoKey  implements Serializable{
    private final int first;
    private final int second;
    
    public TwoKey(int first,int second){
        this.first=first;
        this.second=second;
        
    }

    /**
     * @return the first
     */
    public int getFirst() {
        return first;
    }

    /**
     * @return the second
     */
    public int getSecond() {
        return second;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TwoKey)) {
            return false;
        }
        TwoKey tw = (TwoKey) o;
        return this.first == tw.getFirst()
                && this.second == tw.getSecond();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.first;
        hash = 17 * hash + this.second;
        return hash;
    }

   
    
}
