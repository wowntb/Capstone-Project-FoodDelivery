FROM openjdk
WORKDIR /app
COPY . /app/
RUN javac *.java
CMD java Main