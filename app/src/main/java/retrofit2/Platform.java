/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit2;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.google.firebase.crashlytics.buildtools.reloc.org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

class Platform {
  private static final Platform PLATFORM = findPlatform();

  static Platform get() {
    return PLATFORM;
  }

  //构建对应平台 利Class.forName("android.os.Build")
  private static Platform findPlatform() {
    try {
      Class.forName("android.os.Build");
      if (Build.VERSION.SDK_INT != 0) {
        return new Android();
      }
    } catch (ClassNotFoundException ignored) {
    }
    try {
      Class.forName("java.util.Optional");
      return new Java8();
    } catch (ClassNotFoundException ignored) {

    }
    try {
      Class.forName("org.robovm.apple.foundation.NSObject");
      return new IOS();
    } catch (ClassNotFoundException ignored) {

    }
    return new Platform();
  }

  Executor defaultCallbackExecutor() {
    return null;
  }

  CallAdapter.Factory defaultCallAdapterFactory(Executor callbackExecutor) {
    if (callbackExecutor != null) {
      return new ExecutorCallAdapterFactory(callbackExecutor);
    }
    return DefaultCallAdapterFactory.INSTANCE;
  }

  boolean isDefaultMethod(Method method) {
    return false;
  }

  Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object, Object... args)
      throws Throwable {
    throw new UnsupportedOperationException();
  }

  @IgnoreJRERequirement // Only classloaded and used on Java 8.
  static class Java8 extends Platform {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override boolean isDefaultMethod(Method method) {
      return method.isDefault();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object,
                                         Object... args) throws Throwable {
      // Because the service interface might not be public, we need to use a MethodHandle lookup
      // that ignores the visibility of the declaringClass.
      Constructor<Lookup> constructor = Lookup.class.getDeclaredConstructor(Class.class, int.class);
      constructor.setAccessible(true);
      return constructor.newInstance(declaringClass, -1 /* trusted */)
          .unreflectSpecial(method, declaringClass)
          .bindTo(object)
          .invokeWithArguments(args);
    }
  }

  static class Android extends Platform {
    @Override public Executor defaultCallbackExecutor() {
      return new MainThreadExecutor();
    }

    @Override CallAdapter.Factory defaultCallAdapterFactory(Executor callbackExecutor) {
      return new ExecutorCallAdapterFactory(callbackExecutor);
    }

    static class MainThreadExecutor implements Executor {
      private final Handler handler = new Handler(Looper.getMainLooper());

      @Override public void execute(Runnable r) {
        handler.post(r);
      }
    }
  }

  static class IOS extends Platform {
    @Override public Executor defaultCallbackExecutor() {
      return new MainThreadExecutor();
    }

    @Override CallAdapter.Factory defaultCallAdapterFactory(Executor callbackExecutor) {
      return new ExecutorCallAdapterFactory(callbackExecutor);
    }

    static class MainThreadExecutor implements Executor {
      private static Object queue;
      private static Method addOperation;

      static {
        try {
          // queue = NSOperationQueue.getMainQueue();
          Class<?> operationQueue = Class.forName("org.robovm.apple.foundation.NSOperationQueue");
          queue = operationQueue.getDeclaredMethod("getMainQueue").invoke(null);
          addOperation = operationQueue.getDeclaredMethod("addOperation", Runnable.class);
        } catch (Exception e) {
          throw new AssertionError(e);
        }
      }

      @Override public void execute(Runnable r) {
        try {
          // queue.addOperation(r);
          addOperation.invoke(queue, r);
        } catch (IllegalArgumentException | IllegalAccessException e) {
          throw new AssertionError(e);
        } catch (InvocationTargetException e) {
          Throwable cause = e.getCause();
          if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
          } else if (cause instanceof Error) {
            throw (Error) cause;
          }
          throw new RuntimeException(cause);
        }
      }
    }
  }
}
