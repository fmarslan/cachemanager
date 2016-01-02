/*******************************************************************************
 *
 *
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016  FMA
 *
 * Fatih Mehmet ARSLAN
 * fmarslan06@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
