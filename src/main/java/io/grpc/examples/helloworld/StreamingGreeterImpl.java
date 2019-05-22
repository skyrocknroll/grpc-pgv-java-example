package io.grpc.examples.helloworld;

import io.grpc.examples.manualflowcontrol.HelloReply;
import io.grpc.examples.manualflowcontrol.HelloRequest;
import io.grpc.examples.manualflowcontrol.StreamingGreeterGrpc;
import io.grpc.stub.StreamObserver;

public class StreamingGreeterImpl extends StreamingGreeterGrpc.StreamingGreeterImplBase {
    @Override
    public StreamObserver<HelloRequest> sayHelloStreaming(StreamObserver<HelloReply> responseObserver) {
        return super.sayHelloStreaming(responseObserver);
    }

}
