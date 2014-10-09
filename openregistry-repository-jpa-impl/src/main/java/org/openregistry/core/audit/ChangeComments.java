package org.openregistry.core.audit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: sheliu
 * Date: 6/24/14
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChangeComments {
    private static ThreadLocal<String> comments = new ThreadLocal();

    public static String getComments() {
        String current  = comments.get();
        comments.remove();
        return current;
    }

    public static void setComments(String value) {
        comments.set(value);
    }
}
