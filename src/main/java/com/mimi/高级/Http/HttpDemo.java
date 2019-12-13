package com.mimi.高级.Http;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.*;
import akka.stream.ActorMaterializer;
import akka.util.ByteString;

import java.util.concurrent.CompletionStage;

/**
 * create by gary 2019/12/13
 * 技术交流请加QQ:498982703
 */
public class HttpDemo {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();

        try {
           final ActorMaterializer materializer = ActorMaterializer.create(system);

            CompletionStage<ServerBinding> future = Http.get(system).bindAndHandleSync(request -> {
                if (request.getUri().path().equals("/")) {
                    return HttpResponse.create().withEntity(ContentTypes.TEXT_HTML_UTF8,
                            ByteString.fromString("<html><body>Hello world!</body></htmlp>"));
                } else if (request.getUri().path().equals("/ping")) {
                    return HttpResponse.create().withEntity(ByteString.fromString("Pong"));

                } else if (request.getUri().path().equals("crash")) {
                    throw new RuntimeException("Boom");
                } else {
                    request.discardEntityBytes(materializer);
                    return HttpResponse.create().withStatus(StatusCodes.NOT_FOUND).withEntity("Unkonw resource");
                }

            }, ConnectHttp.toHost("localhost", 8080), materializer);
            System.out.println("server online at http://localhost:8080/\nPress RETURN to stop...");
            System.in.read(); // let it run until user presses return

            future.thenCompose(ServerBinding::unbind)
                    .thenAccept(unbound -> system.terminate());

        } catch (Exception e) {
            e.printStackTrace();
            system.terminate();
        }

    }
}
