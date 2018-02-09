/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometric_authentication_keystroke;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/**
 *
 * @author acer
 */
public class DB_Handler {
    Connection con;
    Map<String, String> map;
    public DB_Handler() throws SQLException, IOException, ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        this.con = DriverManager.getConnection("jdbc:derby:C:\\Users\\acer\\.netbeans-derby\\keystroke", "app","123");
        System.out.println("connection successful");
        
         map = new HashMap<>();
        map.put("a","1");    map.put("A","}");  map.put("`","}");   map.put("?","}");
        map.put("b","2");    map.put("B",";");  map.put("~","}");   map.put(",","}");
        map.put("c","3");    map.put("C",":");  map.put("@","}");   map.put(".","}");
        map.put("d","4");    map.put("D","'");  map.put("#","}");   map.put("/","}");
        map.put("e","5");    map.put("E","/");  map.put("$","}");   map.put("1","v");   
        map.put("f","6");    map.put("F","<");  map.put("!","}");   map.put("2","o");
        map.put("g","7");    map.put("G",">");  map.put("%","}");   map.put("3","x");     
        map.put("h","8");    map.put("H","?");  map.put("^","}");   map.put("4","g");
        map.put("i","9");    map.put("I","|");  map.put("&","}");   map.put("5","w");
        map.put("j","0");    map.put("J",".");  map.put("*","}");   map.put("6","d");
        map.put("k","!");    map.put("K",",");  map.put("(","}");   map.put("7","i");
        map.put("l","@");    map.put("L","1");  map.put(")","}");   map.put("8","f");
        map.put("m","#");    map.put("M","2");  map.put("-","}");   map.put("9","d");
        map.put("n","%");    map.put("N","3");  map.put("_","}");   map.put("0","s");
        map.put("o","^");    map.put("O","4");  map.put("=","}");
        map.put("p","&");    map.put("P","5");  map.put("+","}");
        map.put("q","*");    map.put("Q","6");  map.put("[","}");
        map.put("r","(");    map.put("R","7");  map.put("]","}");
        map.put("s",")");    map.put("S","8");  map.put("{","}");
        map.put("t","_");    map.put("T","9");  map.put("}","}");
        map.put("u","-");    map.put("U","A");  map.put("|","}");
        map.put("v","+");    map.put("V","B");  map.put(";","}");
        map.put("w","=");    map.put("W","C");  map.put(":","}");
        map.put("x","[");    map.put("X","D");  map.put("'","}");
        map.put("y","]");    map.put("Y","E");  map.put("<","}");
        map.put("z","{");    map.put("Z","F");  map.put(">","}");
        
      }
    
    /**
     *
     * @param kp
     * @param UserName
     * @param Pass
     * @throws SQLException
     * @throws IOException
     */
    public void save(KeyPattern kp,String UserName,String Pass) throws SQLException, IOException{
        PreparedStatement st = con.prepareStatement("insert into app.Login(USERNAME,PASSWORD,OBJ) values(?,?,?)");
        //KeyPattern kp=new KeyPattern();
        //kp.setUsername("WINMA");
        //System.out.println(kp.toString());
        
        ByteArrayOutputStream bos= new ByteArrayOutputStream();
        ObjectOutputStream oos= new ObjectOutputStream(bos);
        oos.writeObject(kp);
        oos.flush();
        oos.close();
        oos.close();
        
        byte[] ob=bos.toByteArray();
        ByteArrayInputStream bais=new ByteArrayInputStream(ob);
        
        st.setString(1, UserName);
        st.setString(2, Pass);
        st.setBinaryStream(3, bais,ob.length);
        try{
        st.executeUpdate();
        
        } catch(SQLException e){
            //if(e.getErrorCode()==2627){
                final JPanel panel = new JPanel();
                JOptionPane.showMessageDialog(panel, "User has already registered in the same user name.Please try another !", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(" A user has already registered in the same user name");
                //}
        }
        st.close();
    }
    
    public KeyPattern receive(String UserName, String Pass) throws SQLException, IOException, ClassNotFoundException{
        KeyPattern k = null;
         String st1="SELECT * FROM app.Login where UserName=? AND Password=?";
         PreparedStatement pst=con.prepareStatement(st1);
         pst.setString(1, UserName);
         pst.setString(2, Pass);
         ResultSet set1=pst.executeQuery();
          while(set1.next()){
             Blob bl= set1.getBlob("OBJ");
             byte[] ba=bl.getBytes(1, (int) bl.length());
             ByteArrayInputStream bais1=new ByteArrayInputStream(ba);
             ObjectInputStream ois= new ObjectInputStream(bais1);
             k=(KeyPattern)ois.readObject();
         }
         set1.close();
         pst.close();
          
          // when user name and password is not there.
       
    return k;
    }
    
    public String hashpass(String pass){
       
       String new_pass="";
        for(int i=0 ; i<pass.length();i++){
           
            if(map.containsKey(String.valueOf(pass.charAt(i)))){
            new_pass=new_pass+map.get(String.valueOf(pass.charAt(i)));}
            else{
            new_pass=new_pass+'a';
            }
        }
        
        
        
        
        
        
        
        return new_pass;
        
    }
  
    
    
    
    
}
