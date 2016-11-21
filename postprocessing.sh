for a in `ls *TopWords*`; do sed -i ':a;N;$!ba;s/\n/,/g' $a; done
