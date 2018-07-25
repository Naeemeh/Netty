package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 客户端
 *
 * @author fengjing
 */
public class ChatClient {

    private String host;
    private int port;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new ChatClient("127.0.0.1", 8079).run("1");
    }

    public void run(String messageEnum) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Channel channel = null;
        try {

            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            channel = bootstrap.connect(host, port).sync().channel();

            channel.writeAndFlush(messageEnum);

            if(messageEnum.equals("2")){
                channel.close();
            }


        } finally {

//            group.shutdownGracefully();
        }
    }

}
