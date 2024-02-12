package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisMapTest {

    private final RedisMap redisMap = new RedisMap();

    @BeforeEach
    void setUp(){
        redisMap.clear();
        redisMap.put("Obj_1", "Value_1");
        redisMap.put("Obj_2", "Value_2");
    }

    @Test
    void size(){
        Assertions.assertEquals(2, redisMap.size());
    }

    @Test
    void isEmptyAndClear(){
        Assertions.assertFalse(redisMap.isEmpty());

        redisMap.clear();

        Assertions.assertTrue(redisMap.isEmpty());
    }

    @Test
    void containsKey(){
        Assertions.assertTrue(redisMap.containsKey("Obj_2"));
        Assertions.assertFalse(redisMap.containsKey("Obj_3"));
    }

    @Test
    void containsValue(){
        Assertions.assertTrue(redisMap.containsValue("Value_2"));
        Assertions.assertFalse(redisMap.containsKey("Value_3"));
    }

    @Test
    void get(){
        Assertions.assertEquals("Value_1", redisMap.get("Obj_1"));
        Assertions.assertNull(redisMap.get("Obj_3"));
    }

    @Test
    void put(){
        redisMap.put("Obj_4", "Value_4");
        Assertions.assertEquals("Value_4", redisMap.get("Obj_4"));
    }

    @Test
    void remove(){
        redisMap.remove("Obj_1");
        Assertions.assertEquals(1, redisMap.size());
        Assertions.assertNull(redisMap.get("Obj_1"));
    }

    @Test
    void putAll(){
        Map<String, String> map = new HashMap<>();
        map.put("Obj_5", "Value_5");
        map.put("Obj_6", "Value_6");

        redisMap.putAll(map);

        Assertions.assertEquals(4, redisMap.size());
        Assertions.assertEquals("Value_5", redisMap.get("Obj_5"));
        Assertions.assertEquals("Value_6", redisMap.get("Obj_6"));
    }

    @Test
    void keySet(){
        Set<String> set = redisMap.keySet();
        Assertions.assertTrue(set.contains("Obj_1"));
        Assertions.assertTrue(set.contains("Obj_2"));
        Assertions.assertEquals(2, set.size());
    }

    @Test
    void values(){
        Collection<String> collection = redisMap.values();
        Assertions.assertTrue(collection.contains("Value_1"));
        Assertions.assertTrue(collection.contains("Value_2"));
        Assertions.assertEquals(2, collection.size());
    }

    @Test
    void entrySet(){
        Set<Map.Entry<String, String>> set= redisMap.entrySet();

        Assertions.assertEquals(2, set.size());

        for (Map.Entry<String, String> me : set) {
            Assertions.assertTrue(me.getKey().equals("Obj_1") || me.getKey().equals("Obj_2"));
            if (me.getKey().equals("Obj_1")) Assertions.assertEquals("Value_1", me.getValue());
            else Assertions.assertEquals("Value_2", me.getValue());
        }
    }
}
