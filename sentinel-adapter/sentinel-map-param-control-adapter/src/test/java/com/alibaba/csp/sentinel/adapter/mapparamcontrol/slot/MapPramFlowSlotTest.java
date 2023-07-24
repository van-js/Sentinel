package com.alibaba.csp.sentinel.adapter.mapparamcontrol.slot;


import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.adapter.mapparamcontrol.param.MapParamParser;
import com.alibaba.csp.sentinel.adapter.mapparamcontrol.rule.MapParamFlowItem;
import com.alibaba.csp.sentinel.adapter.mapparamcontrol.rule.MapPramFlowRule;
import com.alibaba.csp.sentinel.adapter.mapparamcontrol.rule.MapPramRuleManager;
import com.alibaba.csp.sentinel.slotchain.StringResourceWrapper;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapPramFlowSlotTest {

    @Test
    public void testMapParamRule() {
        String resource = "testMapParamRule";
        MapPramFlowRule mapPramFlowRule = new MapPramFlowRule()
                .setResource(resource)
                .setCount(1)
                .setIntervalSec(1)
                .setBurst(0)
                .setParamItem(new MapParamFlowItem()
                        .setParseStrategy(0)
                        .setFieldName("name")
                        .setPattern("zhangsan")
                        .setMatchStrategy(0)
                );
        Set<MapPramFlowRule> rules = Sets.newSet(mapPramFlowRule);
        MapPramRuleManager.loadRules(rules);

        MapPramFlowSlot mapPramFlowSlot = new MapPramFlowSlot();
        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put("name", "lisi");

        Object[] params = MapParamParser.parseParameterFor(resource, argsMap);
        System.out.println("params:" + Arrays.toString(params));
        try {
            mapPramFlowSlot.entry(null, new StringResourceWrapper(resource, EntryType.IN), null,
                    1, false, params);
            System.out.println("1 paas");

            mapPramFlowSlot.entry(null, new StringResourceWrapper(resource, EntryType.IN), null,
                    1, false, params);
            System.out.println("2 paas");

            mapPramFlowSlot.entry(null, new StringResourceWrapper(resource, EntryType.IN), null,
                    1, false, params);
            System.out.println("3 paas");

            mapPramFlowSlot.entry(null, new StringResourceWrapper(resource, EntryType.IN), null,
                    1, false, params);
            System.out.println("4 paas");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}