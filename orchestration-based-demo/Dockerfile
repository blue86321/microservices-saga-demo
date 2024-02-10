FROM openjdk:17-jdk-slim

WORKDIR /app

# Install make
RUN apt-get update && apt-get install -y make

# Copy files into container
COPY protos protos
COPY orchestration-based-demo orchestration-based-demo

# Build project
WORKDIR /app/orchestration-based-demo
RUN make install

# gateway
EXPOSE 8080

ENTRYPOINT ["make", "run"]
