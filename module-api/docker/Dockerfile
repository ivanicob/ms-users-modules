FROM maven:3.8.2-jdk-8-slim

RUN useradd -d /home/deploy -m -s /bin/bash deploy

WORKDIR /tmp/app

RUN chown -R deploy:deploy .

USER deploy

EXPOSE 7878