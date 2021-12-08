mvn compile
rm -rf webapp/WEB-INF/classes
cp -r target/classes/ webapp/WEB-INF/
# cd webapp
# # jar -cvf hotel.war *
# # mv hotel.war $CATALINA_HOME/webapps/