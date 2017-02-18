package com.myvertx.app;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class AppCSVServer extends AbstractVerticle{
	
	private HttpServer server;
    private static final char DEFAULT_SEPARATOR = ';';
    private static final char DEFAULT_QUOTE = '"';
    private List<String> header;
	@Override
    public void start() throws Exception 
	{
		Router router = Router.router(vertx);

	    router.route().handler(BodyHandler.create());
	    router.route("/").handler(req -> {
	    	req.response().end("accueil");
	    });
	    router.get("/field/:field/value/:value").handler(this::handleGetLineByColumnValue);
	    server =  vertx.createHttpServer().requestHandler(router::accept).listen(8081);
	}
	
	/**handler permettant de retourner la line associée à une valeur donné pour un certain champ */
	private void handleGetLineByColumnValue(RoutingContext routingContext)
	{
		String field = routingContext.request().getParam("field");
		String value = routingContext.request().getParam("value");
		HttpServerResponse response = routingContext.response();
		vertx.fileSystem().readFile("data/Table_Ciqual_2016.csv", result -> {
		    if (result.succeeded()) {
		        Buffer buffer = result.result();
		        
		        
		       String[] csvStringList = buffer.toString().split("\n");
		       header = parseLine(csvStringList[0]);

		       for(String line : csvStringList)
		       {
		    	   String lineFieldValue = csvLineGetFieldValue(line, field, header);
		    	   if(lineFieldValue != null && lineFieldValue.equals(value))
		    	   {
		    		   response.end(csvLineToJson(line, header).encodePrettily());
		    	   }
		       }
		       response.setStatusCode(400).end("erreur");
		       
		    } else {
		        System.err.println("Oh oh ..." + result.cause());
		    }
		});
		
	}
	/** fonction qui convertit une ligne du cv en */
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
	public static String csvLineGetFieldValue(String cvsLine, String field, List<String> header)
	{
		try {
			return parseLine(cvsLine).get(header.indexOf(field));
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

	
}
