/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc.examples.helloworld;

import io.envoyproxy.pgv.ExplicitValidatorIndex;
import io.envoyproxy.pgv.ReflectiveValidatorIndex;
import io.envoyproxy.pgv.ValidatorImpl;
import io.envoyproxy.pgv.ValidatorIndex;
import io.envoyproxy.pgv.grpc.ValidatingServerInterceptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class HelloWorldServerWithInterceptor {
    private static final Logger logger = Logger.getLogger(HelloWorldServerWithInterceptor.class.getName());

    private Server server;
//    ReflectiveIndex Fails. Fix available in PR  https://github.com/envoyproxy/protoc-gen-validate/pull/186.
//  Work around commenting out   option java_multiple_files = true;
    private ValidatorIndex reflectiveValidatorIndex = new ReflectiveValidatorIndex();
    private ValidatorImpl validator = HelloworldValidator.validatorFor(HelloRequest.class);
    private ExplicitValidatorIndex explicitValidatorIndex = new ExplicitValidatorIndex();


    private void start() throws IOException {
        explicitValidatorIndex.add(HelloRequest.class, validator);
        /* The port on which the server should run */
        int port = 50052;
        server = ServerBuilder.forPort(port)
            .addService(new GreeterImplImpl())
//            Interceptor to Validate.
            .intercept(new ValidatingServerInterceptor(explicitValidatorIndex))
            .build()
            .start();
        logger.info("Server started, listening on " + port);
        System.out.println(HelloworldValidator.HelloRequestValidator.class);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloWorldServerWithInterceptor.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloWorldServerWithInterceptor server = new HelloWorldServerWithInterceptor();
        server.start();
        server.blockUntilShutdown();
    }


}
