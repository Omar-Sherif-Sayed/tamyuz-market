FROM eclipse-temurin:25

ENV TZ="Asia/Riyadh"

WORKDIR /tamyuz.market

COPY build/libs/tamyuz.market-0.0.1-SNAPSHOT.jar tamyuz.market.jar

RUN chmod +x tamyuz.market.jar

EXPOSE 5050

ENTRYPOINT ["java", "-jar", "tamyuz.market.jar", "--spring.profiles.active=docker"]
