for   i   in  `cat ./errordata.txt`;do cat -n ./testFile.log|grep $i |awk '{print $1}' ;done