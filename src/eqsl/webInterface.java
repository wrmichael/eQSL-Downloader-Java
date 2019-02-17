/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eqsl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
//import org.apache.http.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

public class webInterface {

        public boolean archive = false;
        public String myStartYear = "2018";
        public String myEndYear = "2018";
        public String myStartMonth = "01";
        public String myEndMonth = "06";
        
        //public String eUserName = "ac9hp";
        //public String ePassword = "abc123#";
       
	private final String USER_AGENT = "Mozilla/5.0";
        private String callsign;            
        private String Password;
        private String DownloadPath;

    /**
     * Get the value of string
     *
     * @return the value of string
     */
    public String getCallsign() {
        String temp = "";
        
        try 
        {
            //temp = URLDecoder.decode(callsign,"UTF-8");
            
        } catch (Exception ex)
        {
        }
        return callsign;
    }

    /**
     * Set the value of string
     *
     * @param string new value of string
     */
    public void setCallsign(String callsign) {
        try 
        {
            this.callsign = URLEncoder.encode(callsign,"UTF-8");
        } catch (Exception ex)
        {
        }
    }


    /**
     * Get the value of Password
     *
     * @return the value of Password
     */
    public String getPassword() {
        
        return Password;
    }
    
    public String getStartDate() {
        
        return myStartYear+myStartMonth;
    }

    /**
     * Set the value of Password
     *
     * @param Password new value of Password
     */
    public void setPassword(String Password) {
        try 
        {
            this.Password = URLEncoder.encode(Password,"UTF-8");
        } catch (Exception ex)
        {
        }
    }       
    
    public void setArchive(boolean c) {
        try 
        {
            this.archive = c;
        } catch (Exception ex)
        {
        }
    }       
    
    
    public void setDownloadPath(String dp) {
        try 
        {
            //make path accepted by unix/pc by changing \ to / 
            dp = dp.replace("\\","/");
            
            if (!dp.endsWith("/"))
            {
                dp += "/";
            }
            this.DownloadPath = dp;
        } catch (Exception ex)
        {
        }
    }       
        public void login()
        {
                
                try 
                {
                    //eUserName = URLEncoder.encode("ac9hp", "UTF-8");
                    //ePassword = URLEncoder.encode("abc123#", "UTF-8");
                } catch (Exception ex )
                {
                    System.out.print(ex.toString());
                }
            
            try 
            {
               //loginPost(eUserName,ePassword);
            } catch (Exception ex)
            {
                System.out.print(ex.toString());        
            }
            
            
        }
	/*
	// HTTP GET request
	private void sendGet() throws Exception {
                try 
                {
                    //URLEncoder.encode("ac9hp", eUserName);
                    //URLEncoder.encode("ePassword", ePassword);
                } catch (Exception ex )
                {}
                
                String url = "http://www.eqsl.cc/qslcard/LoginFinish.cfm?Callsign=" + eUserName + "&EnteredPassword=" + ePassword + "&RcvdSince=20180101";
                
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	*/
        
	// HTTP POST request
	private String sendPost(String myURL, String myParameters) throws Exception {

		String url = myURL; //"https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = myParameters; //"sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		//System.out.println(response.toString());
                return response.toString();
	}
        
        public void downloadAndParseLog(String html)
        {
            //html = html.toUpperCase();            
            if (-1 == html.toUpperCase().indexOf(".ADI"))
            {
                //System.out.println(html.toUpperCase().indexOf(".ADI"));
                
                //System.out.println("No ADI file listed");
                return;
            }
            try {
                //<A HREF="downloadedfiles/ACWH436338.adi">
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                //Logger.getLogger(webInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            //this.logit(html);
            String tempStr = html.split("downloadedfiles/")[1];
            tempStr = tempStr.substring(0,tempStr.indexOf(".adi"));
           // System.out.print("filename: " + tempStr);
            
            String sUrl = "http://www.eqsl.cc/qslcard/downloadedfiles/" + tempStr + ".adi";
            //System.out.println(sUrl);
            //this.logit("Downloding from: " + sUrl);
            
            String t = getFile(sUrl);
            
              //System.out.print(t);
              
              parseADFI(t);
            return;
        }
        
        public void parseADFI(String s)
        {
            
            /*====================================
The URL is https://www.eQSL.cc/qslcard/GeteQSL.cfm (case insensitive)

The following parameters are required:
  Username        The callsign of the recipient of the eQSL
  Password        The password of the user's account
  CallsignFrom    The callsign of the sender of the eQSL
  QSOYear         YYYY OR YY format date of the QSO
  QSOMonth        MM format
  QSODay          DD format
  QSOHour         HH format (24-hour time)
  QSOMinute       MM format
  QSOBand         20m, 80M, 70cm, etc. (case insensitive)
  QSOMode         Must match exactly and should be an ADIF-compatible mode
*/
        //String adfi = "<CALL:4>NW3H<QSO_DATE:8:D>20141011<TIME_ON:4>1720<BAND:3>20M<MODE:3>SSB<RST_SENT:2>59<RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:4>FN20<EOR>\n" + "\n" + "<CALL:4>N1SP<QSO_DATE:8:D>20141110<TIME_ON:4>2228<BAND:3>20M<MODE:3>SSB<RST_SENT:0><RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:6>fn32jv<EOR>\n" + "\n" + "<CALL:5>N9OJC<QSO_DATE:8:D>20141130<TIME_ON:4>1351<BAND:3>80M<MODE:4>JT65<RST_SENT:0><RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:6>EN60tr<EOR><CALL:5>AF6JO<QSO_DATE:8:D>20141208<TIME_ON:4>0223<BAND:3>40M<MODE:4>JT65<RST_SENT:3>-12<RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:6>DM12lq<EOR><CALL:4>W0QL<QSO_DATE:8:D>20141210<TIME_ON:4>2333<BAND:3>20M<MODE:4>JT65<RST_SENT:3>-16<RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:6>DM79ot<EOR><CALL:5>W3WTE<QSO_DATE:8:D>20141218<TIME_ON:4>2034<BAND:3>20M<MODE:4>JT65<RST_SENT:3>-06<RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:6>FM28hn<EOR><CALL:5>K7BXB<QSO_DATE:8:D>20141219<TIME_ON:4>1856<BAND:3>10M<MODE:4>JT65<RST_SENT:3>-23<RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:6>DM09de<EOR><CALL:5>K5AGC<QSO_DATE:8:D>20141220<TIME_ON:4>0256<BAND:3>40M<MODE:4>JT65<RST_SENT:3>-16<RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:6>DN41cs<EOR><CALL:5>K5AGC<QSO_DATE:8:D>20141221<TIME_ON:4>2008<BAND:3>20M<MODE:4>JT65<RST_SENT:3>-10<RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:6>DN41cs<EOR><CALL:6>KD8OSD<QSO_DATE:8:D>20150201<TIME_ON:4>2303<BAND:3>40M<MODE:4>JT65<RST_SENT:3>-09<RST_RCVD:0><QSL_SENT:1>Y<QSL_SENT_VIA:1>E<APP_EQSL_AG:1>Y<GRIDSQUARE:6>EN82sw<EOR>";
        String QSO_Call = "";
        String QSO_Time = "";
        String QSO_Day = "";
        String QSO_Hour = "";
        String QSO_Minute = "";
        String QSO_Year = "";
        String QSO_Month = "";
        String QSO_Date = "";
        String QSO_Band = "";
        String QSO_Mode = "";
        String current_adfi = s; 
        String current_qso = "";
        
        ArrayList<String> mylist = new ArrayList<String>();
        
  
        int calllength = 0;
        
        this.logit("start\n");
        
        boolean success = (new File(this.DownloadPath)).mkdirs();
        if (!success)
        {
            //logit("did not create folder");
            this.logit("Failed to create: " + this.DownloadPath);
        }

        
        do{
            int idx = current_adfi.indexOf("EOR") +4;
    
            if (idx<5)
            {
                //logit("break");
                break; // complete no more EOR 
            }
            //System.out.print(idx);
            current_qso = current_adfi.substring(0, idx);
            //logit("\n\r[" + current_qso + "]");
            
            QSO_Call = "";
            QSO_Date = "";
            QSO_Time = ""; 
            QSO_Band = "";
            QSO_Mode = "";
            
            String myar[] = current_qso.split("<");
            String field = "";
            String value = "";
            String myqso = "";
            String died = "";
            
            for(int i=0;i < myar.length;i++)
            {
                if (myar[i].length() >0)
                {
                    try 
                    {
                    if (myar[i].indexOf(":")>0)
                    {
                        died = "1";
                        field = myar[i].substring(0,myar[i].indexOf(":"));
                        died = "2";
                        value = myar[i].substring(myar[i].indexOf(">")+1);
                        
                        if (field.equals("CALL"))
                        {
                            died = "4";
                            QSO_Call = value;
                        }
                        if (field.equals("MODE"))
                        {
                            died = "5";
                            QSO_Mode = value; 
                        }
                        if (field.equals("TIME_ON"))
                        {
                            died = "6";
                            QSO_Hour = value.substring(0,2);
                            QSO_Minute = value.substring(2,4);
                            QSO_Time =  value.substring(0,2) + ":" + value.substring(2,4);
                        }
                        if (field.equals("BAND"))
                        {
                            died = "7";
                            QSO_Band = value;
                        }
                        if (field.equals("QSO_DATE"))
                        {
                            died = "8";
                            QSO_Year = value.substring(0,4);
                            QSO_Month = value.substring(4,6);
                            QSO_Day = value.substring(6,8);
                            
                            QSO_Date = value.substring(0,4) + "-" + value.substring(4,6) + "-" + value.substring(6,8);
                        }
                    }
                    } catch (Exception ex)
                    {
                        return;
                    }
                }
            }
             /*====================================
The URL is https://www.eQSL.cc/qslcard/GeteQSL.cfm (case insensitive)

The following parameters are required:
  Username        The callsign of the recipient of the eQSL
  Password        The password of the user's account
  CallsignFrom    The callsign of the sender of the eQSL
  QSOYear         YYYY OR YY format date of the QSO
  QSOMonth        MM format
  QSODay          DD format
  QSOHour         HH format (24-hour time)
  QSOMinute       MM format
  QSOBand         20m, 80M, 70cm, etc. (case insensitive)
  QSOMode         Must match exactly and should be an ADIF-compatible mode
*/
            //https://www.eqsl.cc/QSLCard/DisplayeQSL.cfm?
            //Callsign=WV0Q&VisitorCallsign=AC9HP&QSODate=2018-06-21%2001:20:00.0&Band=6M&Mode=FT8
            String myq;
            String myqp; 
            
            myq = "UserName=" + this.callsign + "&Password=" + this.Password + "&CallsignFrom=" + QSO_Call + "&QSOYear=" + QSO_Year + "&QSOMonth=" + QSO_Month + "&QSODay=" + QSO_Day;
            myq += "&QSOHour=" + QSO_Hour + "&QSOMinute=" + QSO_Minute +  "&QSOBand=" + QSO_Band + "&QSOMode=" + QSO_Mode;
            
            myqp = "CallsignFrom=" + QSO_Call + "&QSOYear=" + QSO_Year + "&QSOMonth=" + QSO_Month + "&QSODay=" + QSO_Day;
            myqp += "&QSOHour=" + QSO_Hour + "&QSOMinute=" + QSO_Minute +  "&QSOBand=" + QSO_Band + "&QSOMode=" + QSO_Mode;
            myqp += ".png";
    
            
            //this.logit("Download = " + this.DownloadPath + " -- " + success);
            
            String myfile  = this.DownloadPath + myqp.replace("&","_").replace(":", "").replace("-","").replace("=","");
            
            this.logit("Downloading: " + myfile);
            File tmpf = new File(myfile);
            
            if (tmpf.exists())
            {
                this.logit("already exists: " + myfile);
            } else
            {
                String myr="";
                //download
                try 
                {
                    //this.logit("https://www.eqsl.cc/QSLCard/GeteQSL.cfm?" + myq);
                    myr = sendPost("https://www.eqsl.cc/QSLCard/GeteQSL.cfm?" + myq,"");
                    //this.logit(myr);
                    /*<!-- If there have been no errors to this point, parse for tag <IMG SRC= and the URL path to the graphic will follow in 
                    double quotes --><!-- The .. should be replaced with https://www.eQSL.cc in the full URL specification -->
                    <img src="/CFFileServlet/_cf_image/_cfimg-155518818509243536.PNG" alt="" /><!-- End of the eQSL file location -->
                    */
                    
                    myr = myr.substring(myr.indexOf("img src=")+9);
              //      this.logit(myr);
                    myr = myr.substring(0,myr.indexOf("\""));
              //      this.logit(myr);
                    myr = "https://www.eQSL.cc" + myr;
              //      this.logit(myr);
                    
                    
                    URL url = new URL(myr);
                    InputStream in = new BufferedInputStream(url.openStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1!=(n=in.read(buf)))
                    {
                       out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    byte[] response = out.toByteArray();
                    
                    
                    FileOutputStream fos = new FileOutputStream(myfile);
                    fos.write(response);
                    fos.close();

                    //return;
                    java.lang.Thread.sleep(10500);
                } catch (Exception ex) 
                {
                    this.logit("failure parseADI" + ex.toString());
                    return;
                }
                //return;
                //this.logit(myqp);
            }

            
            
            
            current_adfi = current_adfi.substring(idx);
            //logit("[" + current_qso + "]");
        } while (current_qso.length() >0);
        
        
        }
        
        public void logit(String s) {
            try {
            //Whatever the file path is.
            String logpath = this.DownloadPath + "applicationlog.txt";
            File statText = new File(logpath);
            FileOutputStream is = new FileOutputStream(statText,true);
            OutputStreamWriter osw = new OutputStreamWriter(is);    
            Writer w = new BufferedWriter(osw);
            w.write("\n" +s);
            w.close();
            } catch (IOException e) {
                System.err.println("Problem writing to the file statsTest.txt");
            }
        }
    
        
        public void test()
        {
            ProcessDownload();
            return;
            //URL url=null;
        /*
            URL obj = null; //new URL(url);
            
            HttpURLConnection con = null; 

            
            try
            {
                eUserName = URLEncoder.encode("ac9hp", "UTF-8");
                ePassword = URLEncoder.encode("abc123#", "UTF-8");
            
                obj = new URL("http://www.eqsl.cc/qslcard/DownloadInBox.cfm?UserName=" + eUserName + "&Password=" + ePassword + "&RcvdSince=20170201");
         
                con =(HttpURLConnection) obj.openConnection();
                } catch (Exception ex)
            {
                System.out.print(ex.toString());
            }

            
        
	try
        {
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
                downloadAndParseLog(response.toString());
                
        } catch (Exception ex)
        {
            System.out.print(ex.toString());
        }
            */
}
        
    public void ProcessDownload()
    {
            
            URL obj = null; //new URL(url);
            
            HttpURLConnection con = null; 

            
            try
            {
                //eUserName = this.getCallsign(); //URLEncoder.encode("ac9hp", "UTF-8");
                //ePassword = this.getPassword(); //URLEncoder.encode("abc123#", "UTF-8");
                
                String sUrl = "http://www.eqsl.cc/qslcard/DownloadInBox.cfm?UserName=" + this.getCallsign() + "&Password=" + this.getPassword() + "&RcvdSince=" + this.myStartYear + this.myStartMonth + "01&ConfirmedOnly=1"; //20170201";
                
                if (this.archive)
                {
                    sUrl += "&Archive=1";
                }
                
                
                //System.out.println(sUrl);
                obj = new URL(sUrl);
         
                con =(HttpURLConnection) obj.openConnection();
                } catch (Exception ex)
            {
                System.out.print(ex.toString());
            }

            
        
	try
        {
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());
                downloadAndParseLog(response.toString());
                
        } catch (Exception ex)
        {
            System.out.print(ex.toString());
        }
    }
     
    public String getFile(String url)
    {
        String t = "";
            URL obj = null; //new URL(url);
            
            HttpURLConnection con = null; 
            
            try
            {
            
                obj = new URL(url);
         
                con =(HttpURLConnection) obj.openConnection();
                } catch (Exception ex)
            {
                System.out.print(ex.toString());
            }

	try
        {
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		//System.out.println(response.toString());
                t = response.toString();
        } catch (Exception ex)
        {
            System.out.print(ex.toString());
        }
        return t;
    }
        
        // HTTP POST request
	private void loginPost(String eUserName,String ePassword) throws Exception {

		String url = "https://www.eqsl.cc/qslcard/LoginFinish.cfm";
		URL obj = new URL(url);
                
                
                
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setAllowUserInteraction(true);
                con.setUseCaches(true);
                con.setInstanceFollowRedirects(true);
                
        	String urlParameters = "Callsign="+ eUserName + "&EnteredPassword=" + ePassword + "&Login=Go";
		//System.out.print(urlParameters);
                
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		//System.out.println(response.toString());
                String INBOX_URL = "";
                String Params = "";
                
                //System.out.print(sendPost("https://www.eqls.cc/qslcard/CookieTest.cfm","sw=1000&sh=1000"));
                
                if (archive)
                {
                    INBOX_URL = "https://www.eqsl.cc/qslcard/InBox.cfm";
                    Params = "Archive=0&Reject=0&LimitDateLo=" + myStartYear + " " + myStartMonth + "&LimitDateHi=" + myEndYear + " " + myEndMonth;
                }else 
                {
                    INBOX_URL = "https://www.eqsl.cc/qslcard/InBox.cfm?Archive=1";
                    Params = "Reject=0&LimitDateLo=" + myStartYear + " " + myStartMonth + "&LimitDateHi=" + myEndYear + " " + myEndMonth;
                }
                String html = sendPost(INBOX_URL,Params);
                //System.out.print(html);
	}

}


/**
 *
 * @author root
 */
/*
public class webInterface {
    
    string EQSL_LOGIN = "";
    
    
}*/
