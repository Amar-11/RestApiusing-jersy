# RestApiusing-jersy
this is about deploy rest api using jersy( jax-rs)

What is JAX-RS:-  JAX-RS: Java API for RESTful Web Services (JAX-RS) is a Java programming language API spec that provides support in creating web
services according to the Representational State Transfer (REST) architectural pattern. JAX-RS uses annotations, introduced in Java SE 5, 
to simplify the development and deployment of web service clients and endpoints. All versions of JAX-RS are part of the Java Specification Requests (JSRs):

I’m using the Jersey, the reference implementation of JAX-RS.
Annotations
JAX-RS annotations allow to identify what a resource class or class method will serve requests for. JAX-RS ensures portability of REST API code
across all Java EE-compliant application servers. The most common annotations are described in the table below.

Annotation	Package Detail
@GET	import javax.ws.rs.GET;
@Produces	import javax.ws.rs.Produces;
@Path	import javax.ws.rs.Path;
@PathParam	import javax.ws.rs.PathParam;
@QueryParam	import javax.ws.rs.QueryParam;
@POST	import javax.ws.rs.POST;
@Consumes	import javax.ws.rs.Consumes;
@FormParam	import javax.ws.rs.FormParam;
@PUT	import javax.ws.rs.PUT;
@DELETE	import javax.ws.rs.DELETE;

//here is the implimentation of this jersy api , here the process by process implimentation .

Create Sample Resource: Ping

Now, let’s write some code. In this paragraph, we will try to create the first JAX-RS resource for ping the REST app:
http://localhost:8080/ping
which allows to ensure if the server is running. In our case, we’ll create 3 classes: PingResource for the JAX-RS resource /ping,
ShopApplication for the JAX-RS application, and a Jersey server for hosting the application.
You might wonder what is a “resource” class? According to JSR-311, a resource class is a Java class that uses JAX-RS annotations to implement 
a corresponding Web resource. Resource classes are POJOs that have at least one method annotated with @Path or a request method designator (JSR-311,
§3.1 Resource Classes)

The ping resource class:

package io.mincong.shop.rest;

import javax.ws.rs.HEAD;
import javax.ws.rs.Path;

@Path("ping")
public class PingResource {

  @HEAD
  public void ping() {
    // do nothing
  }
  
  Create a JAX-RS Application
Once we created the “ping” resource, we need a JAX-RS application to host it. A JAX-RS application consists of one or more resources, 
and zero or more provider. All REST applications need to extends Application. An application contains two methods: getClasses() and getSingletons(). 
Both can be used to get a set of root resource, provider and feature classes.

However, these objects have different life-cycles. The default life-cycle for resource class instances is per-request. The default 
life-cycle for providers (registered directly or via a feature) is singleton. In our case, I choose the per-request for the “ping” resource, 
which means that it goes to getClasses(). We will talk about singletons in the next articles. So, here’s the related Java code:

package io.mincong.shop.rest;

import java.util.*;
import javax.ws.rs.core.Application;

public class ShopApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> set = new HashSet<>();
    set.add(PingResource.class);
    return set;
  }

  @Override
  public Set<Object> getSingletons() {
    return Collections.emptySet();
  }
}
Running JAX-RS Application in Server
The next step is to create a Jersey server, which hosts the « Shop » application. The configuration for a Jersey server is really simple,
you only need to give two things:

The URI of the server
The JAX-RS applications to be deployed
Here’s the code:

package io.mincong.shop.rest;

import java.io.IOException;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost/").port(8080).build();
  }

  static final URI BASE_URI = getBaseURI();

  static HttpServer startServer() {
    ResourceConfig rc = ResourceConfig.forApplication(new ShopApplication());
    return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
  }

  public static void main(String[] args) throws IOException {
    System.out.println("Starting grizzly...");
    HttpServer httpServer = startServer();
    System.in.read();
    httpServer.shutdownNow();
  }
}
