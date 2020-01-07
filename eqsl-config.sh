read -p "Call Sign:" CALLSIGN
read -p "Password:" PASSWORD
echo 'java -jar ./eQSL.jar AUTO' $CALLSIGN $PASSWORD /home/pi/Pictures/eql>./eqsl.sh
