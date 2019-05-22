package io.grpc.examples.helloworld;

import io.envoyproxy.pgv.ReflectiveValidatorIndex;
import io.envoyproxy.pgv.ValidationException;
import io.envoyproxy.pgv.ValidatorImpl;
import io.envoyproxy.pgv.ValidatorIndex;


import io.grpc.stub.StreamObserver;

public class GreeterImplImpl extends GreeterGrpc.GreeterImplBase {
    private ValidatorIndex reflectiveValidatorIndex = new ReflectiveValidatorIndex();

    @Override
    public void sayHello(HelloWorldProto.HelloRequest req, StreamObserver<HelloWorldProto.HelloReply> responseObserver) {
//        String error = null;
        System.out.println(req.getName());
////        System.out.println("Helll!!" + req.getName() + "\n");
////        ValidatorIndex explicitValidatorIndex = new ExplicitValidatorIndex();
//        ValidatorImpl validator = HelloworldValidator.validatorFor(HelloRequest.class);
//        try {
//            validator.assertValid(req, reflectiveValidatorIndex);
//        } catch (ValidationException e) {
//            error = e.getMessage();
////            e.printStackTrace();
//        }

//        HelloworldValidator.HelloRequestValidator helloRequestValidator = new HelloworldValidator.HelloRequestValidator();
//        try {
//            helloRequestValidator.assertValid(req, reflectiveValidatorIndex);
//        } catch (ValidationException e) {
//            e.printStackTrace();
//        }
//        HelloworldValidator.HelloRequestValidator requestValidator = new HelloworldValidator.HelloRequestValidator();
        HelloWorldProto.HelloReply reply;
//        if( error != null){
//            reply = HelloReply.newBuilder().setMessage("Error Occurred! " + error).build();
//        }else {
//            reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
//        }
        reply = HelloWorldProto.HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
