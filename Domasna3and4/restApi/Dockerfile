FROM public.ecr.aws/amazoncorretto/amazoncorretto:21.0.2

COPY . /restApi

WORKDIR /restApi

COPY target/OMMRestApi-1.0.jar app.jar

EXPOSE 5000

CMD ["java", "-jar", "app.jar"]