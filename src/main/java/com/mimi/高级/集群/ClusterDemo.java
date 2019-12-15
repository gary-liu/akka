package com.mimi.高级.集群;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * create by gary 2019/12/15
 * 技术交流请加QQ:498982703
 */
public class ClusterDemo extends AbstractActor {

    Cluster cluster = Cluster.get(getContext().system());


    @Override
    public void preStart() throws Exception {
        // 让当前 Actor 订阅 UnreachableMember 、 MemberEvent 事件，
        // 其中 UnreachableMember 事件会在某个节点被故障检测器（ failure detector ）
        // 认定为不可达（ unreachable ）时触发
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
                ClusterEvent.UnreachableMember.class, ClusterEvent.MemberEvent.class);

    }

    @Override
    public void postStop() throws Exception {
        cluster.unsubscribe(getSelf());

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(message -> {
            if(message instanceof ClusterEvent.MemberUp){
                ClusterEvent.MemberUp memberUp = (ClusterEvent.MemberUp) message;
                Member member = memberUp.member();
                System.out.println("-->Member" + member + "is Up:the role is" + member.roles());

            }else if(message instanceof ClusterEvent.UnreachableMember){
                ClusterEvent.UnreachableMember memberUp = (ClusterEvent.UnreachableMember) message;
                Member member = memberUp.member();
                System.out.println("-->Member" + member + "detected as unreachable: the role is" + member.roles());

            }else if(message instanceof ClusterEvent.MemberRemoved){
                ClusterEvent.MemberRemoved memberUp = (ClusterEvent.MemberRemoved) message;
                Member member = memberUp.member();
                System.out.println("-->Member" + member + "is Removed: the role i" + member.roles());

            }else if(message instanceof ClusterEvent.MemberEvent){
                ClusterEvent.MemberEvent memberUp = (ClusterEvent.MemberEvent) message;
                Member member = memberUp.member();
                System.out.println("-->MemverEvent: " + memberUp + "" + member.roles());

            }else {

                System.out.println("-->Other:" + message);
                unhandled(message);

            }

        }).build();
    }

    public static void main(String[] args) {
        String port = args[0];
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
                .withFallback(ConfigFactory.load("cluster.conf"));

        ActorSystem system = ActorSystem.create("sys", config);
        system.actorOf(Props.create(ClusterDemo.class), "clusterDemo" + port);
    }
}
