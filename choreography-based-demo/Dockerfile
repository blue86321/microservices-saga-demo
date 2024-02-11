FROM openjdk:17-jdk-slim

WORKDIR /app

# Install make
RUN apt-get update && apt-get install -y make

# Copy files into container
COPY . .

# Build project
RUN make install

EXPOSE 8080

ENTRYPOINT ["make", "run"]
