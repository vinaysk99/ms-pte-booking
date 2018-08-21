# Import base image
FROM gcr.io/vinayskg1/infra-jre:latest

# Create log file directory and set permission
RUN groupadd -r ms-pte-booking && useradd -r --create-home -g ms-pte-booking ms-pte-booking
RUN if [ ! -d /var/log/nvk/ ];then mkdir /var/log/nvk/;fi
RUN chown -R ms-pte-booking:ms-pte-booking /var/log/nvk

RUN mkdir /home/ms-pte-booking/trust-store
RUN chown -R ms-pte-booking:ms-pte-booking /home/ms-pte-booking/trust-store
ADD src/trust-store/jetty-trust /home/ms-pte-booking/trust-store/
ADD src/trust-store/jetty-keystore /home/ms-pte-booking/trust-store/

# Move project artifact
ADD target/ms-pte-booking-*.jar /home/ms-pte-booking/

RUN touch /etc/ld.so.conf.d/java.conf
RUN echo $JAVA_HOME/lib/amd64/jli > /etc/ld.so.conf.d/java.conf
RUN ldconfig

RUN setcap CAP_NET_BIND_SERVICE=+eip $JAVA_HOME/bin/java

USER ms-pte-booking

# Launch application server
ENTRYPOINT exec $JAVA_HOME/bin/java $XMS $XMX -jar -Dspring.profiles.active=$ENVIRONMENT -Djavax.net.ssl.trustStore=/home/ms-pte-booking/trust-store/jetty-trust -Djavax.net.ssl.trustStorePassword=$KEY_PASSWORD /home/ms-pte-booking/ms-pte-booking-*.jar --db.password="$DB_PASSWORD" --audit.db.password="$AUDIT_DB_PASSWORD" --jwt.db.password="$JWT_DB_PASSWORD" --server.ssl.key-store-password="$KEYSTORE_PASSWORD" --server.ssl.key-password="$KEY_PASSWORD"