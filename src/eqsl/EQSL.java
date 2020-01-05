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

//import java.io.*;

public class EQSL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length>0)
        {        
            String mya = args[0];
            if (mya.equals("AUTO"))
            {
                System.out.println("Starting download");
                fileManage fm = new fileManage();
                fm.saveFile(args[1],args[2],args[3]);
            
                //fm.saveFile(jTextField1.getText(),String.valueOf(jPasswordField1.getPassword()),jTextField2.getText());
        
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        webInterface wi = new webInterface();
                        wi.setCallsign(args[1]);                    
                        wi.setPassword(args[2]);
                        wi.setDownloadPath(args[3]);
                        wi.setArchive(true);
                        
                        wi.myEndMonth = "12";
                        wi.myEndYear = "2100";
                        wi.myStartMonth = "01";
                        wi.myStartYear = "1901";
                        wi.test();
                        System.out.println("Finished");
                    }     
                    });
                t.start();

            
            return;
            }
        }
        
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
