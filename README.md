## gRPC proto validate generator

* `mvn compile`
* Use `HelloWorldServerWithInterceptor` Which is more idiomatic gRPC Way which works with Interceptors 
*  Run `HelloWorldServer.java`
* Reflective index fails. Waiting for PR merge https://github.com/envoyproxy/protoc-gen-validate/pull/186
* Reflective index is best way once the issue is fixed. 
* Check `reflect` branch for working ReflectiveIndex example which has commented `option java_multiple_files = true;` in the proto files.
