package com.example.insta;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;

import java.io.IOException;
// --------------
class account {
    public final String username;
    private final String password;
    private final HashSet<account>followers=new HashSet<>();
    private final HashSet<account>followings=new HashSet<>();
    private final ArrayList<post>posts=new ArrayList<>();
    private final Stack<post>new_posts=new Stack<>();
    public account(String username, String password ) {
        this.username = username;
        this.password = password;
    }
    // --------------
    public HashSet<account> get_person(int cursor) throws Incorrect_entry_exception {
        if (cursor==1)
            return followers;
        else if (cursor==2)
            return followings;
        throw new Incorrect_entry_exception();
    }
    // --------------
    public boolean certify_password(String password)
    {
        return this.password.equals(password);
    }
    // --------------
    public boolean log_in(String user,String pass)
    {
        if (user.equals(username)&pass.equals(password))
        {
            while (!new_posts.isEmpty())
                new_posts.pop().show_post();
            return true;
        }
        return false;
    }
    // --------------
    public void add_new_post(post np)
    {
        new_posts.push(np);
    }
    // --------------
    public void add_post(ArrayList<String>content)
    {
        post temp=new post(content,this);
        posts.add(temp);
        for (account p:followers)
            p.add_new_post(temp);
        System.out.println("***POST CREATED! DONE***\n_____________________");
    }
    // --------------
    public void show_post()
    {
        for (post p:posts)
        {
            System.out.println((posts.indexOf(p)+1)+".");
            p.show_post();
        }
    }
    // --------------
    public void like_post(int index,account follower)
    {
        try {
            posts.get(index-1).like_post(follower);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("INVALID ENTRY!");
        }
    }
    // --------------
    public void comment_post(int index,String comment,account follower)
    {
        try {
            posts.get(index-1).add_comment(follower,comment);
        }
        catch (IndexOutOfBoundsException ex)
        {
            System.out.println("***INVALID ENTRY!***");
        }
    }
    // --------------
    public void follow(account target) throws Incorrect_entry_exception {
        if (target.equals(this))
            throw new Incorrect_entry_exception("YOU CAN NOT FOLLOW YOU'RE ACCOUNT!\n_____________________");
        followings.add(target);
        target.being_followed(this);
        System.out.println("***ACCOUNT FOLLOWED SUCCESSFULLY***!");
    }
    // --------------
    public void block(account target) throws Incorrect_entry_exception {
        if (followers.contains(target)) {
            followers.remove(target);
            target.being_blocked(this);
            System.out.println("ACCOUNT BLOCKED SUCCESSFULLY !!!");
        }
        else
            throw new Incorrect_entry_exception("WRONG USERNAME!");
    }
    // --------------
    public void being_blocked(account following)
    {
        followings.remove(following);
    }
    // --------------
    public void being_followed(account follower)
    {
        followers.add(follower);
    }
    // --------------
    public void show_info()
    {
        System.out.println("USERNAME : "+username+"\nPOSTS : "+posts.size()+"\nFOLLOWERS : "+followers.size()+"\nFOLLOWINGS : "+followings.size()+"\n_____________________");
    }
    // --------------
    public void full_info()
    {
        System.out.println("POSTS :");
        show_post();
        System.out.println("________________________\nFOLLOWERS :");
        for (account a:followers)
            a.show_info();
        System.out.println("________________________\nFOLLOWINGS :");
        for (account a:followings)
            a.show_info();
    }
    // --------------
    public void post_full_info(int index)
    {
        try {
            posts.get(index-1).full_info();
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("INVALID ENTRY!");
        }
    }
    // --------------
    public void dislike_post(int index) throws Incorrect_entry_exception
    {
        try {
            posts.get(index-1).dislike_post(this);
            System.out.println("POST DISLIKED!\n_____________________");
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("INVALID ENTRY!");
        }
    }
    // --------------
    public account show_following_info(String user) throws Incorrect_entry_exception {
        for (account a:followings)
            if (a.username.equals(user))
            {
                a.show_post();
                return a;
            }
        throw new Incorrect_entry_exception("ACCOUNT NOT FOUND!\n_______________________");
    }
    // --------------

}
// --------------
class Incorrect_entry_exception extends Exception{
    private String message;
    public Incorrect_entry_exception(String message) {
        this.message=message;
    }

    public Incorrect_entry_exception() {
    }

    @Override
    public String getMessage() {
        if (message == null)
            return "WRONG ENTRY !\nTRY AGAIN!";
        return message;
    }
}
// --------------
class person {
    public final String name;
    public final String family;
    private final String number;
    private final HashSet<account>accounts=new HashSet<>();
    private final ArrayList<String>users=new ArrayList<>();
    public person(String name, String family, String number) {
        this.name = name;
        this.family = family;
        this.number = number;
    }
    // --------------
    public void add_account(String user,String pass)
    {
        if (!users.contains(user))
        {
            accounts.add(new account(user, pass));
            // accounts.add(new account(user, pass));
            users.add(user);
            System.out.println("***GREAT*** ACCOUNT CREATED!*\n_______________________");
        }
        else
            System.out.println("THIS USERNAME HAS BEEN ALREADY TAKEN ! \n_______________________");
    }
    // --------------
    public void delete_account(String user,String pass)
    {
        boolean f=false;
        for (account a:accounts)
            if (a.username.equals(user)&a.certify_password(pass))
            {
                accounts.remove(a);
                users.remove(user);
                f=true;
                System.out.println("ACCOUNT DELETED! DONE*!\n_______________________");
                break;
            }
        if (!f)
            System.out.println("ACCOUNT NOT FOUND !\n_______________________");

    }
    // --------------
    public account log_in(String user,String pass) throws Incorrect_entry_exception {
        for (account a:accounts)
        {
            if (a.log_in(user, pass))
                return a;
        }
        throw new Incorrect_entry_exception("THERE IS NO ACCOUNT WITH YOUR NUMBER!\n_______________________");
    }
    // --------------
    public void show_info()
    {
        System.out.println("NAME : "+name+"\nFAMILY : "+family+"\nNUMBER : "+number+"\nACCOUNTS:");
        for (account a:accounts)
            System.out.println(a.username);
        System.out.println("_______________________");
    }
    // --------------
    public account search_account(String user) throws Incorrect_entry_exception {
        for (account a:accounts)
            if (a.username.equals(user))
                return a;
        throw new Incorrect_entry_exception("WRONG USERNAME !\n_______________________");
    }
}
// --------------
class post {
    private final ArrayList<String>content;
    private final HashSet<account>likes=new HashSet<>();
    private final HashMap<account,ArrayList<String>>comments=new HashMap<>();
    private final account creator;
    public post(ArrayList<String> content,account creator) {
        this.content = content;
        this.creator=creator;
    }
    // --------------
    public void show_post()
    {
        System.out.println("POSTED BY : "+creator.username+"\nCONTENT :");
        for (String temp:content)
            System.out.println(temp);
        int count=0;
        for (account temp3:comments.keySet())
            count+=comments.get(temp3).size();
        System.out.println("_________________\nLIKES : "+likes.size()+"\nPEOPLE WHO COMMENTED: "+comments.size()+"\nCOMMENTS : "+count);
    }
    // --------------
    public void like_post(account follower)
    {
        likes.add(follower);
        System.out.println("****** POST LIKED ******!\n_________________");
    }
    // --------------
    public void add_comment(account follower,String comment)
    {
        if (!comments.containsKey(follower)) {
            comments.put(follower,new ArrayList<>());
        }
        comments.get(follower).add(comment);
        System.out.println("******* COMMENT ADDED *******!");
    }
    // --------------
    public void dislike_post(account follower) throws Incorrect_entry_exception {
        if (likes.contains(follower))
        {
            likes.remove(follower);
            System.out.println("POST DISLIKED * !\n_________________");
        }
        else
            throw new Incorrect_entry_exception("YOU HAVE NOT LIKED THIS POST !");
    }
    // --------------
    public void full_info()
    {



        int count=1;
        System.out.println("LIKES :");
        for (account a:likes)
        {
            System.out.println(count+"."+a.username);
            count++;
        }
        HashSet<account>ac= (HashSet<account>) comments.keySet();
        for (account ac2:ac)
        {
            count=1;
            System.out.println(ac2.username+" COMMENTS :");
            for (String com:comments.get(ac2))
            {
                System.out.println(count + "." + com);
                count++;
            }
        }
    }


}
// --------------
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("firstmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Instagram");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
        ArrayList<person>people=new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("_______________________**** INSTAGRAM MEDIA ****_______________________");
        boolean f=false;
        while (!f)
        {
            System.out.println("* 1.CREATE A PERSON \n* 2.LOGIN TO PANEL\n* 3.SHOW INFO \n* 4.MAKE AN ACCOUNT\n* 5.DELETE AN ACCOUNT \n* 6.EXIT\n________________________________________________________________________");
            int cursor=sc.nextInt();
            sc.nextLine();
            switch (cursor)
            {
                case 1:
                {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("***ENTER THE  NAME AND LAST NAME AND ID (each in one line) : --> ");
                    people.add(new person(sc.nextLine(),sc.nextLine(),sc.nextLine()));
                    System.out.println("***PERSON ADDED!***\n_______________________");
                    break;
                }
                case 2:
                {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("*ENTER YOUR NAME : --> \n_______________________");
                    person tr;
                    if ((tr=search_person(sc.nextLine(),people))!=null)
                    {
                        tr.show_info();
                        System.out.println("*ENTER THE ACCOUNT USERNAME AND PASSWORD (each in one line):*");
                        try {
                            account tr2=tr.log_in(sc.nextLine(),sc.nextLine());
                            boolean f2=false;
                            while (!f2)
                            {
                                System.out.println("* 1.FOLLOW SOMEONE\n* 2.BLOCK SOMEONE\n* 3.ADD POST\n* 4.FOLLOWINGS POSTS\n* 5.MY PROFILE\n* 6.Exit");
                                int cursor2=sc.nextInt();
                                sc.nextLine();
                                switch (cursor2)
                                {
                                    case 1:
                                    {
                                        System.out.println("***CANDIDATES :***");
                                        for (person p:people)
                                            p.show_info();
                                        System.out.println("*ENTER THE TARGET NAME AND USERNAME (each in one line):");
                                        person p=search_person(sc.nextLine(),people);
                                        if (p!=null)
                                        {
                                            tr2.follow(p.search_account(sc.nextLine()));
                                        }
                                        else
                                            System.out.println("*PERSON NOT FOUND!\n_______________________");
                                        break;
                                    }
                                    case 2:
                                    {
                                        System.out.println("***CANDIDATES :***");
                                        HashSet<account> temp=tr2.get_person(1);
                                        for (account a:temp)
                                            a.show_info();
                                        System.out.println("*ENTER THE TARGET NAME AND USERNAME  (each in one line):");
                                        person p=search_person(sc.nextLine(),people);
                                        if (p!=null)
                                        {
                                            tr2.block(p.search_account(sc.nextLine()));
                                        }
                                        else
                                            System.out.println("*PERSON NOT FOUND!\n_______________________");
                                        break;
                                    }
                                    case 3:
                                    {
                                        System.out.println("*WRITE YOUR POST THEN TYPE ---> End:");
                                        String temp;
                                        ArrayList<String>temp2=new ArrayList<>();
                                        while (!(temp=sc.nextLine()).equals("End"))
                                        {
                                            temp2.add(temp);
                                        }
                                        tr2.add_post(temp2);
                                        break;
                                    }
                                    case 4:
                                    {
                                        System.out.println("*FOLLOWINGS :");
                                        HashSet<account> temp=tr2.get_person(2);
                                        for (account a:temp)
                                            a.show_info();
                                        System.out.println("_______________________\n*ENTER THE ACCOUNT USERNAME :");
                                        account following=tr2.show_following_info(sc.nextLine());
                                        System.out.println("_______________________\n*1.LIKE\n*2.COMMENT\n*3.DISLIKE\n*4.Show post's full info\n_______________________");
                                        int cs=sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("ENTER THE TARGET POST INDEX :");
                                        int index=sc.nextInt();
                                        sc.nextLine();
                                        switch (cs)
                                        {
                                            case 1:{
                                                following.like_post(index,tr2);
                                                break;
                                            }
                                            case 2:{
                                                System.out.println("* TYPE YOUR COMMENT:");
                                                following.comment_post(index,sc.nextLine(),tr2);
                                                break;
                                            }
                                            case 3:{
                                                following.dislike_post(index);
                                                break;
                                            }
                                            case 4:{
                                                following.post_full_info(index);
                                                break;
                                            }
                                            default:{
                                                throw new Incorrect_entry_exception("WRONG ENTRY!\n_______________________");
                                            }
                                        }
                                        break;
                                    }
                                    case 5:
                                    {
                                        tr2.full_info();
                                        break;
                                    }
                                    case 6:
                                    {
                                        f2=true;
                                        break;
                                    }
                                    default:
                                    {
                                        System.out.println("WRONG ANSWER\nTRY AGAIN\n_______________________");
                                        break;
                                    }

                                }
                            }
                        } catch (Incorrect_entry_exception incorrect_entry_exception) {
                            incorrect_entry_exception.getMessage();
                        }
                    }
                    else
                        System.out.println("**ACCOUNT NOT FOUND!\n_______________________");
                    break;
                }
                case 3:
                {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    for (person p:people)
                        p.show_info();
                    break;
                }
                case 4:
                {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("*TYPE YOUR NAME :");
                    person tr=search_person(sc.nextLine(),people);
                    if (tr!=null)
                    {
                        System.out.println("Type your username and password (each in on line):");
                        tr.add_account(sc.nextLine(),sc.nextLine());
                    }
                    else
                        System.out.println("**ACCOUNT NOT FOUND!\n_______________________");
                    break;
                }
                case 5:
                {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("*TYPE YOUR NAME :");
                    person tr=search_person(sc.nextLine(),people);
                    if (tr!=null)
                    {
                        System.out.println("*TYPE YOUR USERNAME AND PASSWORD (each in on line):");
                        tr.delete_account(sc.nextLine(),sc.nextLine());
                    }
                    else
                        System.out.println("**ACCOUNT NOT FOUND!\n_______________________");
                    break;
                }
                case 6:
                {
                    f=true;
                    break;
                }
            }
        }
    }
    // --------------
    static person search_person(String name,ArrayList<person>people)
    {
        for (person p:people)
            if (p.name.equals(name))
                return p;
        return null;
    }
    // --------------
}
