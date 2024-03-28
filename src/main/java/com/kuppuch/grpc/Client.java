package com.kuppuch.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Жми цифры, чтобы увидеть результат");
            System.out.println("1 - последовательный ввод двух чисел. Возвращает сумму. Унарный запрос-ответ");
            System.out.println("2 - пустой запрос. В ответ возвращает поток данных с сервера. Server-side streaming");
            System.out.println("0 - выход");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    System.out.println("Число 1:");
                    double first = scanner.nextDouble();
                    System.out.println("Число 2:");
                    double second = scanner.nextDouble();
                    singleRequest(first, second);
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

    public static void singleRequest(double first, double second) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080").usePlaintext().build();

        CoordinateServiceGrpc.CoordinateServiceBlockingStub stub = CoordinateServiceGrpc.newBlockingStub(channel);

        GreetingService.CoordinateRequest request = GreetingService.CoordinateRequest.newBuilder().
                setLatitude(first).
                setLongitude(second).
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
