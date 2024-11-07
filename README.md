# PROJETO

para rodar projeto maven
-> mvn clean javafx:run -pl interface

para executar artefato jar da pipeline CI/CD
-> java --module-path PATH_TO_JFX_LIB --add-modules javafx.controls,javafx.fxml -jar PATH_TO_JAR

para rodar SmokeTest
-> mvn -Dtest=SmokeTest test -pl persistencia
