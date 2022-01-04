package at.htl.controller;

import at.htl.Greeter;
import at.htl.HelloRequest;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class HelloRepositoryTest{

    @GrpcClient
    Greeter client;

    @Test
    void shouldReturnHello() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> message = new CompletableFuture<>();
        client.sayHello(HelloRequest.newBuilder().setName("Quarkus").build())
                .subscribe().with(reply -> message.complete(reply.getMessage()));
        assertThat(message.get(5, TimeUnit.SECONDS), is(equalTo("Hello Quarkus")));
    }
}
