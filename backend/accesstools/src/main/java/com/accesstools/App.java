package com.accesstools;

public class App {
    public static void main(String[] args) {
        AccessNode node = AccessNode.builder()
                .setReadType(AccessReadType.RW)
                .setNodeType(AccessNodeType.GROUP)
                .setName("user1")
                .build();
        System.out.println(AccessUtils.checkOnWrite(node, "user1"));
    }
}
