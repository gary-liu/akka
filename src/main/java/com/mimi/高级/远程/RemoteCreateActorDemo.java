package com.mimi.高级.远程;

import akka.actor.*;
import akka.remote.RemoteScope;
import com.typesafe.config.ConfigFactory;

/**
 * create by gary 2019/12/13
 * 技术交流请加QQ:498982703
 */
public class RemoteCreateActorDemo {

    static class RemoteCreateActor extends AbstractActor{
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                System.out.println("remote msg ==" + msg);
            }).build();
        }
    }


    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("remoteactor.conf"));
//
//        ActorRef ref = system.actorOf(Props.create(RemoteCreateActor.class), "rmtCrtActor");
//        ref.tell("hello rmt", ActorRef.noSender());

        Address address = new Address("akka.tcp", "sys", "127.0.0.1", 2552);
        ActorRef ref = system.actorOf(Props.create(RemoteCreateActor.class).withDeploy(new Deploy(new RemoteScope(address))), "rmtCrtActor");
        ref.tell("hello rmt", ActorRef.noSender());








    }

}
