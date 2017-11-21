package introsde.rest.activitypreference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import introsde.rest.activitypreference.model.Activity;
import introsde.rest.activitypreference.model.ActivityType;
import introsde.rest.activitypreference.model.Person;

public class App
{    
    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException, ParseException
    {
    	String protocol = "http://";
        String hostname = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostname);
        if (hostname.equals("127.0.0.1") || hostname.equals("127.0.1.1"))
        {
        	hostname = "localhost";
        }
        String port = "5900";

        // We need this so the App will run on Heroku properly where we got the assigned port
        // in an environment value named PORT
        if(String.valueOf(System.getenv("PORT")) != "null") {
        	port = String.valueOf(System.getenv("PORT"));
        }

        URI baseUrl = new URI(protocol + hostname + ":" + port + "/introsde/");

        JdkHttpServerFactory.createHttpServer(baseUrl, createApp());
        System.out.println("server starts on " + baseUrl + "\n [kill the process to exit]");
        /*System.out.println("Starting introsde standalone HTTP server...");
        JdkHttpServerFactory.createHttpServer(BASE_URI, createApp());
        System.out.println("Server started on " + BASE_URI + "\n[kill the process to exit]");*/
    }
    
    public static void init() throws ParseException, IOException {
    	//Insert ActivityType
    	ActivityType at1 = new ActivityType();
    	at1.setActivity_type("Social");
    	ActivityType.postActivityType(at1);
    	
    	ActivityType at2 = new ActivityType();
    	at2.setActivity_type("Sport");
    	ActivityType.postActivityType(at2);
    	
    	ActivityType at3 = new ActivityType();
    	at3.setActivity_type("Outdoor");
    	ActivityType.postActivityType(at3);
    	
    	//Insert Person 1
    	Person p1 = new Person();
    	p1.setFirstname("Massimo");
    	p1.setLastname("Rossi");
    	p1.setBirthdate("1991-01-15");
    	
    	Activity a1 = new Activity();
    	a1.setName("Running");
    	a1.setDescription("Running with friends");
    	a1.setPlace("City Centre Trento");
    	a1.setStartdate("2017-10-13T09:50:00.0");
    	
    	a1.setIdPerson(1);
    	a1.setIdActivityType(1);
    	a1.setActivityType(at1);
    	a1.setPerson(p1);
    	
    	List<Activity> tmp1 = new ArrayList<Activity>();
    	tmp1.add(a1);
    	p1.setActivities(tmp1);
    	Person.newPerson(p1);
    	
    	//Insert Person2
    	Person p2 = new Person();
    	p2.setFirstname("Luca");
    	p2.setLastname("Bianchi");
    	p2.setBirthdate("1999-02-10");
    	
    	Activity a2 = new Activity();
    	a2.setName("Dancing");
    	a2.setDescription("Dancing Group Dances");
    	a2.setPlace("Iris Club");
    	a2.setStartdate("2017-10-13T21:00:00.0");
    	
    	a2.setIdPerson(2);
    	a2.setIdActivityType(1);
    	a2.setActivityType(at1);
    	a2.setPerson(p2);
    	
    	List<Activity> tmp2 = new ArrayList<Activity>();
    	tmp2.add(a2);
    	p2.setActivities(tmp2);
    	Person.newPerson(p2);
    	
    	//Insert Person 3
    	Person p3 = new Person();
    	p3.setFirstname("Filippo");
    	p3.setLastname("Turri");
    	p3.setBirthdate("1999-10-23");
    	
    	Activity a3 = new Activity();
    	a3.setName("Boxing");
    	a3.setDescription("Boxing in the gym");
    	a3.setPlace("Santa Chiara Sport Gym");
    	a3.setStartdate("2017-10-14T18:00:00.0");
    	
    	a3.setIdPerson(3);
    	a3.setIdActivityType(2);
    	a3.setActivityType(at2);
    	a3.setPerson(p3);
    	
    	List<Activity> tmp3 = new ArrayList<Activity>();
    	tmp3.add(a3);
    	p3.setActivities(tmp3);
    	Person.newPerson(p3);
    	
    	//Insert Person 4
    	Person p4 = new Person();
    	p4.setFirstname("Piano");
    	p4.setLastname("Neri");
    	p4.setBirthdate("1993-05-15");
    	
    	Activity a4 = new Activity();
    	a4.setName("Skiing");
    	a4.setDescription("Skiing on the Bondone Mountain");
    	a4.setPlace("Bondone Mountain");
    	a4.setStartdate("2017-10-15T0800:00.0");
    	
    	a4.setIdPerson(4);
    	a4.setIdActivityType(2);
    	a4.setActivityType(at2);
    	a4.setPerson(p4);
    	
    	List<Activity> tmp4 = new ArrayList<Activity>();
    	tmp4.add(a4);
    	p4.setActivities(tmp4);
    	Person.newPerson(p4);
    	
    	//Insert Person 5
    	Person p5 = new Person();
    	p5.setFirstname("Gianni");
    	p5.setLastname("Cassia");
    	p5.setBirthdate("1994-05-25");
    	
    	Activity a5 = new Activity();
    	a5.setName("Skateboarding");
    	a5.setDescription("Skateboarding with friends");
    	a5.setPlace("Rovereto Centre");
    	a5.setStartdate("2017-10-17T16:00:00.0");
    	
    	a5.setIdPerson(5);
    	a5.setIdActivityType(1);
    	a5.setActivityType(at1);
    	a5.setPerson(p5);
    	
    	List<Activity> tmp5 = new ArrayList<Activity>();
    	tmp5.add(a5);
    	p5.setActivities(tmp5);
    	Person.newPerson(p5);
    	
    	//File f = new File("database.sqlite");
    	//if(f.exists() && !f.isDirectory()) { 
    	//    f.delete();
    	//}
    	//copyFileUsingStream(new File("database_origin.sqlite"), new File("database.sqlite"));
    }

    public static ResourceConfig createApp() {
    	System.out.println("Starting introsde REST services...");
        return new MyApplicationConfig();
    }
    
    /*
    public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }*/
}
