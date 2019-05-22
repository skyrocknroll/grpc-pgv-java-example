package io.grpc.examples.helloworld;

import io.envoyproxy.pgv.*;
import io.grpc.stub.StreamObserver;

public class GreeterImplImpl extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        System.out.println("Helll!!" + req.getName() + "\n");
        ValidatorIndex explicitValidatorIndex = new ExplicitValidatorIndex();
        ValidatorIndex reflectiveValidatorIndex = new ReflectiveValidatorIndex();
        Validator<HelloRequest> objectValidator = reflectiveValidatorIndex.validatorFor(HelloRequest.class);
        System.out.println("xxxxxx Me " + objectValidator.isValid(req));

//
//
//        Validator<HelloRequest> validator = validatorIndex.validatorFor(HelloRequest.class);
//        boolean valid = validator.isValid(req);
        HelloworldValidator.HelloRequestValidator helloRequestValidator = new HelloworldValidator.HelloRequestValidator();
        try {
            helloRequestValidator.assertValid(req, reflectiveValidatorIndex);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        HelloworldValidator.HelloRequestValidator requestValidator = new HelloworldValidator.HelloRequestValidator();

//        try {
//            validator.isValid(req);
//        } catch (ValidationException e) {
//            e.printStackTrace();
//        }
//        System.out.println("valid---" + valid);
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
