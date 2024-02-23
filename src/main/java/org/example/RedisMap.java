package org.example;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import redis.clients.jedis.JedisPooled;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Tag(name = "RedisMap", description = "Map на основе Redis")
public class RedisMap implements Map<String, String> {
    private static final String HOST = "localhost";
    private static final Integer PORT = 6379;
    private static final String KEY = "hash1";

    private final JedisPooled jedis = new JedisPooled(HOST, PORT);

    @Operation(summary = "Возвращает размер Мапы")
    @Override
    public int size() {
        return (int) jedis.hlen(KEY);
    }

    @Operation(summary = "Проверяет пустая ли Мапа или нет")
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Operation(summary = "Проверяет наличие ключа в Мапе")
    @Override
    public boolean containsKey(Object key) {
        return key instanceof String && jedis.hexists(KEY, (String) key);
    }

    @Operation(summary = "Проверяет наличие значения в Мапе")
    @Override
    public boolean containsValue(Object value) {
        return values().stream().anyMatch(val -> val.equals(value));
    }

    @Operation(summary = "Возвращает значение из Мапы по ключу")
    @Override
    public String get(Object key) {
        return key instanceof String ? jedis.hget(KEY, (String) key) : null;
    }

    @Operation(summary = "Помещает ключ и значение в Мапу, возвращая значение")
    @Override
    public String put(String key, String value) {
        jedis.hset(KEY, key, value);
        return value;
    }

    @Operation(summary = "Удаляет элемент из Мапы по ключу, возвращая значение")
    @Override
    public String remove(Object key) {
        String value = get(key);
        if (value != null) jedis.hdel(KEY, (String) key);
        return value;
    }

    @Operation(summary = "Помещает все элементы из входящей Мапы в РедисМапу")
    @SuppressWarnings("unchecked")
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        jedis.hmset(KEY, (Map<String, String>) m);
    }

    @Operation(summary = "Очищает Мапу")
    @Override
    public void clear() {
        if (!isEmpty()) jedis.hdel(KEY, keySet().toArray(new String[0]));
    }

    @Operation(summary = "Возвращает Сет ключей из Мапы")
    @Override
    public Set<String> keySet() {
        return jedis.hkeys(KEY);
    }

    @Operation(summary = "Возвращает Коллекцию значений из Мапы")
    @Override
    public Collection<String> values() {
        return jedis.hvals(KEY);
    }

    @Operation(summary = "Возвращает Сет пар ключ-значение из Мапы")
    @Override
    public Set<Entry<String, String>> entrySet() {
        return jedis.hgetAll(KEY).entrySet();
    }
}
