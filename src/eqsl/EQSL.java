/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqsl;

/**
 *
 * @author root
 */
public class EQSL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        eqsl.loginScreen ls = new eqsl.loginScreen();
        ls.setVisible(true);
        
        
        javax.swing.JFrame jf=new javax.swing.JFrame();
        jf.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        jf.setSize(400, 375);
        jf.add(ls);
        jf.setVisible(true);
        jf.setTitle("eQSL Downloader - AC9HP 2019");
        
        
    }
   
    
}
