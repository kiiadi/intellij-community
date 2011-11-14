/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author peter
 */
public class ObjectUtils {
  private ObjectUtils() {
  }

  public static final Object NULL = new Object();

  @NotNull
  public static <T> T assertNotNull(@Nullable final T t) {
    return _assertNotNull(t);
  }

  @NotNull
  private static <T> T _assertNotNull(@NotNull T t) {
    return t;
  }

  @Nullable
  public static <T> T chooseNotNull(@Nullable T t1, @Nullable T t2) {
    return t1 == null? t2 : t1;
  }

  @NotNull
  public static <T> T notNull(@Nullable T value, @NotNull T defaultValue) {
    return value != null ? value : defaultValue;
  }
}
