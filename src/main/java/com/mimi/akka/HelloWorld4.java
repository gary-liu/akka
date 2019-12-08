package com.mimi.akka;

import akka.actor.*;

/**
 * create by gary 2019/12/8
 * 技术交流请加QQ:498982703
 * 打印actorRef 对象 找对到对应的actorRef 向他发给消息
 *
 */
public class HelloWorld4 {

    static class ActorDemo extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class, (msg) -> {

                System.out.println(msg);

            }).build();

        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref1 = system.actorOf(Props.create(ActorDemo.class), "actorDemo1");
//        System.out.println(ref);
        ActorRef ref2 = system.actorOf(Props.create(ActorDemo.class), "actorDemo2");

        ActorSelection actorSelection = system.actorSelection("akka://sys/user/actorDemo*");
        actorSelection.tell("123", ActorRef.noSender());


    }


}
