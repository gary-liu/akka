package com.mimi.高级.Future;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.pattern.AskTimeoutException;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * create by gary 2019/12/12
 * 技术交流请加QQ:498982703
 */
public class FutureDemo {

    static class FutureActor extends AbstractActor{
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                Thread.sleep(1000);
                getSender().tell("reply", getSelf());
            }).build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef ref =  system.actorOf(Props.create(FutureActor.class), "futureActor");

        Timeout timeout = new Timeout(Duration.create(3, "seconds"));

        Future<Object> future = Patterns.ask(ref, "hello future", timeout);

        try {
            // Await同步获取响应，如果超时了则会抛出java.util.concurrent.TimeoutException
            String result = (String) Await.result(future, timeout.duration());
            System.out.println(result);

            future.onSuccess(new OnSuccess<Object>() {
                @Override
                public void onSuccess(Object result) throws Throwable {
                    System.out.println("receive:"+result);
                }
            }, system.dispatcher());


            future.onFailure(new OnFailure() {
                @Override
                public void onFailure(Throwable failure) throws Throwable {
                    if (failure instanceof AskTimeoutException)
                        System.out.println("超时异常");
                    else
                        System.out.println("其它异常:" + failure);
                }
            }, system.dispatcher());


            future.onComplete(new OnComplete<Object>() {
                @Override
                public void onComplete(Throwable failure, Object success) throws Throwable {
                    if (failure != null) {
                        System.out.println("出异常了");
                    } else {
                        System.out.println(success);

                    }
                }
            }, system.dispatcher());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
