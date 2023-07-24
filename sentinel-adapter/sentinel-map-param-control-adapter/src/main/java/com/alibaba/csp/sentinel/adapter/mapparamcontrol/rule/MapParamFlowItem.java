/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.adapter.mapparamcontrol.rule;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Eric Zhao
 * @since 1.6.0
 */
@Data
@Accessors(chain = true)
public class MapParamFlowItem {

    /**
     * Should be set when applying to parameter flow rules.
     */
    private Integer index;

    /**
     * Strategy for parsing item (e.g. client IP, arbitrary headers and URL parameters).
     * 暂未用到，先留着
     */
    private int parseStrategy;
    /**
     * Field to get (only required for arbitrary headers or URL parameters mode).
     */
    private String fieldName;
    /**
     * Matching pattern. If not set, all values will be kept in LRU map.
     */
    private String pattern;
    /**
     * Matching strategy for item value.
     */
    private int matchStrategy = 0;
}
