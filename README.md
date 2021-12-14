# Spring Boot application witch sends metrics to an InfluxDB

## How to run
Start InfluxDB: ```docker run --name influxdb -p 8086:8086 --rm influxdb:1.8```  
Start InfluxDB "query console": ```docker exec -it influxdb influx```  
Start application: ./gradlew bootrun
