package com.mimi.akka;

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
 * 获取回调的消息Patterns.ask
 */
public class HelloWorld3 {

    static class SimpleActor extends AbstractActor{
        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class, (msg) -> {
                System.out.println(msg);

            }).build();
        }
    }

    static class ActorDemo extends AbstractActor{

        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class, (msg) -> {
                ActorRef ref = getContext().actorOf(Props.create(SimpleActor.class), "SimpleActor");
                getSender().tell("result", getSelf());

            }).build();

        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref = system.actorOf(Props.create(ActorDemo.class), "actorDemo");
        Timeout timeout = new Timeout(Duration.create(1, TimeUnit.SECONDS));

        Future future = Patterns.ask(ref, "hello", timeout);

        future.onSuccess(new OnSuccess() {
            @Override
            public void onSuccess(Object result) throws Throwable {
                System.out.println(result);
            }
        }, system.getDispatcher());
        System.out.println(223);


    }


}
