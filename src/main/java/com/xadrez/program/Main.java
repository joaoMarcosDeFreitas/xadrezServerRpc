package com.xadrez.program;

import com.xadrez.grpc.XadrezServiceImpl;

import io.grpc.ServerBuilder;

public class Main {

	public static void main(String[] args) {
		try {
			ServerBuilder.forPort(50051)
			.addService(new XadrezServiceImpl())
			.build()
			.start()
			.awaitTermination();
			
			
		} catch(Exception e) {
			System.out.println(e.getMessage().toString());
		}
	}

}
