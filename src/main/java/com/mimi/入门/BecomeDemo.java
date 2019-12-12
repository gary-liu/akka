package com.mimi.入门;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * create by gary 2019/12/8
 * 技术交流请加QQ:498982703
 */
public class BecomeDemo {

    static class ActorDemo extends AbstractActor{
        private Integer count =0;

        Receive receiveB = new ReceiveBuilder().matchAny(msg -> {
            System.out.println("优惠500");

        }).build();


        @Override
        public Receive createReceive() {
            return new ReceiveBuilder().matchAny(msg -> {
                count ++;
                if (count >= 3) {
                    getContext().become(receiveB);
                }else {
                    System.out.println("优惠1000");

                }

            }).build();


        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef actorRef = system.actorOf(Props.create(ActorDemo.class), "ActoreDemo");

        actorRef.tell("1", ActorRef.noSender());
        actorRef.tell("2", ActorRef.noSender());
        actorRef.tell("3", ActorRef.noSender());
        actorRef.tell("4", ActorRef.noSender());



    }
}
