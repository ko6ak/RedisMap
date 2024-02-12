package org.example;

import redis.clients.jedis.JedisPooled;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RedisMap implements Map<String, String> {
    private static final String HOST = "localhost";
    private static final Integer PORT = 6379;
    private static final String KEY = "hash1";

    private final JedisPooled jedis = new JedisPooled(HOST, PORT);

    @Override
    public int size() {
        return (int) jedis.hlen(KEY);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof String && jedis.hexists(KEY, (String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().stream().anyMatch(val -> val.equals(value));
    }

    @Override
    public String get(Object key) {
        return key instanceof String ? jedis.hget(KEY, (String) key) : null;
    }

    @Override
    public String put(String key, String value) {
        jedis.hset(KEY, key, value);
        return value;
    }

    @Override
    public String remove(Object key) {
        String value = get(key);
        if (value != null) jedis.hdel(KEY, (String) key);
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        jedis.hmset(KEY, (Map<String, String>) m);
    }

    @Override
    public void clear() {
        if (!isEmpty()) jedis.hdel(KEY, keySet().toArray(new String[0]));
    }

    @Override
    public Set<String> keySet() {
        return jedis.hkeys(KEY);
    }

    @Override
    public Collection<String> values() {
        return jedis.hvals(KEY);
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return jedis.hgetAll(KEY).entrySet();
    }
}
