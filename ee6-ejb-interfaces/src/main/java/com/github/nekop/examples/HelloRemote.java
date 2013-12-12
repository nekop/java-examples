package com.github.nekop.examples;

import javax.ejb.Remote;

@Remote
public interface HelloRemote {
    String hello(String name);
}
