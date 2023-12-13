from clamav/clamav:1.0.3-21
ARG version=17-jdk

RUN wget -O /etc/apk/keys/adoptium.rsa.pub https://packages.adoptium.net/artifactory/api/security/keypair/public/repositories/apk && \
    echo 'https://packages.adoptium.net/artifactory/apk/alpine/main' >> /etc/apk/repositories
    apk add temurin-17-jdk && \


ENV JAVA_HOME=/usr/lib/jvm/default-jvm
ENV PATH=$PATH:/usr/lib/jvm/default-jvm/bin

RUN echo "Java home " $JAVA_HOME
RUN javac -version
RUN mkdir -p /usr/local/app
RUN chmod 777 /usr/local/app

RUN mkdir -p /usr/local/glowroot
RUN chmod 777 /usr/local/glowroot
COPY ./glowroot/ /usr/local/glowroot/

#Installing Network Tools
RUN apk add --no-cache &&\
    apk add net-tools &&\
    apk add netcat-openbsd &&\
	apk add busybox-extras &&\
    apk add util-linux &&\
	apk add traceroute &&\
	apk add tcpdump &&\
	apk add procps-ng &&\
	apk add psmisc &&\
	apk add iputils &&\
	apk add bind-tools

COPY ./build/dist/VirusScanner*.jar /usr/local/app/VirusScanner.jar
COPY ./bootstrap.sh /usr/local/app
RUN chmod 777 /usr/local/app/bootstrap.sh
RUN sed -i 's/#MaxFileSize 400M/MaxFileSize 150M/' /etc/clamav/clamd.conf
EXPOSE 3000

ENTRYPOINT ["./usr/local/app/bootstrap.sh"]

