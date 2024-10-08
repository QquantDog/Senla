FROM tomcat:11.0
#RUN apt-get update && apt-get install -y nano
COPY context.xml /usr/local/tomcat/conf/
ENTRYPOINT ["catalina.sh", "run"]