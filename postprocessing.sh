for a in `ls TopWords*`; do sed -i ':a;N;$!ba;s/\n/,/g' $a; done
for a in `ls TopWords*`; do wc -l $a; done
