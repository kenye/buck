/*
 * Copyright 2012-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.rules;

import com.facebook.buck.android.AndroidPlatformTarget;
import com.facebook.buck.event.BuckEventBus;
import com.facebook.buck.event.ConsoleEvent;
import com.facebook.buck.event.ThrowableConsoleEvent;
import com.facebook.buck.jvm.core.JavaPackageFinder;
import com.facebook.buck.util.immutables.BuckStyleImmutable;
import com.google.common.base.Supplier;

import org.immutables.value.Value;

@Value.Immutable
@BuckStyleImmutable
abstract class AbstractBuildContext {

  public abstract ActionGraph getActionGraph();

  public abstract JavaPackageFinder getJavaPackageFinder();
  public abstract BuckEventBus getEventBus();

  @Value.Default
  public Supplier<AndroidPlatformTarget> getAndroidPlatformTargetSupplier() {
    return AndroidPlatformTarget.EXPLODING_ANDROID_PLATFORM_TARGET_SUPPLIER;
  }

  @Value.Default
  public boolean shouldReportAbsolutePaths() {
    return false;
  }

  public void logBuildInfo(String format, Object... args) {
    getEventBus().post(ConsoleEvent.fine(format, args));
  }

  public void logError(Throwable error, String msg, Object... formatArgs) {
    getEventBus().post(ThrowableConsoleEvent.create(error, msg, formatArgs));
  }

  public void logError(String msg, Object... formatArgs) {
    getEventBus().post(ConsoleEvent.severe(msg, formatArgs));
  }
}