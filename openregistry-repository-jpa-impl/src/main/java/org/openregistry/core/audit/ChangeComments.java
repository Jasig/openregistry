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
    private static ThreadLocal<String> comments;

    public ChangeComments () {
        comments = new ThreadLocal<String>();
    }

    public static String getComments() {
        return comments.get();
    }

    public static void setComments(String value) {
        comments.set(value);
    }
}
