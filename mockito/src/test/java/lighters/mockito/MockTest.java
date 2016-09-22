/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lighters.mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.internal.matchers.Any;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.verification.Timeout;
import org.mockito.verification.VerificationMode;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by david on 16/9/1.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class MockTest {

    @Test
    public void testMock() {
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).clear();
        verify(mockedList).add("one");

    }

    @Test
    public void testMockStub() {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        //when(mockedList.get(1)).thenThrow(new RuntimeException());
        //
        ////following prints "first"
        System.out.println(mockedList.get(0));
        //
        ////following throws runtime exception
        //System.out.println(mockedList.get(1));
        //
        ////following prints "null" because get(999) was not stubbed
        //System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
        verify(mockedList).get(0);
    }

    @Test
    public void testArgumentMatchers() {

        Map map = mock(Map.class);
        when(map.get(anyObject())).thenReturn("xiba");
        System.out.println(map.get("1"));

        LinkedList mockedList = mock(LinkedList.class);
        //stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
        //when(mockedList.contains(argThat(isValid()))).thenReturn("element");

        //following prints "element"
        System.out.println(mockedList.get(999));

        //you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

        //argument matchers can also be written as Java 8 Lambdas

        //verify(mockedList).add(someString -> someString.length() > 5);
    }

    @Test
    public void testNumbersVerify() {
        LinkedList mockedList = mock(LinkedList.class);

        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("twice");
        verify(mockedList, atMost(5)).add("three times");
    }

    @Test
    public void testExceptions() {
        LinkedList mockedList = mock(LinkedList.class);
        doThrow(new RuntimeException()).when(mockedList).clear();

        //following throws RuntimeException:
        mockedList.clear();
    }

    @Test
    public void testInOrder() {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrder1 = inOrder(singleMock);

        //following will make sure that add is first called with "was added first, then with "was added second"
        inOrder1.verify(singleMock).add("was added first");
        inOrder1.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        inOrder1 = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrder1.verify(firstMock).add("was called first");
        inOrder1.verify(secondMock).add("was called second");
    }

    @Test
    public void testNeverHappen() {
        List mockOne = mock(List.class);
        List mockTwo = mock(List.class);
        List mockThree = mock(List.class);

        //using mocks - only mockOne is interacted
        mockOne.add("one");

        //ordinary verification
        verify(mockOne).add("one");

        //verify that method was never called on a mock
        verify(mockOne, never()).add("two");

        //verify that other mocks were not interacted
        verifyZeroInteractions(mockTwo, mockThree);
    }

    @Test
    public void testNoMore() {
        List mockedList = mock(List.class);

        //using mocks
        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList).add("two");
        verify(mockedList).add("one");

        //following verification will fail
        verifyNoMoreInteractions(mockedList);


    }

    @Test
    public void testConsecutiveCalls() {
        HashMap mock = mock(HashMap.class);
        when(mock.get("some arg")).thenThrow(new RuntimeException()).thenReturn("foo");

        //First call: throws runtime exception:
        try {
            mock.get("some arg");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //Second call: prints "foo"
        System.out.println(mock.get("some arg"));

        //Any consecutive call: prints "foo" as well (last stubbing wins).
        System.out.println(mock.get("some arg"));
    }

    @Test
    public void testGenericAnswer() {
        HashMap mock = mock(HashMap.class);

        when(mock.get(anyString())).thenAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Object mock = invocation.getMock();
                return "called with arguments: " + args[0];
            }
        });

        //the following prints "called with arguments: foo"
        System.out.println(mock.get("foo"));
    }

    @Test
    public void testDoMethods() {
        List mockedList = mock(LinkedList.class);
        doThrow(new RuntimeException()).when(mockedList).clear();

        //following throws RuntimeException:
        mockedList.clear();
    }

    @Test
    public void testSpyRealObjects() {

        List list = new LinkedList();
        List sypList = spy(list);

        //optionally, you can stub out some methods:
        when(sypList.size()).thenReturn(100);

        LinkedList mock = mock(LinkedList.class);
        when(mock.add(anyInt())).thenCallRealMethod();
        when(mock.get(anyInt())).thenCallRealMethod();
        mock.add(1);
        System.out.println(mock.get(0));

        //using the spy calls *real* methods
        sypList.add("one");
        sypList.add("two");

        //prints "one" - the first element of a list
        System.out.println(sypList.get(0));

        //size() method was stubbed - 100 is printed
        System.out.println(sypList.size());

        //optionally, you can verify
        verify(sypList).add("one");
        verify(sypList).add("two");
    }

    @Test
    public void testSpyLimits() {
        List list = new LinkedList();
        List spy = spy(list);

        //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
        //when(spy.get(0)).thenReturn("foo");

        //You have to use doReturn() for stubbing
        doReturn("foo").when(spy).get(0);
    }

    @Test
    public void testDefaultReturn() {
        Map mock = mock(HashMap.class, Mockito.RETURNS_SMART_NULLS);

        System.out.println(mock.get("b"));
    }

    @Test
    public void testArgumentMatcher() {
        class ListOfTwoElements implements ArgumentMatcher<List> {
            public boolean matches(List list) {
                return list.size() == 2;
            }

            public String toString() {
                //printed in verification errors
                return "[list of 2 elements]";
            }
        }

        List mock = mock(List.class);

        when(mock.addAll(argThat(new ListOfTwoElements()))).thenReturn(true);

        mock.addAll(Arrays.asList("one", "two"));

        verify(mock).addAll(argThat(new ListOfTwoElements()));
    }

    @Test
    public void testPartialRealMock1() {
        //you can create partial mock with spy() method:
        LinkedList linkedList = new LinkedList();
        linkedList.addFirst(1);

        LinkedList list = spy(linkedList);
        list.add(2);
        verify(list).add(2);

        assertThat(list.get(0), is(1));
        assertThat(list.get(1), is(2));
    }

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPartialRealMock2() {
        //you can enable partial mock capabilities selectively on mocks:
        List mock = mock(LinkedList.class);
        //Be sure the real implementation is 'safe'.
        //If real implementation throws exceptions or depends on specific state of the object then you're in trouble.

        when(mock.get(anyInt())).thenCallRealMethod();

        thrown.expect(Exception.class);
        mock.get(0);
        System.out.println("hahah");
        mock.get(0);
    }

    @Test
    public void testResetMock() {
        List mock = mock(List.class);
        when(mock.size()).thenReturn(10);
        mock.add(1);
        reset(mock);
        assertThat(mock.size(), is(0));
    }

    @Test
    public void testTimeout() {
        List mock = mock(List.class);

        when(mock.get(0)).thenReturn(1);

        System.out.println(mock.get(0));

        verify(mock, timeout(100)).get(0);
        //above is an alias to:
        verify(mock, timeout(100).times(1)).get(0);

        System.out.println(mock.get(0));

        verify(mock, timeout(100).times(2)).get(0);

        verify(mock, timeout(100).atLeast(2)).get(0);

        verify(mock, new Timeout(100, new VerificationMode() {
            @Override
            public void verify(VerificationData data) {
                Assume.assumeNotNull(data);
            }

            @Override
            public VerificationMode description(String description) {
                return null;
            }
        })).get(0);
    }

    @Spy ArrayList list1;
    @InjectMocks ArrayList list2;

    @Test
    public void testMocksInit() {
        MockitoAnnotations.initMocks(this);
        Assert.assertNotNull(list1);
        Assert.assertNotNull(list2);
    }

    @Test
    public void testWriteInOneLine() {
        List list = when(mock(List.class).subList(0, 10)).thenReturn(new ArrayList()).getMock();
    }

    @Test
    public void testIgnoreStubs1() {

        //mocking lists for the sake of the example (if you mock List in real you will burn in hell)
        List mock1 = mock(List.class), mock2 = mock(List.class);

        //stubbing mocks:
        when(mock1.get(0)).thenReturn(10);
        when(mock2.get(0)).thenReturn(20);

        //using mocks by calling stubbed get(0) methods:
        //System.out.println(mock1.get(0)); //prints 10
        System.out.println(mock2.get(0)); //prints 20

        mock1.get(0);
        verify(mock1).get(0);

        //using mocks by calling clear() methods:
        mock1.clear();
        mock2.clear();

        //verification:
        verify(mock1).clear();
        verify(mock2).clear();



        //verifyNoMoreInteractions() fails because get() methods were not accounted for.
        try {
            verifyNoMoreInteractions(mock1, mock2);
        } catch (NoInteractionsWanted e) {
            System.out.println(e);
        }

        //However, if we ignore stubbed methods then we can verifyNoMoreInteractions()
        verifyNoMoreInteractions(ignoreStubs(mock1, mock2));


        //Remember that ignoreStubs() *changes* the input mocks and returns them for convenience.
    }

    @Test
    public void testIgnoreStubs2() {

        List list = mock(List.class);
        when(list.get(0)).thenReturn("foo");

        list.add(0);
        System.out.println(list.get(0)); //we don't want to verify this
        list.clear();

        //verify(list).add(0);
        //verify(list).add(0);
        //verify(list).clear();


        // Same as: InOrder inOrder = inOrder(list);
        InOrder inOrder = inOrder(ignoreStubs(list));

        inOrder.verify(list).add(0);
        // this will have an error..
        //inOrder.verify(list).get(0);
        inOrder.verify(list).clear();
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    public void testMockDetails() {
        List list = mock(List.class);
        assertThat(Mockito.mockingDetails(list).isMock(), is(true));
        assertThat(Mockito.mockingDetails(list).isSpy(), is(false));
    }

    @Test
    public void testCustomMessage(){
        List list = mock(List.class);
        when(list.get(0)).thenReturn(1);
        verify(list, description("should print the get(0) result")).get(0);
    }


}
