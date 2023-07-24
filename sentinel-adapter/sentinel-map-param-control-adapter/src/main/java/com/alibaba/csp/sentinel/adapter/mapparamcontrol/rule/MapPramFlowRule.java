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

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Eric Zhao
 * @since 1.6.0
 */
@Data
@Accessors(chain = true)
public class MapPramFlowRule {

    private String resource;
    private int grade = RuleConstant.FLOW_GRADE_QPS;
    private double count;
    private long intervalSec = 1;

    private int controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;
    private int burst;
    /**
     * For throttle (rate limiting with queueing).
     */
    private int maxQueueingTimeoutMs = 500;

    /**
     * For parameter flow control. If not set, the gateway rule will be
     * converted to normal flow rule.
     */
    private MapParamFlowItem paramItem;
}
