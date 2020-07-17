package org.cyberpwn.react.util;

import java.util.*;

public class GList<T> extends ArrayList<T> {
    private static final long serialVersionUID = 4480457702775755227L;

    public GList() {
        super();
    }

    public GList(T[] array) {
        super();
        add(array);
    }

    public GList(List<T> array) {
        super();

        if (array == null) {
            return;
        }

        add(array);
    }

    public GList(Set<T> set) {
        super();

        this.addAll(set);
    }

    public GList(Collection<T> set) {
        super();

        this.addAll(set);
    }

    @SuppressWarnings("unchecked")
    public GList(GList<?> constructions) {
        super();

        for (Object i : constructions) {
            add((T) i);
        }
    }

    public T mostCommon() {
        GMap<T, Integer> common = new GMap<>();

        for (T i : this) {
            if (!common.containsKey(i)) {
                common.put(i, 0);
            }

            common.put(i, common.get(i) + 1);
        }

        int sm = 0;
        T v = null;

        for (T i : common.keySet()) {
            if (common.get(i) > sm) {
                sm = common.get(i);
                v = i;
            }
        }

        return v;
    }

    public GList<T> shuffle() {
        GList<T> o = copy();
        Collections.shuffle(o);
        return o;
    }

    @SuppressWarnings("unchecked")
    public GList<GList<T>> split() {
        GList<GList<T>> mtt = new GList<>();
        GList<T> ma = new GList<>();
        GList<T> mb = new GList<>();

        for (int i = 0; i < size() / 2; i++) {
            if (hasIndex(i)) {
                break;
            }

            ma.add(get(i));
        }

        for (int i = (size() / 2) - 1; i < size(); i++) {
            if (hasIndex(i)) {
                break;
            }

            mb.add(get(i));
        }

        mtt.add(ma, mb);

        return null;
    }

    public boolean hasIndex(int i) {
        return i > size() - 1;
    }

    public T pickRandom() {
        Random random = new Random();
        return get(random.nextInt(size()));
    }

    public GList<String> stringList() {
        GList<String> s = new GList<>();

        for (T i : this) {
            s.add(i.toString());
        }

        return s;
    }

    public GList<T> removeDuplicates() {
        Set<T> set = new LinkedHashSet<>(this);
        clear();
        addAll(set);

        return this;
    }

    public boolean hasDuplicates() {
        return size() != new LinkedHashSet<>(this).size();
    }

    public void sort() {
        this.sort(Comparator.comparing(Object::toString));
    }

    public void push(T value, int limit) {
        add(value);

        while (size() > limit && !isEmpty()) {
            remove(0);
        }
    }

    @SuppressWarnings("unchecked")
    public void add(T... array) {
        this.addAll(Arrays.asList(array));
    }

    public GList<T> qadd(T t) {
        this.add(t);
        return this;
    }

    public void add(List<T> array) {
        this.addAll(array);
    }

    public String toString(String split) {
        StringBuilder s = new StringBuilder();

        for (Object i : this) {
            s.append(split).append(i.toString());
        }

        if (s.length() == 0) {
            return "";
        }

        return s.substring(split.length());
    }

    public GList<T> reverse() {
        GList<T> m = this.copy();
        Collections.reverse(m);
        return m;
    }

    @Override
    public String toString() {
        return toString(", ");
    }

    public GList<T> copy() {
        GList<T> c = new GList<>();

        c.addAll(this);

        return c;
    }

    public GList<T> qdel(T t) {
        remove(t);
        return this;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T t = get(0);
        remove(0);
        return t;
    }
}
