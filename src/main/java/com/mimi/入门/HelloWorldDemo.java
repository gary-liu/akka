package com.mimi.入门;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

/**
 * create by gary 2019/12/11
 * 技术交流请加QQ:498982703
 */
public class HelloWorldDemo extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(this.getContext().getSystem(), this);

    // 可以通过工厂方式创建actor, 一次模板，到处创建
    public static Props createProps() {
        return Props.create(new Creator<Actor>() {
            @Override
            public Actor create() throws Exception {
                return new HelloWorldDemo();
            }
        });

    }


    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(o -> {
            log.info("any" + o.toString());
        }).matchEquals("hello", s -> log.info("equals" + s))
                .match(String.class, s -> log.info(s))
                .build();
    }


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");

        ActorRef actorRef = system.actorOf(HelloWorldDemo.createProps(), "actorDemo");
        // ActorRef.noSender()实际上就是叫做deadLetters的actor
        actorRef.tell("hello", ActorRef.noSender());
    }
}
