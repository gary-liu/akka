package com.mimi.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * create by gary 2019/12/8
 * 技术交流请加QQ:498982703
 * 向另一个actor发送消息
 */
public class HelloWorld2 {

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
//                ref.tell(msg, getSelf());
                ref.forward(msg, getContext());
                getSender().tell("result", getSelf());

            }).build();

        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref = system.actorOf(Props.create(ActorDemo.class), "actorDemo");
        ref.tell("simple world",ActorRef.noSender());
    }


}
