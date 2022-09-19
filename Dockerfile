FROM openjdk:11

VOLUME /tmp

ADD target/icommerce-0.0.1-SNAPSHOT.jar icommerce.jar

ENTRYPOINT exec java -jar icommerce.jar