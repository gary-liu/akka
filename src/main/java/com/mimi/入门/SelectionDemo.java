package com.mimi.入门;

import akka.actor.*;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.util.Try;

import java.util.concurrent.TimeUnit;

/**
 * create by gary 2019/12/11
 * 技术交流请加QQ:498982703
 */
public class SelectionDemo {

    static class TargetActor extends AbstractActor{

        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> System.out.println("target receive:  "+msg))
                    .build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef actorRef1 = system.actorOf(Props.create(TargetActor.class), "targetDemo1");
        ActorRef actorRef2 = system.actorOf(Props.create(TargetActor.class), "targetDemo2");
        System.out.println(actorRef1);

        ActorSelection actorSelection = system.actorSelection("user/targetDemo*");

        actorSelection.tell("hello", ActorRef.noSender());

        Future<ActorRef> future = actorSelection.resolveOne(new Timeout(Duration.create(3, TimeUnit.SECONDS)));
        future.onComplete(new OnSuccess<Try<ActorRef>>() {
            @Override
            public void onSuccess(Try<ActorRef> result) throws Throwable {
                System.out.println(result);

            }
        }, system.getDispatcher());


    }
}
