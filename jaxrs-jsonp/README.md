To test GET:

> $ curl -v http://localhost:8080/jaxrs-jsonp/rest/hello

To test POST:

> $ curl -v -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"message":"hello"}' http://localhost:8080/jaxrs-jsonp/rest/hello