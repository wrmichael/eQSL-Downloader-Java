# eQSL-Downloader-Java
Download your eQSL images from eQSL without having to download each one manually 


command line options: 

AUTO {callsign} {password} {path}

example:
java -jar ./eQSL.jar AUTO ac9hp mypassword /home/pi/Pictures/eqsl


Things to do for PI digital frame version: 

Create base image with all things installed

Create script to get usename and password and create the eqsl.sh file with that data 

Create login script that has options:
  
    Run raspi-config
    Go to Shell 
    Run tool to create/update 
         username and password for eqsl
         update crontabe schedule
    
Add to image with crontab:
      daily reboot
      weekly / daily / monthly download frmo eqsl 

Future: 

Web interface to udpate/maintain pi configurations 

Link to other folders (drop box / google drive /etc ) that may hold paper QSL images. 
