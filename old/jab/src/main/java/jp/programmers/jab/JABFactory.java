package jp.programmers.jab;

public class JABFactory {

    public static JAB create(String type) {
        if (type == null || "".equals(type)) {
            return new StandardJAB();
        } else if ("netty-oio".equals(type)) {
            return new NettyOioJAB();
        } else if ("netty-nio".equals(type)) {
            return new NettyNioJAB();
        } else {
            System.out.println("Unknown type, will use StandardJAB: " + type);
            return new StandardJAB();
        }
    }
}
