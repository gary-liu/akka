package com.mimi.高级.扩展机制;

import akka.actor.AbstractExtensionId;
import akka.actor.ActorSystem;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import com.typesafe.config.Config;

/**
 * create by gary 2019/12/15
 * 技术交流请加QQ:498982703
 */
public class ExtensionDemo {

    static class RPCExtension implements Extension{
        private String server;

        private int port;

        public RPCExtension(String server, int port) {
            this.server = server;
            this.port = port;
        }

        public void rpcCall(String cmd) {
            System.out.println("call" + cmd + "-->" + server + ":" + port);
        }
    }


    static class RPCExtProvider extends AbstractExtensionId<RPCExtension>{
        private static RPCExtProvider provider = new RPCExtProvider();

        @Override
        public RPCExtension createExtension(ExtendedActorSystem system) {
            Config config = system.settings().config();
            String server = config.getString("akkademo.server");
            int port = config.getInt("akkademo.port");
            return new RPCExtension(server, port);

        }

        public static RPCExtProvider getInstance() {
            return provider;
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        RPCExtension rpcExtension = RPCExtProvider.getInstance().get(system);
        rpcExtension.rpcCall("hello");

    }
}
