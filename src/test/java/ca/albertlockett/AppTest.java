package ca.albertlockett;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

  public AppTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  public GraphQLObjectType resolveType(TypeResolutionEnvironment env) {
    return (GraphQLObjectType) env.getSchema().getType("MyImplementingType"); 
  }

  public void testApp() {

    TypeDefinitionRegistry types = new SchemaParser().parse(new File("src/test/resources/schema.gql"));
    RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
      .type("Query", typeWiring -> typeWiring.dataFetcher("hello", env -> "Hello, world"))
      .type("MyInterface", typeWiring -> typeWiring.typeResolver(this::resolveType))
      .build();

    GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(types, wiring);
    System.out.println("Hello, world!");
    assertTrue(true);
  }

}
