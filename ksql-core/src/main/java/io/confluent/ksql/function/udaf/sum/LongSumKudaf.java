/**
 * Copyright 2017 Confluent Inc.
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
 **/

package io.confluent.ksql.function.udaf.sum;

import io.confluent.ksql.function.KsqlAggregateFunction;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.streams.kstream.Merger;

import java.util.Arrays;

public class LongSumKudaf extends KsqlAggregateFunction<Long, Long> {

  public LongSumKudaf(Integer argIndexInValue) {
    super(argIndexInValue, 0L, Schema.INT64_SCHEMA,
          Arrays.asList(Schema.INT64_SCHEMA), "SUM", LongSumKudaf.class);
  }

  @Override
  public Long aggregate(Long currentVal, Long currentAggVal) {
    return currentVal + currentAggVal;
  }

  @Override
  public Merger getMerger() {
    return new Merger<String, Long>() {
      @Override
      public Long apply(final String aggKey, final Long aggOne, final Long aggTwo) {
        return aggOne + aggTwo;
      }
    };
  }
}
