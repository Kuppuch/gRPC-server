package com.kuppuch.grpc;

import io.grpc.stub.StreamObserver;

import java.util.Collection;
import java.util.Random;

public class CoordinateServiceImpl extends CoordinateServiceGrpc.CoordinateServiceImplBase {

    @Override
    public void coordinate(GreetingService.CoordinateRequest request,
                           StreamObserver<GreetingService.CoordinateResponse> responseObserver) {
        GreetingService.CoordinateResponse response = GreetingService.CoordinateResponse.newBuilder().
                setSumm(request.getLatitude() + request.getLongitude()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void randNumbers(GreetingService.EmptyMessage request,
                            StreamObserver<GreetingService.CoordinateResponse> responseObserver) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GreetingService.CoordinateResponse response = GreetingService.CoordinateResponse.newBuilder().
                    setSumm(random.nextDouble()).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}
