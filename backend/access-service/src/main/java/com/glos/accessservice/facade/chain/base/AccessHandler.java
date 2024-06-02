package com.glos.accessservice.facade.chain.base;

import java.util.Objects;

public abstract class AccessHandler {

    protected AccessHandler next;


    public AccessHandler getNext() {
        return next;
    }

    public final void add(AccessHandler handler) {
        if (next == null) {
            next = handler;
            return;
        }
        AccessHandler lastNext = this.next;
        while(lastNext.next != null) lastNext = lastNext.next;
        lastNext.next = handler;
    }

    public final void clear() {
        if (next == null)
            return;
        AccessHandler prevNext;
        AccessHandler lastNext = this.next;
        while(lastNext.next != null) {
            prevNext = lastNext;
            lastNext = lastNext.next;
            prevNext.next = null;
        }
    }

    public boolean check(AccessRequest request) {
        requestNonNullOrThrow(request);
        return false;
    }

    protected final boolean checkNext(AccessRequest request) {
        if (next == null)
            return true;
        return next.check(request);
    }

    protected void requestNonNullOrThrow(AccessRequest request)
            throws NullPointerException {
        Objects.requireNonNull(request, "request param is null");
        Objects.requireNonNull(request.getPath(), "path field is null");
        Objects.requireNonNull(request.getUsername(), "username field is null");
    }

}
