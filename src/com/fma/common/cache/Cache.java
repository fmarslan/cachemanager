/*******************************************************************************
 * Copyright (c) 2015  FMA
 * Fatih Mehmet ARSLAN
 * fmarslan06@gmail.com
 *******************************************************************************/
package com.fma.common.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.fma.common.cache.CacheObject.CacheDependecy;
import com.fma.common.cache.CacheObject.CacheListener;

@SuppressWarnings({ "serial", "rawtypes" })
public class Cache implements Serializable {

	private static HashMap<Object, CacheObject> cache = new LinkedHashMap<>();

	@SuppressWarnings("unchecked")
	public static <T> T get(Object key) {
		if (!cache.containsKey(key))
			return null;
		CacheObject obj = ((CacheObject) cache.get(key));
		if (!obj.isExpired())
			return (T) obj.getData();
		cache.remove(key);
		return null;
	}

	public static <K, V> V put(K key, V value) {
		return put(new CacheObject<K, V>(key, value));
	}


	public static <K, V> V put(K key, V value, CacheDependecy dependency) {
		return put(new CacheObject<K, V>(key,value, dependency));
	}

	public static <K, V> V put(K key, CacheListener<V> listener) {
		return put(new CacheObject<K, V>(key, listener));
	}

	public static <K, V> V put(K key, CacheDependecy dependency, CacheListener<V> listener) {
		return put(new CacheObject<K, V>(key, dependency, listener));
	}

	public static <K, V> V put(CacheObject<K, V> object) {
		if (cache.containsKey(object.getKey()))
			cache.replace(object.getKey(), object);
		else
			cache.put(object.getKey(), object);
		return object.getData();
	}

	public static void clear() {
		cache = new LinkedHashMap<>();
	}

	public static Iterator iterator() {
		return cache.values().iterator();
	}
	
	public Iterator keySet(){
		return cache.keySet().iterator();
	}
}
