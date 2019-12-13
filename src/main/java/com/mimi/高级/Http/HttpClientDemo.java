package com.mimi.高级.Http;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;

import java.util.concurrent.CompletionStage;

/**
 * create by gary 2019/12/13
 * 技术交流请加QQ:498982703
 */
public class HttpClientDemo {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create();

        final CompletionStage<HttpResponse> reponseFuture = Http.get(system).singleRequest(HttpRequest.create("https://akka.io"));

        final ActorMaterializer materializer = ActorMaterializer.create(system);
        reponseFuture.thenAccept(response -> {
            response.entity().getDataBytes().runWith(Sink.foreach(content -> {
                System.out.println(content.utf8String());

            }), materializer);
        });



    }

}
