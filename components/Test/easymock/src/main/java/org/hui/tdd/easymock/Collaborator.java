package org.hui.tdd.easymock;

/**
 * @author Hui.Liu
 * @since 2021-11-30 11:34
 */
public interface Collaborator {

    void documentAdded(String title);

    void documentChanged(String title);

    void documentRemoved(String title);

    byte voteForRemoval(String title);

    byte voteForRemovals(String... titles);
}
