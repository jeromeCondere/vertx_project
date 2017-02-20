package com.myvertx.app;

import java.util.ArrayList;
import java.util.List;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class AppCSVServer extends AbstractVerticle{
	
	private HttpServer server;
	private HttpClient client;
    private static final char DEFAULT_SEPARATOR = ';';
    private static final char DEFAULT_QUOTE = '"';
    private int port = 8081;
    private List<String> header;
    private long requestTimeout = 5000;
	@Override
    public void start(final Future future) throws Exception 
	{
		Router router = Router.router(vertx);

	    router.route().handler(BodyHandler.create());
	    router.route("/").handler(req -> {
	    	req.response().end("accueil");
	    });
	    
	    router.post("/ressource/availaible").handler(this::handleIsRessourceAvailable);
	    router.post("/line/by/columnValue").handler(this::handleGetLineByColumnValue);
	    server =  vertx.createHttpServer().requestHandler(router::accept).listen(port,done -> {
	          if (done.failed()) {
	              future.fail(done.cause());
	            } else {
	              future.complete();
	            }
	          });
	    HttpClientOptions options = new HttpClientOptions().setDefaultHost("localhost").setDefaultPort(8081);
		client = vertx.createHttpClient(options);
	}
	private void handleIsRessourceAvailable(RoutingContext routingContext)
	{
		HttpServerResponse response = routingContext.response();
		response.putHeader("content-type", "application/json");
		JsonObject bodyJson = routingContext.getBodyAsJson();
			// on verifie si le fichier existe
			vertx.fileSystem().exists(bodyJson.getString("path"), resultExist -> {
				if(resultExist.succeeded() && resultExist.result())
				{
					//s'il existe on verifie si on peut l'ouvrir
					OpenOptions options = new OpenOptions();
					vertx.fileSystem().open(bodyJson.getString("path"), options, resultOpen -> {
						if(resultOpen.succeeded())
							response.end(successMessage());
						else
							response.setStatusCode(400).end(errorMessage("file exists but can't be opened"));
					});
				}
				else 
					response.setStatusCode(400).end(errorMessage("file does not exist"));
			});
			
	}
	
	/**handler permettant de retourner la line associée à une valeur donné pour un certain champ */
	private void handleGetLineByColumnValue(RoutingContext routingContext)
	{
		
		
		HttpServerResponse response = routingContext.response();
		JsonObject bodyRequestJson = routingContext.getBodyAsJson();
		
		// TODO regler le problème lorsque le body n'est pas envoyé
		if(bodyRequestJson == null || bodyRequestJson.isEmpty())
			response.end(errorMessage("no body provided"));
		
		
		
		String field = bodyRequestJson.getString("field");
		String value = bodyRequestJson.getString("value");
		response.putHeader("content-type", "application/json");
		if(field == null || field.equals(""))
			response.end(errorMessage("no field provided"));
		
		/*Le Json fourni est de cette forme
		 * {
		 *  "field": field,
		 *  "value: value,
		 *  "file": path
		 * 
		 */
		//on regarde si la ressource est disponible
		
		client.post("/ressource/availaible", responseAvailaible -> {
			responseAvailaible.bodyHandler(bodyIsAvailable -> {
				
				JsonObject bodyIsAvailableJson = bodyIsAvailable.toJsonObject();
				//si la ressource existe et est disonible
				if(bodyIsAvailableJson.getString("message").equals("success"))
				{
					vertx.fileSystem().readFile(bodyRequestJson.getString("file"), result -> {
					    if (result.succeeded()) {
					        Buffer buffer = result.result();
					        
					        
					       String[] csvStringList = buffer.toString().split("\n");
					       //on recupère le header
					       header = parseLine(csvStringList[0]);
					       /* on recherche la ligne dont la colonne est egale à la valeur donnée
					       	  equivalent Select * from file where column = field
					       */
					       JsonArray resultLines = new JsonArray();
					       for(String line : csvStringList)
					       {
					    	   String lineFieldValue = csvLineGetFieldValue(line, field, header);
					    	   // lorsqu'une ligne est trouvée on l'ajoute a l'array
					    	   if(lineFieldValue != null && lineFieldValue.equals(value))
					    	   {
					    		   resultLines.add(csvLineToJson(line, header).encodePrettily()) ;
					    	   }
					       }
					       
					       response.end(resultLines.encodePrettily());
					       
					       
					    } else {
					        System.err.println("Oh oh ..." + result.cause());
					    }
					});
					
					
				}
				//si la ressource n'existe pas
				else
					response.setStatusCode(400).end(bodyIsAvailable.toJsonObject().encodePrettily());
			  });
		  }).putHeader("content-type", "application/json")
			.setTimeout(4000)
		  	.end(new JsonObject()
		  		.put("path", bodyRequestJson.getString("file"))
		  		.encodePrettily()
		  		);
		
	}
	/** fonction qui convertit une ligne du csv en JsonObject */
	public static JsonObject csvLineToJson(String cvsLine, List<String> header) {
		JsonObject json = new JsonObject();
		for( String field : header)
		{
			String value = csvLineGetFieldValue(cvsLine, field, header);
			if(value == null)
				return null;
			json.put(field, value);
		}
		return json;
    }
	
	/** revient à faire <br>
	 * SELECT field FROM csvline WHERE field = value
	 * */
	public static String csvLineGetFieldValue(String csvLine, String field, List<String> header)
	{
		try {
			return parseLine(csvLine).get(header.indexOf(field));
		}
		catch (IndexOutOfBoundsException e)
		{
			return null;
		}
		
	}

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }
    
    public String errorMessage(String cause)
    {
    	return new JsonObject().put("message", "error")
    			   .put("cause", cause)
    			   .encodePrettily();
    }
    public String successMessage()
    {
    	return new JsonObject().put("message", "success").encodePrettily();
    }
	
}
