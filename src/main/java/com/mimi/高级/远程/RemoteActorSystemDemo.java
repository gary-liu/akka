package com.mimi.高级.远程;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;

/**
 * create by gary 2019/12/13
 * 技术交流请加QQ:498982703
 */
public class RemoteActorSystemDemo {

    static class SimpleActor extends AbstractActor{
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg->{
                System.out.println(msg);
            }).build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("remote.conf"));

        ActorRef actorRef = system.actorOf(Props.create(SimpleActor.class), "simpleActor");

        ActorSelection actorSelection = system.actorSelection("akka.tcp://sys@127.0.0.1:2552/user/simpleActor");

        actorSelection.tell("12", ActorRef.noSender());




    }
}
