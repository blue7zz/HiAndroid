package com.example.hilibrary.chapter_3;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 架构：
 * CopyOnWriteArrayList数据结构是ArrayList是一致的，底层是个数组
 * CopyOnWriteArrayList对数组进行操作基本会走四步：
 * <p>
 * 1.加锁
 * 2.从原数组中拷贝处新数组
 * 3.在新数组上进行操作,并把新数组赋值给数组容器
 * 4.解锁
 * <p>
 * 除此之外底层数组还被volatile关键字修饰
 * private transient volatile Object[] array;
 *
 * @param <E>
 */
public class CopyOnWriteArrayListDemo<E> {
    private volatile Object[] array = new Object[10];
    private ReentrantLock lock = new ReentrantLock();


    private Object[] getArray() {
        return array;
    }

    private void setArray(Object... objects) {
        array = objects;
    }

    // 添加元素到数组尾部
    public boolean add(E e) {
        final ReentrantLock lock = this.lock; // 加锁
        lock.lock();
        try { // 得到所有的原数组
            Object[] elements = getArray();
            int len = elements.length;
            //简单的在原来数组上修改其中某几个元素的值，是无法触发可见性的。我们必须通过修改数组的内存地址才行，也就是说对数组进行重新赋值。
            // 拷贝到新数组里面，新数组的长度是 + 1 的，因为新增会多一个元素
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            // 在新数组中进行赋值，新元素直接放在数组的尾部
            newElements[len] = e;
            //在新的数组上进行拷贝，对老数组没有任何影响，只有新数组完全拷贝完成之后，外部才能访问到，降低了在赋值过程中，老数组数据变动的影响。
            // 替换掉原来的数组
            setArray(newElements);
            return true;
        } finally {  // finally 里面释放锁，保证即使 try 发生了异常，仍然能够释放锁
            lock.unlock();
        }
    }

//    public boolean add1(E e){
//        // len：数组的长度、index：插入的位置
//        int numMoved = len - index;
//        // 如果要插入的位置正好等于数组的末尾，直接拷贝数组即可
//        if (numMoved == 0)
//            newElements = Arrays.copyOf(elements, len + 1);
//        else {
//            // 如果要插入的位置在数组的中间，就需要拷贝 2 次 // 第一次从 0 拷贝到 index。
//            // 第二次从 index+1 拷贝到末尾。
//            newElements = new Object[len + 1];
//            System.arraycopy(elements, 0, newElements, 0, index);
//            System.arraycopy(elements, index, newElements, index + 1, numMoved); }
//            // index 索引位置的值是空的，直接赋值即可。
//            newElements[index] = element;
//            // 把新数组的值赋值给数组的容器中
//            setArray(newElements);
//    }

    public E get(Object[] obj, int i) {
        return (E) obj;
    }

    // 删除某个索引位置的数据
    public E remove(int index) {
        final ReentrantLock lock = this.lock; // 加锁
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length; // 先得到老值
            E oldValue = get(elements, index);
            int numMoved = len - index - 1;
            // 如果要删除的数据正好是数组的尾部，直接删除
            if (numMoved == 0) setArray(Arrays.copyOf(elements, len - 1));
            else {
                // 如果删除的数据在数组的中间，分三步走
                // 1. 设置新数组的长度减一，因为是减少一个元素
                // 2. 从 0 拷贝到数组新位置
                // 3. 从新位置拷贝到数组尾部
                Object[] newElements = new Object[len - 1];
                System.arraycopy(elements, 0, newElements, 0, index);
                System.arraycopy(elements, index + 1, newElements, index, numMoved);
                setArray(newElements);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }


    // 批量删除包含在 c 中的元素
    //1：锁
    //2：遍历出非删除的元素，存入临时数组
    //3: 把非删除的元素，拷贝到数组内
    //4: 解锁
    public boolean removeAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            // 说明数组有值，数组无值直接返回 false
            if (len != 0) {
                // newlen 表示新数组的索引位置，新数组中存在不包含在 c 中的元素
                int newlen = 0;
                Object[] temp = new Object[len];
                // 循环，把不包含在 c 里面的元素，放到新数组中
                for (int i = 0; i < len; ++i) {
                    Object element = elements[i];
                    // 不包含在 c 中的元素，从 0 开始放到新数组中
                    if (!c.contains(element))
                        temp[newlen++] = element;
                }
                // 拷贝新数组，变相的删除了不包含在 c 中的元素
                if (newlen != len) {
                    setArray(Arrays.copyOf(temp, newlen));
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }


    // o：我们需要搜索的元素
    // elements：我们搜索的目标数组
    // index：搜索的开始位置
    // fence：搜索的结束位置
    private static int indexOf(Object o, Object[] elements, int index, int fence) {
        // 支持对 null 的搜索
        if (o == null) {
            for (int i = index; i < fence; i++)
                // 找到第一个 null 值，返回下标索引的位置
                if (elements[i] == null) return i;
        } else {
            // 通过 equals 方法来判断元素是否相等
            // 如果相等，返回元素的下标位置
            for (int i = index; i < fence; i++) if (o.equals(elements[i])) return i;
        }
        return -1;
    }

    /**-------------迭代-------------**/
    //在 CopyOnWriteArrayList 类注释中，明确说明了，在其迭代过程中，即使数组的原值被改变，
    // 也不会抛出 ConcurrentModificationException 异常，其根源在于数组的每次变动，都会生成新的数组，
    // 不会影响老数组，这样的话，迭代过程中，根本就不会发生迭代数组的变动。



}

