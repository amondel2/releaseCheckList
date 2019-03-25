#!/bin/bash
sftp aaron@40.121.35.212 <<End-Of-Session
cd war
sudo su
rm -Rf /home/aaron/war/*.war
put /home/aaron/IdeaProjects/releaseCheckList/build/libs/*.war
exit
End-Of-Session
ssh aaron@40.121.35.212 <<End-Of-Session
sudo su
cd /opt/tomcat/
systemctl stop tomcat
rm -Rf logs/*
rm -Rf webapps/rel*
rm -Rf work/Catalina/*
mv /home/aaron/war/*.war webapps/rel.war
systemctl start tomcat
iptables -A PREROUTING -t nat -i eth0 -p tcp --dport 80 -j REDIRECT --to-port 8080
exit
exit
End-Of-Session
echo DONE!
