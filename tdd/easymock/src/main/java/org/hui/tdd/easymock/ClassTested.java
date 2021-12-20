package org.hui.tdd.easymock;

/**
 * @author Hui.Liu
 * @since 2021-11-30 12:40
 */
public class ClassTested {
    private Collaborator listener;

    public void setListener(Collaborator listener) {
        this.listener = listener;
    }

    public void addDocument(String title, String document) {
        listener.documentAdded(title);
    }
}
