package com.mimi.入门;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * create by gary 2019/12/11
 * 技术交流请加QQ:498982703
 */
public class ForwardDemo {
    static class  TargetActor extends AbstractActor{
        @Override
        public Receive createReceive() {
            System.out.println(getSender());
            return receiveBuilder().matchAny(System.out::println).build();
        }
    }

    static class ForwardActor extends AbstractActor{

        private ActorRef target = getContext().actorOf(Props.create(TargetActor.class), "targetActor");

        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny((msg) -> {
                target.forward(msg, getContext());
            }).build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");

        ActorRef forwardDemo = system.actorOf(Props.create(ForwardActor.class), "forwardDemo");
        forwardDemo.tell("123", ActorRef.noSender());


    }

}
