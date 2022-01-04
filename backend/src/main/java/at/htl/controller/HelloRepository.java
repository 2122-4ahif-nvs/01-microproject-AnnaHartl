package at.htl.controller;

import at.htl.Greeter;
import at.htl.GreeterGrpc;
import at.htl.HelloReply;
import at.htl.HelloRequest;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class HelloRepository implements Greeter {

    @Override
    public Uni<HelloReply> sayHello(HelloRequest request) {
        return Uni.createFrom().item(() ->
                HelloReply.newBuilder().setMessage("Hello " + request.getName()).build()
        );
    }
}
