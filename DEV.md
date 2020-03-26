
### Insecure SSL for localhost

Enable this flag when enabling SSL and HTTP/2
```
chrome://flags/#allow-insecure-localhost
```

### Performance test

Run the app with one of the web servers
```
gradle runApp -Pserver.type=tomcat
gradle runApp -Pserver.type=undertow
gradle runApp -Pserver.type=reactor-netty

or
./scripts/run-app.sh <server.type>
```

And Run simple performance with 
```
gradle load-testing:runTest -Psimulation=ListSimulation

or

./scripts/perf.sh
``` 