package com.mimi.akka;

import akka.actor.*;

/**
 * create by gary 2019/12/8
 * 技术交流请加QQ:498982703
 * 停止 PoisonPill.getInstance()
 */
public class HelloWorld7 {


    static class ActorDemo extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class, (msg) -> {
                System.out.println("优惠1000");
            }).build();

        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref1 = system.actorOf(Props.create(ActorDemo.class), "actorDemo1");
        ref1.tell("1", ActorRef.noSender());//1000
        ref1.tell(PoisonPill.getInstance(), ActorRef.noSender());
        ref1.tell("2", ActorRef.noSender());



    }


}
