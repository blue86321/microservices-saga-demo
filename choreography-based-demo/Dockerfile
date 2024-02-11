FROM openjdk:17-jdk-slim

WORKDIR /app

# Install make
RUN apt-get update && apt-get install -y make

# Copy files into container
COPY protos protos
COPY choreography-based-demo choreography-based-demo

# Build project
WORKDIR /app/choreography-based-demo
RUN make install

# gateway
EXPOSE 8080

ENTRYPOINT ["make", "run"]
