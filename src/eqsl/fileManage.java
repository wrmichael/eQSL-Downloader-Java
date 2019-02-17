/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqsl;

import java.io.*;
import java.util.*;
import java.io.Writer.*;
import java.io.Reader.*;

/**
 *
 * @author root
 */
public class fileManage {

        //File f = new File("./ien.jpg");
        FileWriter fw;
        FileReader fr;
        
        String readFile()
        {
            String strS = "";
            try
            {
                File lif = new File("./data.out");

                if (!lif.exists())
                {
                    return "||||||";
                }

                fr = new FileReader(lif);
                int i;

                        while((i=fr.read())!=-1)
                        {
                            strS = strS + (char)i;
                        };
                    fr.close();
            } catch(IOException iox)
            {
            } catch(Exception ex) 
            {
                
            }finally
            {
            
            }
            return strS;
        }
        
        boolean saveFile(String userName, String Password, String strPath)
        {
            try 
            {
            File lif = new File("./data.out");
            
            fw =  new FileWriter(lif,false);
            fw.write(userName + "|" + Password + "|" + strPath);
            //fw.write(Password);
            fw.close();
            } catch (IOException ex)
            {
                //throw(ex);
                
            }
            finally
            {
                if (!(null == fw))
                {
                    try 
                    {
                        fw.close();
                    } catch(IOException iex)
                    {
                    
                    }
                    
                    
                }
            }
            return true;
                    
        }
        
        void validateDownloadFolder(String f)
        {
            if (0 == f.trim().length() )
            {
                //failed
               return;
            }
            
            File directory = new File(f);
            if (!directory.exists())
            {
                directory.mkdir(); 
            }
        }
    
}
