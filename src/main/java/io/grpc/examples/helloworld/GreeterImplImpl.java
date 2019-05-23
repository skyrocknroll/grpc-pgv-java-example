package io.grpc.examples.helloworld;

import io.envoyproxy.pgv.ReflectiveValidatorIndex;
import io.envoyproxy.pgv.ValidationException;
import io.envoyproxy.pgv.ValidatorImpl;
import io.envoyproxy.pgv.ValidatorIndex;


import io.grpc.stub.StreamObserver;

public class GreeterImplImpl extends GreeterGrpc.GreeterImplBase {
    private ValidatorIndex reflectiveValidatorIndex = new ReflectiveValidatorIndex();

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        System.out.println(req.getName());
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
