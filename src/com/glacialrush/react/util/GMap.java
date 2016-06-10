package com.glacialrush.react.util;

import java.util.concurrent.ConcurrentHashMap;

public class GMap<K, V> extends ConcurrentHashMap<K, V>
{
	private static final long serialVersionUID = 1527847670799761130L;
	
	public GMap<K, V> copy()
	{
		GMap<K, V> m = new GMap<K, V>();
		
		for(K k : this.keySet())
		{
			m.put(k, get(k));
		}
		
		return m;
	}
	
	public GMap<K, V> qput(K k, V v)
	{
		put(k, v);
		return this.copy();
	}
	
	public GMap<V, GList<K>> flip()
	{
		GMap<V, GList<K>> flipped = new GMap<V, GList<K>>();
		
		for(K i : keySet())
		{
			if(i == null)
			{
				continue;
			}
			
			if(!flipped.containsKey(get(i)))
			{
				flipped.put(get(i), new GList<K>());
			}
			
			flipped.get(get(i)).add(i);
		}
		
		return flipped;
	}
	
	@Override
	public String toString()
	{
		GList<String> s = new GList<String>();
		
		for(K i : keySet())
		{
			s.add(i.toString() + ": " + get(i).toString());
		}
		
		return "[" + s.toString() + "]";
	}
	
	public GMap<K, V> append(GMap<K, V> umap)
	{
		for(K i : umap.keySet())
		{
			put(i, umap.get(i));
		}
		
		return this;
	}
	
	public GList<K> k()
	{
		return new GList<K>(keySet());
	}
	
	public GList<V> v()
	{
		return new GList<V>(values());
	}
	
	public void putNVD(K k, V v)
	{
		if(!containsValue(v))
		{
			put(k, v);
		}
	}
	
	public GList<V> get(GList<K> keys)
	{
		GList<V> ulv = new GList<V>();
		
		for(K i : keys)
		{
			if(get(i) != null)
			{
				ulv.add(get(i));
			}
		}
		
		return ulv;
	}
	
	public GMap<K, V> removeDuplicateKeys()
	{
		GMap<K, V> map = this.copy();
		GList<K> keys = map.k().removeDuplicates();
		
		clear();
		
		for(K i : keys)
		{
			put(i, map.get(i));
		}
		
		return this;
	}
	
	public GMap<K, V> removeDuplicateValues()
	{
		GMap<K, V> map = this.copy();
		GList<K> keys = map.k().removeDuplicates();
		
		clear();
		
		for(K i : keys)
		{
			put(i, map.get(i));
		}
		
		return this;
	}
	
	public void put(GList<K> k, GList<V> v)
	{
		if(k.size() != v.size())
		{
			return;
		}
		
		for(int i = 0; i < k.size(); i++)
		{
			put(k, v);
		}
	}
}
