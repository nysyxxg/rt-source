package jdk.management.resource.internal.inst;

import java.io.FileDescriptor;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import jdk.internal.instrumentation.InstrumentationMethod;
import jdk.internal.instrumentation.InstrumentationTarget;
import jdk.management.resource.ResourceRequest;
import jdk.management.resource.ResourceRequestDeniedException;
import jdk.management.resource.internal.ApproverGroup;
import jdk.management.resource.internal.CompletionHandlerWrapper;
import jdk.management.resource.internal.FutureWrapper;
import jdk.management.resource.internal.ResourceIdImpl;

@InstrumentationTarget("sun.nio.ch.WindowsAsynchronousSocketChannelImpl")
public class WindowsAsynchronousSocketChannelImplRMHooks {
  protected final FileDescriptor fd = null;
  
  public final void close() {}
  
  @InstrumentationMethod
  <V extends Number, A> Future<V> implRead(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
    int i;
    ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.localAddress);
    ResourceRequest resourceRequest = ApproverGroup.SOCKET_READ_GROUP.getApprover(this);
    long l = 0L;
    if (paramBoolean) {
      i = 0;
      for (ByteBuffer byteBuffer : paramArrayOfByteBuffer)
        i += byteBuffer.remaining(); 
    } else {
      i = paramByteBuffer.remaining();
    } 
    try {
      l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
      if (l < i)
        throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved"); 
    } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
      if (paramCompletionHandler != null) {
        paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
        return null;
      } 
      CompletableFuture completableFuture = new CompletableFuture();
      completableFuture.completeExceptionally(resourceRequestDeniedException);
      return completableFuture;
    } 
    if (paramCompletionHandler != null)
      paramCompletionHandler = new CompletionHandlerWrapper<V, ? super A>(paramCompletionHandler, resourceIdImpl, resourceRequest, l); 
    Future future = implRead(paramBoolean, paramByteBuffer, paramArrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
    if (paramCompletionHandler == null)
      if (future.isDone()) {
        int j = 0;
        try {
          j = ((Number)future.get()).intValue();
        } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
        j = Math.max(0, j);
        resourceRequest.request(-(l - j), resourceIdImpl);
      } else {
        future = new FutureWrapper(future, resourceIdImpl, resourceRequest, l);
      }  
    return future;
  }
  
  @InstrumentationMethod
  <V extends Number, A> Future<V> implWrite(boolean paramBoolean, ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, long paramLong, TimeUnit paramTimeUnit, A paramA, CompletionHandler<V, ? super A> paramCompletionHandler) {
    int i;
    ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.localAddress);
    ResourceRequest resourceRequest = ApproverGroup.SOCKET_WRITE_GROUP.getApprover(this);
    long l = 0L;
    if (paramBoolean) {
      i = 0;
      for (ByteBuffer byteBuffer : paramArrayOfByteBuffer)
        i += byteBuffer.remaining(); 
    } else {
      i = paramByteBuffer.remaining();
    } 
    try {
      l = Math.max(resourceRequest.request(i, resourceIdImpl), 0L);
      if (l < i)
        throw new ResourceRequestDeniedException("Resource limited: insufficient bytes approved"); 
    } catch (ResourceRequestDeniedException resourceRequestDeniedException) {
      if (paramCompletionHandler != null) {
        paramCompletionHandler.failed(resourceRequestDeniedException, paramA);
        return null;
      } 
      CompletableFuture completableFuture = new CompletableFuture();
      completableFuture.completeExceptionally(resourceRequestDeniedException);
      return completableFuture;
    } 
    if (paramCompletionHandler != null)
      paramCompletionHandler = new CompletionHandlerWrapper<V, ? super A>(paramCompletionHandler, resourceIdImpl, resourceRequest, l); 
    Future future = implWrite(paramBoolean, paramByteBuffer, paramArrayOfByteBuffer, paramLong, paramTimeUnit, paramA, paramCompletionHandler);
    if (paramCompletionHandler == null)
      if (future.isDone()) {
        int j = 0;
        try {
          j = ((Number)future.get()).intValue();
        } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {}
        j = Math.max(0, j);
        resourceRequest.request(-(l - j), resourceIdImpl);
      } else {
        future = new FutureWrapper(future, resourceIdImpl, resourceRequest, l);
      }  
    return future;
  }
  
  @InstrumentationMethod
  void implClose() {
    try {
      implClose();
    } finally {
      ResourceIdImpl resourceIdImpl = ResourceIdImpl.of(this.fd);
      if (resourceIdImpl != null) {
        ResourceRequest resourceRequest = ApproverGroup.FILEDESCRIPTOR_OPEN_GROUP.getApprover(this.fd);
        resourceRequest.request(-1L, resourceIdImpl);
      } 
      if (this.localAddress != null) {
        resourceIdImpl = ResourceIdImpl.of(this.localAddress);
        ResourceRequest resourceRequest = ApproverGroup.SOCKET_OPEN_GROUP.getApprover(this);
        resourceRequest.request(-1L, resourceIdImpl);
      } 
    } 
  }
}


/* Location:              D:\software\jd-gui\jd-gui-windows-1.6.3\rt.jar!\jdk\management\resource\internal\inst\WindowsAsynchronousSocketChannelImplRMHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */