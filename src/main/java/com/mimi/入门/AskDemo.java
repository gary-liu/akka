package com.mimi.入门;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * create by gary 2019/12/8
 * 技术交流请加QQ:498982703
 */
public class AskDemo {

    static class ActorDemo extends AbstractActor{
        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class,(msg)->{
                System.out.println(msg);
                getSender().tell(msg, getSelf());

            }).build();
        }
    }

    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref = system.actorOf(Props.create(ActorDemo.class), "ActorDemo");

        Timeout timeout = new Timeout(Duration.create(2, TimeUnit.SECONDS));

        Future<Object> akksAsk = Patterns.ask(ref, "akks ask", timeout);
        System.out.println("ask");
        akksAsk.onSuccess(new OnSuccess<Object>() {
            @Override
            public void onSuccess(Object result) throws Throwable {
                System.out.println(result);

            }
        },system.getDispatcher());


    }
}
