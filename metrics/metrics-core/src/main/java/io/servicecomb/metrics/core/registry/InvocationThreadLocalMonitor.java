/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.metrics.core.registry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InvocationThreadLocalMonitor {
  private final Map<String, InvocationThreadLocalCache> caches;

  public InvocationThreadLocalMonitor() {
    this.caches = new ConcurrentHashMap<>();
  }

  public InvocationThreadLocalCache getInvocationThreadLocalCache(String operationName) {
    InvocationThreadLocalCache model = caches.get(operationName);
    if (model == null) {
      model = caches.computeIfAbsent(operationName, m -> new InvocationThreadLocalCache(operationName));
    }
    return model;
  }

  public List<InvocationThreadLocalCache> collectInvocationThreadLocalCache() {
    return caches.values().stream().map(InvocationThreadLocalCache::collect).collect(Collectors.toList());
  }
}
