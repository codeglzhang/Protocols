package com.example.demo;

import com.example.demo.protocol.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	@Autowired
	NettyServer nettyServer2;
	@Autowired
	NettyServer nettyServer;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
//		int a;
//		byte b= (byte) 0xff;
//		a=b&0xff;
//		System.out.println(a);
	}

	@Override
	public void run(String... args) throws Exception {
		nettyServer.run();
	}
}
