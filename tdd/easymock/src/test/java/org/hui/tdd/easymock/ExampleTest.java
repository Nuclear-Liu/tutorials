package org.hui.tdd.easymock;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.replay;

/**
 * @author Hui.Liu
 * @since 2021-11-30 12:48
 */
@RunWith(EasyMockRunner.class)
public class ExampleTest extends EasyMockSupport {

    @TestSubject
    private ClassTested classUnderTest; // 2

    @Mock
    private Collaborator mock; // 1

//    @Rule
//    public EasyMockRule rule = new EasyMockRule(this);

    /*@Before
    public void setUp() {
        mock = mock(Collaborator.class); // 1
        classUnderTest = new ClassTested();
        classUnderTest.setListener(mock);
    }*/

    @Test
    public void testRemoveNonExistingDocument() {
        replay(mock);
        classUnderTest.removeDocument("Does not exist");
    }

    @Test
    @Ignore("expect error")
    public void addDocument() {
        mock.documentAdded("New Document"); // 3
        replayAll(); // 4
        classUnderTest.addDocument("New Document", "content"); // 5
        verifyAll(); // 6
    }

}
