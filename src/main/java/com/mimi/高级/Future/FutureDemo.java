package com.mimi.高级.Future;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * create by gary 2019/12/12
 * 技术交流请加QQ:498982703
 */
public class FutureDemo {

    static class FutureActor extends AbstractActor{
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                Thread.sleep(1000);
                getSender().tell("reply", getSelf());
            }).build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref =  system.actorOf(Props.create(FutureActor.class), "futureActor");

        Timeout timeout = new Timeout(Duration.create(3, "seconds"));

        Future<Object> future = Patterns.ask(ref, "hello future", timeout);

        try {
            // Await同步获取响应，如果超时了则会抛出java.util.concurrent.TimeoutException
            String result = (String) Await.result(future, timeout.duration());
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
