package com.github.starter.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.29.0)",
    comments = "Source: todo.proto")
public final class TodoServiceGrpc {

  private TodoServiceGrpc() {}

  public static final String SERVICE_NAME = "todo.TodoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.github.starter.proto.Todos.SearchRequest,
      com.github.starter.proto.Todos.TodoList> getGetTodosMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getTodos",
      requestType = com.github.starter.proto.Todos.SearchRequest.class,
      responseType = com.github.starter.proto.Todos.TodoList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.starter.proto.Todos.SearchRequest,
      com.github.starter.proto.Todos.TodoList> getGetTodosMethod() {
    io.grpc.MethodDescriptor<com.github.starter.proto.Todos.SearchRequest, com.github.starter.proto.Todos.TodoList> getGetTodosMethod;
    if ((getGetTodosMethod = TodoServiceGrpc.getGetTodosMethod) == null) {
      synchronized (TodoServiceGrpc.class) {
        if ((getGetTodosMethod = TodoServiceGrpc.getGetTodosMethod) == null) {
          TodoServiceGrpc.getGetTodosMethod = getGetTodosMethod =
              io.grpc.MethodDescriptor.<com.github.starter.proto.Todos.SearchRequest, com.github.starter.proto.Todos.TodoList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getTodos"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.starter.proto.Todos.SearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.starter.proto.Todos.TodoList.getDefaultInstance()))
              .setSchemaDescriptor(new TodoServiceMethodDescriptorSupplier("getTodos"))
              .build();
        }
      }
    }
    return getGetTodosMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.starter.proto.Todos.Todo,
      com.github.starter.proto.Todos.Todo> getSaveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "save",
      requestType = com.github.starter.proto.Todos.Todo.class,
      responseType = com.github.starter.proto.Todos.Todo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.starter.proto.Todos.Todo,
      com.github.starter.proto.Todos.Todo> getSaveMethod() {
    io.grpc.MethodDescriptor<com.github.starter.proto.Todos.Todo, com.github.starter.proto.Todos.Todo> getSaveMethod;
    if ((getSaveMethod = TodoServiceGrpc.getSaveMethod) == null) {
      synchronized (TodoServiceGrpc.class) {
        if ((getSaveMethod = TodoServiceGrpc.getSaveMethod) == null) {
          TodoServiceGrpc.getSaveMethod = getSaveMethod =
              io.grpc.MethodDescriptor.<com.github.starter.proto.Todos.Todo, com.github.starter.proto.Todos.Todo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "save"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.starter.proto.Todos.Todo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.starter.proto.Todos.Todo.getDefaultInstance()))
              .setSchemaDescriptor(new TodoServiceMethodDescriptorSupplier("save"))
              .build();
        }
      }
    }
    return getSaveMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.starter.proto.Todos.Todo,
      com.github.starter.proto.Todos.Todo> getUpdateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "update",
      requestType = com.github.starter.proto.Todos.Todo.class,
      responseType = com.github.starter.proto.Todos.Todo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.starter.proto.Todos.Todo,
      com.github.starter.proto.Todos.Todo> getUpdateMethod() {
    io.grpc.MethodDescriptor<com.github.starter.proto.Todos.Todo, com.github.starter.proto.Todos.Todo> getUpdateMethod;
    if ((getUpdateMethod = TodoServiceGrpc.getUpdateMethod) == null) {
      synchronized (TodoServiceGrpc.class) {
        if ((getUpdateMethod = TodoServiceGrpc.getUpdateMethod) == null) {
          TodoServiceGrpc.getUpdateMethod = getUpdateMethod =
              io.grpc.MethodDescriptor.<com.github.starter.proto.Todos.Todo, com.github.starter.proto.Todos.Todo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.starter.proto.Todos.Todo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.starter.proto.Todos.Todo.getDefaultInstance()))
              .setSchemaDescriptor(new TodoServiceMethodDescriptorSupplier("update"))
              .build();
        }
      }
    }
    return getUpdateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      com.github.starter.proto.Todos.Todo> getFindByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findById",
      requestType = com.google.protobuf.StringValue.class,
      responseType = com.github.starter.proto.Todos.Todo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      com.github.starter.proto.Todos.Todo> getFindByIdMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.StringValue, com.github.starter.proto.Todos.Todo> getFindByIdMethod;
    if ((getFindByIdMethod = TodoServiceGrpc.getFindByIdMethod) == null) {
      synchronized (TodoServiceGrpc.class) {
        if ((getFindByIdMethod = TodoServiceGrpc.getFindByIdMethod) == null) {
          TodoServiceGrpc.getFindByIdMethod = getFindByIdMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.StringValue, com.github.starter.proto.Todos.Todo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "findById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.StringValue.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.starter.proto.Todos.Todo.getDefaultInstance()))
              .setSchemaDescriptor(new TodoServiceMethodDescriptorSupplier("findById"))
              .build();
        }
      }
    }
    return getFindByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      com.google.protobuf.BoolValue> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete",
      requestType = com.google.protobuf.StringValue.class,
      responseType = com.google.protobuf.BoolValue.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      com.google.protobuf.BoolValue> getDeleteMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.StringValue, com.google.protobuf.BoolValue> getDeleteMethod;
    if ((getDeleteMethod = TodoServiceGrpc.getDeleteMethod) == null) {
      synchronized (TodoServiceGrpc.class) {
        if ((getDeleteMethod = TodoServiceGrpc.getDeleteMethod) == null) {
          TodoServiceGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.StringValue, com.google.protobuf.BoolValue>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.StringValue.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.BoolValue.getDefaultInstance()))
              .setSchemaDescriptor(new TodoServiceMethodDescriptorSupplier("delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.starter.proto.Todos.Params,
      com.github.starter.proto.Todos.Status> getStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "status",
      requestType = com.github.starter.proto.Todos.Params.class,
      responseType = com.github.starter.proto.Todos.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.starter.proto.Todos.Params,
      com.github.starter.proto.Todos.Status> getStatusMethod() {
    io.grpc.MethodDescriptor<com.github.starter.proto.Todos.Params, com.github.starter.proto.Todos.Status> getStatusMethod;
    if ((getStatusMethod = TodoServiceGrpc.getStatusMethod) == null) {
      synchronized (TodoServiceGrpc.class) {
        if ((getStatusMethod = TodoServiceGrpc.getStatusMethod) == null) {
          TodoServiceGrpc.getStatusMethod = getStatusMethod =
              io.grpc.MethodDescriptor.<com.github.starter.proto.Todos.Params, com.github.starter.proto.Todos.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "status"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.starter.proto.Todos.Params.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.starter.proto.Todos.Status.getDefaultInstance()))
              .setSchemaDescriptor(new TodoServiceMethodDescriptorSupplier("status"))
              .build();
        }
      }
    }
    return getStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TodoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TodoServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TodoServiceStub>() {
        @java.lang.Override
        public TodoServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TodoServiceStub(channel, callOptions);
        }
      };
    return TodoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TodoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TodoServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TodoServiceBlockingStub>() {
        @java.lang.Override
        public TodoServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TodoServiceBlockingStub(channel, callOptions);
        }
      };
    return TodoServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TodoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TodoServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TodoServiceFutureStub>() {
        @java.lang.Override
        public TodoServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TodoServiceFutureStub(channel, callOptions);
        }
      };
    return TodoServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TodoServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getTodos(com.github.starter.proto.Todos.SearchRequest request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.TodoList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTodosMethod(), responseObserver);
    }

    /**
     */
    public void save(com.github.starter.proto.Todos.Todo request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Todo> responseObserver) {
      asyncUnimplementedUnaryCall(getSaveMethod(), responseObserver);
    }

    /**
     */
    public void update(com.github.starter.proto.Todos.Todo request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Todo> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMethod(), responseObserver);
    }

    /**
     */
    public void findById(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Todo> responseObserver) {
      asyncUnimplementedUnaryCall(getFindByIdMethod(), responseObserver);
    }

    /**
     */
    public void delete(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<com.google.protobuf.BoolValue> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     */
    public void status(com.github.starter.proto.Todos.Params request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Status> responseObserver) {
      asyncUnimplementedUnaryCall(getStatusMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetTodosMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.github.starter.proto.Todos.SearchRequest,
                com.github.starter.proto.Todos.TodoList>(
                  this, METHODID_GET_TODOS)))
          .addMethod(
            getSaveMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.github.starter.proto.Todos.Todo,
                com.github.starter.proto.Todos.Todo>(
                  this, METHODID_SAVE)))
          .addMethod(
            getUpdateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.github.starter.proto.Todos.Todo,
                com.github.starter.proto.Todos.Todo>(
                  this, METHODID_UPDATE)))
          .addMethod(
            getFindByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.StringValue,
                com.github.starter.proto.Todos.Todo>(
                  this, METHODID_FIND_BY_ID)))
          .addMethod(
            getDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.StringValue,
                com.google.protobuf.BoolValue>(
                  this, METHODID_DELETE)))
          .addMethod(
            getStatusMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.github.starter.proto.Todos.Params,
                com.github.starter.proto.Todos.Status>(
                  this, METHODID_STATUS)))
          .build();
    }
  }

  /**
   */
  public static final class TodoServiceStub extends io.grpc.stub.AbstractAsyncStub<TodoServiceStub> {
    private TodoServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TodoServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TodoServiceStub(channel, callOptions);
    }

    /**
     */
    public void getTodos(com.github.starter.proto.Todos.SearchRequest request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.TodoList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTodosMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void save(com.github.starter.proto.Todos.Todo request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Todo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(com.github.starter.proto.Todos.Todo request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Todo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findById(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Todo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFindByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<com.google.protobuf.BoolValue> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void status(com.github.starter.proto.Todos.Params request,
        io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Status> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TodoServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<TodoServiceBlockingStub> {
    private TodoServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TodoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TodoServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.github.starter.proto.Todos.TodoList getTodos(com.github.starter.proto.Todos.SearchRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetTodosMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.starter.proto.Todos.Todo save(com.github.starter.proto.Todos.Todo request) {
      return blockingUnaryCall(
          getChannel(), getSaveMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.starter.proto.Todos.Todo update(com.github.starter.proto.Todos.Todo request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.starter.proto.Todos.Todo findById(com.google.protobuf.StringValue request) {
      return blockingUnaryCall(
          getChannel(), getFindByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.BoolValue delete(com.google.protobuf.StringValue request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.starter.proto.Todos.Status status(com.github.starter.proto.Todos.Params request) {
      return blockingUnaryCall(
          getChannel(), getStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TodoServiceFutureStub extends io.grpc.stub.AbstractFutureStub<TodoServiceFutureStub> {
    private TodoServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TodoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TodoServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.starter.proto.Todos.TodoList> getTodos(
        com.github.starter.proto.Todos.SearchRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTodosMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.starter.proto.Todos.Todo> save(
        com.github.starter.proto.Todos.Todo request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.starter.proto.Todos.Todo> update(
        com.github.starter.proto.Todos.Todo request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.starter.proto.Todos.Todo> findById(
        com.google.protobuf.StringValue request) {
      return futureUnaryCall(
          getChannel().newCall(getFindByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.BoolValue> delete(
        com.google.protobuf.StringValue request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.starter.proto.Todos.Status> status(
        com.github.starter.proto.Todos.Params request) {
      return futureUnaryCall(
          getChannel().newCall(getStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_TODOS = 0;
  private static final int METHODID_SAVE = 1;
  private static final int METHODID_UPDATE = 2;
  private static final int METHODID_FIND_BY_ID = 3;
  private static final int METHODID_DELETE = 4;
  private static final int METHODID_STATUS = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TodoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TodoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_TODOS:
          serviceImpl.getTodos((com.github.starter.proto.Todos.SearchRequest) request,
              (io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.TodoList>) responseObserver);
          break;
        case METHODID_SAVE:
          serviceImpl.save((com.github.starter.proto.Todos.Todo) request,
              (io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Todo>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((com.github.starter.proto.Todos.Todo) request,
              (io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Todo>) responseObserver);
          break;
        case METHODID_FIND_BY_ID:
          serviceImpl.findById((com.google.protobuf.StringValue) request,
              (io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Todo>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((com.google.protobuf.StringValue) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.BoolValue>) responseObserver);
          break;
        case METHODID_STATUS:
          serviceImpl.status((com.github.starter.proto.Todos.Params) request,
              (io.grpc.stub.StreamObserver<com.github.starter.proto.Todos.Status>) responseObserver);
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

  private static abstract class TodoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TodoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.starter.proto.Todos.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TodoService");
    }
  }

  private static final class TodoServiceFileDescriptorSupplier
      extends TodoServiceBaseDescriptorSupplier {
    TodoServiceFileDescriptorSupplier() {}
  }

  private static final class TodoServiceMethodDescriptorSupplier
      extends TodoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TodoServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (TodoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TodoServiceFileDescriptorSupplier())
              .addMethod(getGetTodosMethod())
              .addMethod(getSaveMethod())
              .addMethod(getUpdateMethod())
              .addMethod(getFindByIdMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
