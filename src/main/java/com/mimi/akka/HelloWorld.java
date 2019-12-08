package com.mimi.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * create by gary 2019/12/8
 * 技术交流请加QQ:498982703
 * 发送字符串
 */
public class HelloWorld {

    static class ActorDemo extends AbstractActor{

        public void test(String msg) {
            System.out.println(msg);
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class, (msg) -> {
                test(msg);
            }).build();

        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref = system.actorOf(Props.create(ActorDemo.class), "actorDemo");
        ref.tell("hello world",ActorRef.noSender());
    }


}
