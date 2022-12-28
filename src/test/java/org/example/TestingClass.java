package org.example;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.easymock.EasyMock;
import org.easymock.TestSubject;
import org.jongo.MongoCollection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TestingClass {

    FriendsCollection friends = createMock(FriendsCollection.class);
    FriendshipsMongo friendshipsMongo = createMock(FriendshipsMongo.class);
    List<String> friendsList = new ArrayList<>();

    @BeforeEach
    void set_up() {
        Person person1 = new Person();
        Person person2 = new Person();
        Person person3 = new Person("Mark");
        friendsList.add("person1");
        friendsList.add("person2");
        friendsList.add("person3");

    }

    @Test
    public void givenWhenTypeIsInterface_thenReturnTrue() {

        Assert.assertTrue(friends instanceof FriendsCollection);
    }


    @Test
    public void mockingWorksAsExpected() {
        Person person2 = new Person();
        expect(friends.findByName("person2")).andReturn(person2);
        EasyMock.replay(friends);
        assertThat(friends.findByName("person2")).isEqualTo(person2);


    }


    @Test
    public void savePerson() throws UnknownHostException {
        Person person3 = new Person("John");
        friends.save(person3);
        EasyMock.expect(friends.findByName("John")).andReturn(person3);
        EasyMock.replay(friends);
        assertThat(friends.findByName("John")).isEqualTo(person3);
    }

    @Test
    public void addFriends() throws UnknownHostException {
        Person person3 = new Person("John");
        Person person4 = new Person("Zbig");
        friends.save(person3);
        friends.save(person4);
        friendshipsMongo.makeFriends("John", "Zbig");
        EasyMock.expect(friendshipsMongo.areFriends("John", "Zbig")).andReturn(true);
        EasyMock.replay(friendshipsMongo);
        assertThat(friendshipsMongo.areFriends("John", "Zbig")).isEqualTo(true);
    }

    @Test
    public void getFriends(){
        Person person3 = new Person("John");
        Person person4 = new Person("Zbig");
        friends.save(person3);
        friends.save(person4);
        friendshipsMongo.makeFriends("John", "Zbig");
        List <String> myFriends = new ArrayList<>();
        myFriends.add("Zbig");
        EasyMock.expect(friendshipsMongo.getFriendsList("John")).andReturn(myFriends);
        EasyMock.replay(friendshipsMongo);
        assertTrue(friendshipsMongo.getFriendsList("John").contains("Zbig"));

    }
}
