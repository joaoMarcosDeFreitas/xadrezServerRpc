package com.xadrez.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class XadrezServiceGrpc {

  private XadrezServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "XadrezService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.xadrez.grpc.ConectarRequest,
      com.xadrez.grpc.ConectarResponse> getConectarJogadorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConectarJogador",
      requestType = com.xadrez.grpc.ConectarRequest.class,
      responseType = com.xadrez.grpc.ConectarResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.xadrez.grpc.ConectarRequest,
      com.xadrez.grpc.ConectarResponse> getConectarJogadorMethod() {
    io.grpc.MethodDescriptor<com.xadrez.grpc.ConectarRequest, com.xadrez.grpc.ConectarResponse> getConectarJogadorMethod;
    if ((getConectarJogadorMethod = XadrezServiceGrpc.getConectarJogadorMethod) == null) {
      synchronized (XadrezServiceGrpc.class) {
        if ((getConectarJogadorMethod = XadrezServiceGrpc.getConectarJogadorMethod) == null) {
          XadrezServiceGrpc.getConectarJogadorMethod = getConectarJogadorMethod =
              io.grpc.MethodDescriptor.<com.xadrez.grpc.ConectarRequest, com.xadrez.grpc.ConectarResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConectarJogador"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xadrez.grpc.ConectarRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xadrez.grpc.ConectarResponse.getDefaultInstance()))
              .setSchemaDescriptor(new XadrezServiceMethodDescriptorSupplier("ConectarJogador"))
              .build();
        }
      }
    }
    return getConectarJogadorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.xadrez.grpc.JogadaRequest,
      com.xadrez.grpc.JogadaResponse> getEnviarJogadaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EnviarJogada",
      requestType = com.xadrez.grpc.JogadaRequest.class,
      responseType = com.xadrez.grpc.JogadaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.xadrez.grpc.JogadaRequest,
      com.xadrez.grpc.JogadaResponse> getEnviarJogadaMethod() {
    io.grpc.MethodDescriptor<com.xadrez.grpc.JogadaRequest, com.xadrez.grpc.JogadaResponse> getEnviarJogadaMethod;
    if ((getEnviarJogadaMethod = XadrezServiceGrpc.getEnviarJogadaMethod) == null) {
      synchronized (XadrezServiceGrpc.class) {
        if ((getEnviarJogadaMethod = XadrezServiceGrpc.getEnviarJogadaMethod) == null) {
          XadrezServiceGrpc.getEnviarJogadaMethod = getEnviarJogadaMethod =
              io.grpc.MethodDescriptor.<com.xadrez.grpc.JogadaRequest, com.xadrez.grpc.JogadaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EnviarJogada"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xadrez.grpc.JogadaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xadrez.grpc.JogadaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new XadrezServiceMethodDescriptorSupplier("EnviarJogada"))
              .build();
        }
      }
    }
    return getEnviarJogadaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.xadrez.grpc.EstadoRequest,
      com.xadrez.grpc.EstadoResponse> getObterEstadoDoJogoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ObterEstadoDoJogo",
      requestType = com.xadrez.grpc.EstadoRequest.class,
      responseType = com.xadrez.grpc.EstadoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.xadrez.grpc.EstadoRequest,
      com.xadrez.grpc.EstadoResponse> getObterEstadoDoJogoMethod() {
    io.grpc.MethodDescriptor<com.xadrez.grpc.EstadoRequest, com.xadrez.grpc.EstadoResponse> getObterEstadoDoJogoMethod;
    if ((getObterEstadoDoJogoMethod = XadrezServiceGrpc.getObterEstadoDoJogoMethod) == null) {
      synchronized (XadrezServiceGrpc.class) {
        if ((getObterEstadoDoJogoMethod = XadrezServiceGrpc.getObterEstadoDoJogoMethod) == null) {
          XadrezServiceGrpc.getObterEstadoDoJogoMethod = getObterEstadoDoJogoMethod =
              io.grpc.MethodDescriptor.<com.xadrez.grpc.EstadoRequest, com.xadrez.grpc.EstadoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ObterEstadoDoJogo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xadrez.grpc.EstadoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.xadrez.grpc.EstadoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new XadrezServiceMethodDescriptorSupplier("ObterEstadoDoJogo"))
              .build();
        }
      }
    }
    return getObterEstadoDoJogoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static XadrezServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XadrezServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XadrezServiceStub>() {
        @java.lang.Override
        public XadrezServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XadrezServiceStub(channel, callOptions);
        }
      };
    return XadrezServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static XadrezServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XadrezServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XadrezServiceBlockingV2Stub>() {
        @java.lang.Override
        public XadrezServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XadrezServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return XadrezServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static XadrezServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XadrezServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XadrezServiceBlockingStub>() {
        @java.lang.Override
        public XadrezServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XadrezServiceBlockingStub(channel, callOptions);
        }
      };
    return XadrezServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static XadrezServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<XadrezServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<XadrezServiceFutureStub>() {
        @java.lang.Override
        public XadrezServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new XadrezServiceFutureStub(channel, callOptions);
        }
      };
    return XadrezServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void conectarJogador(com.xadrez.grpc.ConectarRequest request,
        io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConectarJogadorMethod(), responseObserver);
    }

    /**
     */
    default void enviarJogada(com.xadrez.grpc.JogadaRequest request,
        io.grpc.stub.StreamObserver<com.xadrez.grpc.JogadaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnviarJogadaMethod(), responseObserver);
    }

    /**
     */
    default void obterEstadoDoJogo(com.xadrez.grpc.EstadoRequest request,
        io.grpc.stub.StreamObserver<com.xadrez.grpc.EstadoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getObterEstadoDoJogoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service XadrezService.
   */
  public static abstract class XadrezServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return XadrezServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service XadrezService.
   */
  public static final class XadrezServiceStub
      extends io.grpc.stub.AbstractAsyncStub<XadrezServiceStub> {
    private XadrezServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XadrezServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XadrezServiceStub(channel, callOptions);
    }

    /**
     */
    public void conectarJogador(com.xadrez.grpc.ConectarRequest request,
        io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConectarJogadorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void enviarJogada(com.xadrez.grpc.JogadaRequest request,
        io.grpc.stub.StreamObserver<com.xadrez.grpc.JogadaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEnviarJogadaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void obterEstadoDoJogo(com.xadrez.grpc.EstadoRequest request,
        io.grpc.stub.StreamObserver<com.xadrez.grpc.EstadoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getObterEstadoDoJogoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service XadrezService.
   */
  public static final class XadrezServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<XadrezServiceBlockingV2Stub> {
    private XadrezServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XadrezServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XadrezServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public com.xadrez.grpc.ConectarResponse conectarJogador(com.xadrez.grpc.ConectarRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getConectarJogadorMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.xadrez.grpc.JogadaResponse enviarJogada(com.xadrez.grpc.JogadaRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getEnviarJogadaMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.xadrez.grpc.EstadoResponse obterEstadoDoJogo(com.xadrez.grpc.EstadoRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getObterEstadoDoJogoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service XadrezService.
   */
  public static final class XadrezServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<XadrezServiceBlockingStub> {
    private XadrezServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XadrezServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XadrezServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.xadrez.grpc.ConectarResponse conectarJogador(com.xadrez.grpc.ConectarRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConectarJogadorMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.xadrez.grpc.JogadaResponse enviarJogada(com.xadrez.grpc.JogadaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEnviarJogadaMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.xadrez.grpc.EstadoResponse obterEstadoDoJogo(com.xadrez.grpc.EstadoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getObterEstadoDoJogoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service XadrezService.
   */
  public static final class XadrezServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<XadrezServiceFutureStub> {
    private XadrezServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected XadrezServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new XadrezServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.xadrez.grpc.ConectarResponse> conectarJogador(
        com.xadrez.grpc.ConectarRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConectarJogadorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.xadrez.grpc.JogadaResponse> enviarJogada(
        com.xadrez.grpc.JogadaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEnviarJogadaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.xadrez.grpc.EstadoResponse> obterEstadoDoJogo(
        com.xadrez.grpc.EstadoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getObterEstadoDoJogoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CONECTAR_JOGADOR = 0;
  private static final int METHODID_ENVIAR_JOGADA = 1;
  private static final int METHODID_OBTER_ESTADO_DO_JOGO = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONECTAR_JOGADOR:
          serviceImpl.conectarJogador((com.xadrez.grpc.ConectarRequest) request,
              (io.grpc.stub.StreamObserver<com.xadrez.grpc.ConectarResponse>) responseObserver);
          break;
        case METHODID_ENVIAR_JOGADA:
          serviceImpl.enviarJogada((com.xadrez.grpc.JogadaRequest) request,
              (io.grpc.stub.StreamObserver<com.xadrez.grpc.JogadaResponse>) responseObserver);
          break;
        case METHODID_OBTER_ESTADO_DO_JOGO:
          serviceImpl.obterEstadoDoJogo((com.xadrez.grpc.EstadoRequest) request,
              (io.grpc.stub.StreamObserver<com.xadrez.grpc.EstadoResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getConectarJogadorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.xadrez.grpc.ConectarRequest,
              com.xadrez.grpc.ConectarResponse>(
                service, METHODID_CONECTAR_JOGADOR)))
        .addMethod(
          getEnviarJogadaMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.xadrez.grpc.JogadaRequest,
              com.xadrez.grpc.JogadaResponse>(
                service, METHODID_ENVIAR_JOGADA)))
        .addMethod(
          getObterEstadoDoJogoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.xadrez.grpc.EstadoRequest,
              com.xadrez.grpc.EstadoResponse>(
                service, METHODID_OBTER_ESTADO_DO_JOGO)))
        .build();
  }

  private static abstract class XadrezServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    XadrezServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.xadrez.grpc.XadrezProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("XadrezService");
    }
  }

  private static final class XadrezServiceFileDescriptorSupplier
      extends XadrezServiceBaseDescriptorSupplier {
    XadrezServiceFileDescriptorSupplier() {}
  }

  private static final class XadrezServiceMethodDescriptorSupplier
      extends XadrezServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    XadrezServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (XadrezServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new XadrezServiceFileDescriptorSupplier())
              .addMethod(getConectarJogadorMethod())
              .addMethod(getEnviarJogadaMethod())
              .addMethod(getObterEstadoDoJogoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
