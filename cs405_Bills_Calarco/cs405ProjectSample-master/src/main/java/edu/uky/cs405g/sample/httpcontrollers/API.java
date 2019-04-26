package edu.uky.cs405g.sample.httpcontrollers;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.uky.cs405g.sample.Launcher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

@Path("/api")
public class API {

    private Type mapType;
    private Gson gson;

    public API() {
        mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        gson = new Gson();
    }


    //curl http://localhost:9998/api/check
    //{"status_code":1}
    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response healthcheck() {

        String responseString = "{\"status_code\":0}";
        try {

            //Here is where you would put your system test, but this is not required.
            //We just want to make sure your API is up and active/
            //status_code = 0 , API is offline
            //status_code = 1 , API is online
            responseString = "{\"status_code\":1}";

        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }


    //curl http://localhost:9998/api/listlocations
    //{"779a038b-aacc-44ca-b8cc-99671475061f":"800 Rose St.","1e4494a9-5677-49e4-b59f-b77c7900c73f":"123 Campus Road"}
    @GET
    @Path("/listlocations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTeams() {
        String responseString = "{}";
        try {
            Map<String,String> teamMap = Launcher.dbEngine.getLocations();

            responseString = Launcher.gson.toJson(teamMap);

        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }
    //***CHANGES MADE***
    //curl http://localhost:9998/api/getservice/
    //{"address":"800 Rose St.","lid":"c078b038-8ad2-4f45-adf0-03a22fffa8b9"} WILL BE DIFFERENT
    @GET
    @Path("/getservice/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getService(@PathParam("id") String id) {
        String responseString = "{}";
        try {

            Map<String,String> teamMap = Launcher.dbEngine.getService(id); //getService is in DBEngine

            responseString = Launcher.gson.toJson(teamMap);

        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }

    //***CHANGES MADE***
    //curl http://localhost:9998/api/getprovider/
    //{"address":"800 Rose St.","lid":"c078b038-8ad2-4f45-adf0-03a22fffa8b9"} WILL BE DIFFERENT
    @GET
    @Path("/getprovider/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProv(@PathParam("id") String id) {
        String responseString = "{}";
        try {

            Map<String,String> teamMap = Launcher.dbEngine.getProv(id); //getProv is in DBEngine

            responseString = Launcher.gson.toJson(teamMap);

        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }

    //***CHANGES MADE***
    //curl http://localhost:9998/api/getpatient/
    //{"address":"800 Rose St.","lid":"c078b038-8ad2-4f45-adf0-03a22fffa8b9"} WILL BE DIFFERENT
    @GET
    @Path("/getpatient/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPati(@PathParam("id") String id) {
        String responseString = "{}";
        try {

            Map<String,String> teamMap = Launcher.dbEngine.getPati(id); //getPati is in DBEngine

            responseString = Launcher.gson.toJson(teamMap);

        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }

    //***CHANGES MADE***
    //curl http://localhost:9998/api/getdata/
    //{"address":"800 Rose St.","lid":"c078b038-8ad2-4f45-adf0-03a22fffa8b9"} WILL BE DIFFERENT
    @GET
    @Path("/getdata/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData(@PathParam("id") String id) {
        String responseString = "{}";
        try {

            Map<String,String> teamMap = Launcher.dbEngine.getData(id); //getProv is in DBEngine

            responseString = Launcher.gson.toJson(teamMap);

        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }
    //***removeservice***
    //curl http://localhost:9998/api/removelocation/ff2f86ba-ea87-4f5d-8d39-4bdd20b7a532
    //{"status":"1"}
    @GET
    @Path("/removeservice/{service_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteService(@PathParam("service_id") String servId) {
        String responseString = "{}";
        try {


            String queryString = "delete from Service WHERE service_id='" + servId + "'";

            System.out.println(queryString);

            int status = Launcher.dbEngine.executeUpdate(queryString);

            System.out.println("status: " + status);

            responseString = "{\"status\":\"" + status +"\"}";


        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }

    //***removeprovider***
    //curl http://localhost:9998/api/removelocation/ff2f86ba-ea87-4f5d-8d39-4bdd20b7a532
    //{"status":"1"}
    @GET
    @Path("/removeprovider/{provider_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProvider(@PathParam("provider_id") String provId) {
        String responseString = "{}";
        try {


            String queryString = "delete from Provider WHERE provider_id='" + provId + "'";

            System.out.println(queryString);

            int status = Launcher.dbEngine.executeUpdate(queryString);

            System.out.println("status: " + status);

            responseString = "{\"status\":\"" + status +"\"}";


        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }

    //***removepatient***
    //curl http://localhost:9998/api/removelocation/ff2f86ba-ea87-4f5d-8d39-4bdd20b7a532
    //{"status":"1"}
    @GET
    @Path("/removepatient/{pid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePatient(@PathParam("pid") String pid) {
        String responseString = "{}";
        try {


            String queryString = "delete from Patient WHERE pid='" + pid + "'";

            System.out.println(queryString);

            int status = Launcher.dbEngine.executeUpdate(queryString);

            System.out.println("status: " + status);

            responseString = "{\"status\":\"" + status +"\"}";


        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }

    //***removeservice***
    //curl http://localhost:9998/api/removelocation/ff2f86ba-ea87-4f5d-8d39-4bdd20b7a532
    //{"status":"1"}
    @GET
    @Path("/removedata/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteData(@PathParam("id") String did) {
        String responseString = "{}";
        try {


            String queryString = "delete from Data_Sources WHERE id='" + did + "'";

            System.out.println(queryString);

            int status = Launcher.dbEngine.executeUpdate(queryString);

            System.out.println("status: " + status);

            responseString = "{\"status\":\"" + status +"\"}";


        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(500).entity(exceptionAsString).build();
        }
        return Response.ok(responseString).header("Access-Control-Allow-Origin", "*").build();
    }

    //***EDITED TO BECOME addservice***
    //curl -d '{"address":"800 Rose St."}' -H "Content-Type: application/json" -X POST http://localhost:9998/api/addlocation
    //{"address":"800 Rose St.","lid":"ff2f86ba-ea87-4f5d-8d39-4bdd20b7a532"}
    @POST
    @Path("/addservice")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addServ(InputStream incomingData) {

        StringBuilder crunchifyBuilder = new StringBuilder();
        String returnString = null;
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }

            String jsonString = crunchifyBuilder.toString();
            Map<String, String> myMap = gson.fromJson(jsonString, mapType); //gets map from input str
            String address = myMap.get("address");
            String deptID = myMap.get("department_id");
            String servID = myMap.get("service_id");
            String taxID = myMap.get("taxid");
            Map<String,String> servMap = Launcher.dbEngine.getService(servID); //check to see if it already exists

            if(servMap.size() == 0) {

                Map<String,String> addMap = Launcher.dbEngine.getLoc(address); //check to see if it already exists
                if(addMap.size() == 0) {
                    Launcher.dbEngine.executeUpdate("insert into Location values ('" + address + "','" + taxID + "')");
                }

                Map<String,String> depMap = Launcher.dbEngine.getDept(deptID); //check to see if it already exists
                if(depMap.size() == 0) {
                    Launcher.dbEngine.executeUpdate("insert into Departments values ('" + taxID + "','" + deptID + "')");
                }

                Map<String,String> instMap = Launcher.dbEngine.getInst(taxID); //check to see if it already exists
                if(instMap.size() == 0) {
                    Launcher.dbEngine.executeUpdate("insert into Institution values ('" + taxID + "')");
                }

                //generate a new unique location Id
                //String locationId = UUID.randomUUID().toString();

                String createUsersTable = "insert into Service values ('" + address + "','" + deptID + "','" + servID + "','" + taxID + "')";

                System.out.println(createUsersTable);

                Launcher.dbEngine.executeUpdate(createUsersTable);

                servMap = Launcher.dbEngine.getService(servID);

                returnString = gson.toJson(servMap);


            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can't insert duplicate service!")
                        .header("Access-Control-Allow-Origin", "*").build();
            }


        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error")
                    .header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(returnString).header("Access-Control-Allow-Origin", "*").build();
    }

    //***EDITED TO BECOME addprovider***
    //curl -d '{"address":"800 Rose St."}' -H "Content-Type: application/json" -X POST http://localhost:9998/api/addlocation
    //{"address":"800 Rose St.","lid":"ff2f86ba-ea87-4f5d-8d39-4bdd20b7a532"}
    @POST
    @Path("/addprovider")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProv(InputStream incomingData) {

        StringBuilder crunchifyBuilder = new StringBuilder();
        String returnString = null;
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }

            String jsonString = crunchifyBuilder.toString();
            Map<String, String> myMap = gson.fromJson(jsonString, mapType); //gets map from input str
            String deptID = myMap.get("department_id");
            String npi = myMap.get("npi");
            Map<String,String> provMap = Launcher.dbEngine.getProv(npi); //check to see if it already exists

            if(provMap.size() == 0) {

                //generate a new unique location Id
                //String locationId = UUID.randomUUID().toString();

                String createUsersTable = "insert into Provider values ('" + npi + "','" + deptID + "')";

                System.out.println(createUsersTable);

                Launcher.dbEngine.executeUpdate(createUsersTable);

                provMap = Launcher.dbEngine.getProv(npi);

                returnString = gson.toJson(provMap);


            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can't insert duplicate provider!")
                        .header("Access-Control-Allow-Origin", "*").build();
            }


        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error")
                    .header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(returnString).header("Access-Control-Allow-Origin", "*").build();
    }


    //***EDITED TO BECOME addpatient***
    //curl -d '{"address":"800 Rose St."}' -H "Content-Type: application/json" -X POST http://localhost:9998/api/addlocation
    //{"address":"800 Rose St.","lid":"ff2f86ba-ea87-4f5d-8d39-4bdd20b7a532"}
    @POST
    @Path("/addpatient")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPati(InputStream incomingData) {

        StringBuilder crunchifyBuilder = new StringBuilder();
        String returnString = null;
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }

            String jsonString = crunchifyBuilder.toString();
            Map<String, String> myMap = gson.fromJson(jsonString, mapType); //gets map from input str
            String address = myMap.get("address");
            String npi = myMap.get("provider_id");
            String pid = myMap.get("pid");
            String ssn = myMap.get("ssn");
            Map<String,String> patiMap = Launcher.dbEngine.getPati(pid); //check to see if it already exists

            if(patiMap.size() == 0) {

                //generate a new unique location Id
                //String locationId = UUID.randomUUID().toString();

                String createUsersTable = "insert into Patient values ('" + address + "','" + npi + "','" + pid + "','" + ssn + "')";

                System.out.println(createUsersTable);

                Launcher.dbEngine.executeUpdate(createUsersTable);

                patiMap = Launcher.dbEngine.getPati(npi);

                returnString = gson.toJson(patiMap);


            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can't insert duplicate provider!")
                        .header("Access-Control-Allow-Origin", "*").build();
            }


        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error")
                    .header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(returnString).header("Access-Control-Allow-Origin", "*").build();
    }

    //***EDITED TO BECOME adddata***
    //curl -d '{"address":"800 Rose St."}' -H "Content-Type: application/json" -X POST http://localhost:9998/api/addlocation
    //{"address":"800 Rose St.","lid":"ff2f86ba-ea87-4f5d-8d39-4bdd20b7a532"}
    @POST
    @Path("/adddata")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addData(InputStream incomingData) {

        StringBuilder crunchifyBuilder = new StringBuilder();
        String returnString = null;
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }

            String jsonString = crunchifyBuilder.toString();
            Map<String, String> myMap = gson.fromJson(jsonString, mapType); //gets map from input str
            String data = myMap.get("data");
            String npi = myMap.get("provider_id");
            String pid = myMap.get("pid");
            String servID = myMap.get("ssn");
            String id = myMap.get("id");
            Map<String,String> dataMap = Launcher.dbEngine.getData(id); //check to see if it already exists

            if(dataMap.size() == 0) {

                //generate a new unique location Id
                //String locationId = UUID.randomUUID().toString();

                String createUsersTable = "insert into Data_Sources values ('" + data + "','" + npi + "','" + pid + "','" + servID + "','" + id + "')";

                System.out.println(createUsersTable);

                Launcher.dbEngine.executeUpdate(createUsersTable);

                dataMap = Launcher.dbEngine.getPati(npi);

                returnString = gson.toJson(dataMap);


            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Can't insert duplicate provider!")
                        .header("Access-Control-Allow-Origin", "*").build();
            }


        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            ex.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error")
                    .header("Access-Control-Allow-Origin", "*").build();
        }

        return Response.ok(returnString).header("Access-Control-Allow-Origin", "*").build();
    }

}
