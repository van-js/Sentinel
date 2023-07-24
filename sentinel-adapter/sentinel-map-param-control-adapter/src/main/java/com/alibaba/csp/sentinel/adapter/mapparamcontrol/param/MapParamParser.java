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
package com.alibaba.csp.sentinel.adapter.mapparamcontrol.param;

import com.alibaba.csp.sentinel.adapter.mapparamcontrol.SentinelMapParamConstants;
import com.alibaba.csp.sentinel.adapter.mapparamcontrol.rule.MapParamFlowItem;
import com.alibaba.csp.sentinel.adapter.mapparamcontrol.rule.MapPramFlowRule;
import com.alibaba.csp.sentinel.adapter.mapparamcontrol.rule.MapPramRuleManager;
import com.alibaba.csp.sentinel.util.StringUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Eric Zhao
 * @since 1.6.0
 */
public class MapParamParser {

    /**
     * Parse parameters for given resource from the request entity on condition of the rule predicate.
     */
    public static Object[] parseParameterFor(String resource, Map<String, Object> argsMap) {
        if (StringUtil.isEmpty(resource) || argsMap == null || argsMap.isEmpty()) {
            return new Object[0];
        }
        Set<MapPramFlowRule> gatewayRules = new HashSet<>();
        boolean hasNonParamRule = false;
        for (MapPramFlowRule rule : MapPramRuleManager.getRulesForResource(resource)) {
            if (rule.getParamItem() != null) {
                gatewayRules.add(rule);
            } else {
                hasNonParamRule = true;
            }
        }
        if (!hasNonParamRule && gatewayRules.isEmpty()) {
            return new Object[0];
        }
        int size = hasNonParamRule ? gatewayRules.size() + 1 : gatewayRules.size();
        Object[] arr = new Object[size];
        for (MapPramFlowRule rule : gatewayRules) {
            MapParamFlowItem paramItem = rule.getParamItem();
            int idx = paramItem.getIndex();
            String value = argsMap.get(paramItem.getFieldName()).toString();
            String param = parseWithMatchStrategyInternal(paramItem.getMatchStrategy(), value, paramItem.getPattern());
            arr[idx] = param;
        }
        if (hasNonParamRule) {
            arr[size - 1] = SentinelMapParamConstants.MAP_PARAM_DEFAULT_PARAM;
        }
        return arr;
    }

    private static String parseWithMatchStrategyInternal(int matchStrategy, String value, String pattern) {
        if (value == null) {
            return null;
        }
        return value.equals(pattern) ? value : SentinelMapParamConstants.MAP_PARAM_NOT_MATCH_PARAM;
    }
}
