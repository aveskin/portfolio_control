# Используем базовый образ с установленной JDK 17
FROM openjdk:17-jdk-slim AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и необходимые файлы для сборки
COPY pom.xml ./
COPY src ./src
COPY mvnw ./
COPY .mvn .mvn

# Устанавливаем права на выполнение для скрипта mvnw
RUN chmod +x mvnw

# Сборка приложения (в данном случае Maven)
RUN ./mvnw clean package -DskipTests

# Второй этап: создание исполняемого образа
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный .jar файл из первого этапа
COPY --from=build /app/target/*.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]