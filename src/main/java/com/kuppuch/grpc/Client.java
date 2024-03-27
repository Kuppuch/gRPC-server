package com.kuppuch.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    singleRequest();
                    break;
                case "2":
                    streamRequest();
                    break;
                case "0":
                    return;
                default:
                    continue;
            }
        }
    }

    public static void singleRequest() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080").usePlaintext().build();

        CoordinateServiceGrpc.CoordinateServiceBlockingStub stub = CoordinateServiceGrpc.newBlockingStub(channel);

        GreetingService.CoordinateRequest request = GreetingService.CoordinateRequest.newBuilder().
                setLatitude(80.0).
                setLongitude(20.0).
                build();

        GreetingService.CoordinateResponse response = stub.coordinate(request);

        System.out.println(response);
        channel.shutdown();
    }

    public static void streamRequest() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080").usePlaintext().build();

        CoordinateServiceGrpc.CoordinateServiceBlockingStub stub = CoordinateServiceGrpc.newBlockingStub(channel);

        GreetingService.EmptyMessage request = GreetingService.EmptyMessage.newBuilder().build();

        Iterator<GreetingService.CoordinateResponse> responseIterator = stub.randNumbers(request);

        while (responseIterator.hasNext())
            System.out.println(responseIterator.next());

        channel.shutdown();
    }
}
