package com.mimi.akka;

import akka.actor.*;

/**
 * create by gary 2019/12/8
 * 技术交流请加QQ:498982703
 *
 * become unbecome
 */
public class HelloWorld5 {


    static class ActorDemo extends AbstractActor{
        private int count;

        Receive receive = receiveBuilder().matchAny((msg) ->{

            if ("unbecome".equals(msg)) {
                getContext().unbecome();

            }else {
                System.out.println("优惠500");

            }

        }).build();



        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class, (msg) -> {
                count++;
                System.out.println("优惠1000");
                if (count == 3) {
                    getContext().become(receive);

                }
            }).build();

        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref1 = system.actorOf(Props.create(ActorDemo.class), "actorDemo1");
        ref1.tell("1",ActorRef.noSender());
        ref1.tell("2",ActorRef.noSender());
        ref1.tell("3",ActorRef.noSender());
        ref1.tell("4",ActorRef.noSender());
        ref1.tell("unbecome", ActorRef.noSender());
        ref1.tell("6", ActorRef.noSender());

    }


}
