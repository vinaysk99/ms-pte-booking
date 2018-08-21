# Import base image
FROM gcr.io/vinayskg1/infra-jre:latest

# Create log file directory and set permission
RUN groupadd -r ms-pte-booking && useradd -r --create-home -g ms-pte-booking ms-pte-booking
RUN if [ ! -d /var/log/nvk/ ];then mkdir /var/log/nvk/;fi
RUN chown -R ms-pte-booking:ms-pte-booking /var/log/nvk

# Move project artifact
ADD target/ms-pte-booking-*.jar /home/ms-pte-booking/

RUN touch /etc/ld.so.conf.d/java.conf
RUN echo $JAVA_HOME/lib/amd64/jli > /etc/ld.so.conf.d/java.conf
RUN ldconfig

RUN setcap CAP_NET_BIND_SERVICE=+eip $JAVA_HOME/bin/java

USER ms-pte-booking

# Launch application server
ENTRYPOINT exec $JAVA_HOME/bin/java $XMS $XMX -jar -Dspring.profiles.active=$ENVIRONMENT /home/ms-pte-booking/ms-pte-booking-*.jar --db.password="$DB_PASSWORD" --server.ssl.key-password="$KEY_PASSWORD"