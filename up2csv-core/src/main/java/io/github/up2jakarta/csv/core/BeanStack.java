package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.Segment;

import java.lang.reflect.Type;
import java.util.Stack;

final class BeanStack {

    private final Stack<Class<? extends Segment>> stack;
    private final Type[] arguments;
    private final BeanContext context;
    private final int offset;

    public BeanStack(BeanContext context) {
        this(context, 0, new Stack<>());
    }

    private BeanStack(BeanContext context, int offset, Stack<Class<? extends Segment>> stack, Type... arguments) {
        this.arguments = arguments;
        this.context = context;
        this.offset = offset;
        this.stack = stack;
    }

    private Stack<Class<? extends Segment>> newStack() {
        final Stack<Class<? extends Segment>> copy = new Stack<>();
        copy.addAll(this.stack);
        return copy;
    }

    public BeanContext getContext() {
        return context;
    }

    public int getOffset() {
        return offset;
    }

    public Type[] getArguments() {
        return arguments;
    }

    public boolean push(Class<? extends Segment> beanType) {
        if (stack.contains(beanType)) {
            return true;
        }
        this.stack.push(beanType);
        return false;
    }

    public BeanStack with(int offset) {
        return new BeanStack(this.context, offset, this.newStack(), this.arguments);
    }

    public BeanStack with(Type... arguments) {
        return new BeanStack(this.context, this.offset, this.newStack(), arguments);
    }

}
