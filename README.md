# DIANS-Proekt
Members:

211017 - M-Jak

211054 - zdrave123

211090 - SomalianBoi

211102 - k-cvet

211208 - Nik0la23

[Link to application](https://omm-app.nicepebble-3b5681aa.westeurope.azurecontainerapps.io/)  

[Link to pins microservice](https://omm-api.nicepebble-3b5681aa.westeurope.azurecontainerapps.io)

Апликацијата и апи-то се хостирани користејќи Container App на Azure, од images креирани со docker-compose и соодветни dockerfile датотеки.
Поставени се во заеднички environment, што може да се види и од името на доменот во линковите погоре.
proxy сервисот служи при локално подигнување на Docker контејнерот, за комуникација меѓу апликацијата и апи-то во рамките на истиот Docker контејнер, односно истата мрежа која се креира со docker-compose.
