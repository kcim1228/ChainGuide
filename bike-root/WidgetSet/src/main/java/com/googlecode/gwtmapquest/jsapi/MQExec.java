package com.googlecode.gwtmapquest.jsapi;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.googlecode.gwtmapquest.transaction.MQALatLng;
import com.googlecode.gwtmapquest.transaction.MQARectLL;

public final class MQExec {

	public static final String ROUTE_VERSION = "2";
	public static final String SEARCH_VERSION = "0";
	public static final String GEOCODE_VERSION = "1";
	public static final String ROUTEMATRIX_VERSION = "0";
	public static final String GETRECORDINFO_VERSION = "0";
	public static final String REVERSEGEOCODE_VERSION = "0";
	public static final String GETSESSION_VERSION = "1";
	   
	private String serverName = "localhost";
	private String serverPath = "mq";
	private int serverPort = 80;
	private String proxyServerPath = "";
	private String proxyServerName = "";
	private int proxyServerPort = 0;
	private long socketTimeout = 0;
	private String transactionInfo = "";

	public MQExec(String serverName, String serverPath, Integer serverPort,
            String proxyServerName, String proxyServerPath, Integer proxyServerPort) {
		
		if (serverName != null) {
			this.serverName = serverName;
		}
		if (serverPath != null) {
			this.serverPath = serverPath;
		}
		if (serverPort != null) {
			this.serverPort = serverPort;
		}
		
		if (proxyServerName != null) {
			this.proxyServerName = proxyServerName;
		}
		if (proxyServerPath != null) {
			this.proxyServerPath = proxyServerPath;
		}
		if (proxyServerPort != null) {
			this.proxyServerPort = proxyServerPort;
		}
	}

	public void setTransactionInfo(String value) {
		if (value.length() > 32) {
			transactionInfo = value.substring(0, 32);
		} else {
			transactionInfo = value;
		}
	}
	
	public String getRequestXml(String transaction, JsArray<MQObject> request, String strVersion) {
		String version = (strVersion == null || strVersion.length() == 0) ? "0" : strVersion;
		
		String result = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
		result += "<" + transaction + " Version=\"" + version + "\">\n";
		
		for (int i = 0; i < request.length(); i++) {
			result += request.get(i).saveXml();
			result += "\n";
		}
		
		result += "</" + transaction + ">";
		return result;
	}
	   
	public void doTransaction(final String transaction, JsArray<MQObject> request, String version, final RequestCallback callback) {
		String url = "";
		request.set(request.length(), MQAuthentication.newInstance(transactionInfo));
		String requestXML = getRequestXml(transaction, request, version);

		if (!proxyServerName.equals("")) {
			url += "http://" + this.proxyServerName;
			if (this.proxyServerPort != 0) {
				url += ":" + this.proxyServerPort;
			}
			url += "/";
		}

		url += this.proxyServerPath;
		url += this.proxyServerPath.indexOf("?") == -1 ? "?" : "&";
		url += "sname=" + this.serverName;
		url += "&spath=" + this.serverPath;
		url += "&sport=" + this.serverPort;

		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
		try {
			requestBuilder.sendRequest(requestXML, new RequestCallback() {
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						callback.onResponseReceived(request, response);
					} else {
						callback.onError(request, new Exception(transaction + ": " + response.getStatusCode() + ": " + response.getStatusText() + " " + response.getText()));
					}					
				}
				public void onError(Request request, Throwable error) {
					callback.onError(request, error);					
				}
			});
		} catch (RequestException e) {
			callback.onError(null, e);
		}
	}

	public void geocode(MQAbstractAddress address, MQGeocodeOptions options, final MQGeocodeCallback callback) {
		if(address == null) {
			throw new IllegalArgumentException("address is null");
		} 

		JsArray<MQObject> request = JsArray.createArray().cast();
		request.set(request.length(), address);
		
		if(options != null) {
			request.set(request.length(), options);
		}

		doTransaction("Geocode", request, this.GEOCODE_VERSION, new RequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				MQLocationCollection locations = MQLocationCollection.newInstance();
				com.google.gwt.xml.client.Document doc = XMLParser.parse(response.getText());
				NodeList nodeList = doc.getElementsByTagName("LocationCollection");
				if (nodeList.getLength() > 0) {
					String xml = nodeToString(nodeList.item(0));
					locations.loadXml(xml);
				}
				callback.onSuccess(locations, response);
			}
			public void onError(Request request, Throwable error) {
				callback.onError(request, error);
			}
		});
	}
		   
	/**
	 * Calculates a route using a collection of locations as origin and destination. These locations can be Address, GeoAddress, Intersection, or GeoIntersection objects.
	 * @param locations Collection of locations. (Please Note: This object will not be updated, it is simply used for the request.) 
	 * @param options Route options object, to modify behavior of route. (Please Note: This object will not be updated, it is simply used for the request.)
	 * @param sessionID The unique Session ID. 
	 */
	public void doRoute(MQLocationCollection locations, MQRouteOptions options, final String sessionID, final MQRouteCallback callback) {
		if (locations == null) {
			throw new IllegalArgumentException("locations is null");
		}
		if (options == null) {
			throw new IllegalArgumentException("options is null");
		}
		
		JsArray<MQObject> request = JsArray.createArray().cast();
		request.set(request.length(), locations);
		request.set(request.length(), options);
		request.set(request.length(), MQXmlNodeObject.newInstance("SessionID", sessionID));
		
		doTransaction("DoRoute", request, this.ROUTE_VERSION, new RequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				final MQRouteResults routeResults = MQRouteResults.newInstance();
				
				com.google.gwt.xml.client.Document doc = XMLParser.parse(response.getText());
				NodeList nodeList = doc.getElementsByTagName("RouteResults");
				if (nodeList.getLength() > 0) {
					String xml = nodeToString(nodeList.item(0));
					routeResults.loadXml(xml);
				}
				
				if (sessionID != null && sessionID.length() > 0){
					getRouteBoundingBoxFromSessionResponse(sessionID, new MQRouteBoundingBoxCallback() {
						public void onSuccess(MQARectLL boundingBox) {
							callback.onSuccess(routeResults, boundingBox);
						}
						public void onError(Request request, Throwable error) {
							callback.onError(request, error);
						}
					});
				} else {
					callback.onSuccess(routeResults, null);
				}
			}
			public void onError(Request request, Throwable error) {
				callback.onError(request, error);
			}
		});
	}

	/**
	 * Generates a request to the MapQuest server (as specified by the server name, path, and port number) to create a user Session. The Session is assigned a unique identifier which is used to access and update the Session information.
	 * @param session Object containing objects for the session. (Please Note: This object will not be updated, it is simply used for the request.)
	 */
	public void createSessionEx(MQSession session, final MQCreateSessionCallback callback) {
		if (session == null) {
			throw new IllegalArgumentException("session is null");
		}

		JsArray<MQObject> request = JsArray.createArray().cast();
		request.set(request.length(), session);
		
		doTransaction("CreateSession", request, GETSESSION_VERSION, new RequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				com.google.gwt.xml.client.Document doc = XMLParser.parse(response.getText());
				
				NodeList nodeList = doc.getElementsByTagName("SessionID");
				if (nodeList.getLength() > 0) {
					String sessionID = nodeList.item(0).getFirstChild().getNodeValue();
					callback.onSuccess(sessionID);
				} else {
					callback.onError(request, new Exception("SessionID element not in response: " + response.getText()));
				}
			}
			public void onError(Request request, Throwable error) {
				callback.onError(request, error);
			}
		});
	}

	public void getRouteBoundingBoxFromSessionResponse(String sessionID, final MQRouteBoundingBoxCallback callback) {
		if (sessionID == null || sessionID.length() == 0) {
			throw new IllegalArgumentException("sessionID is null or empty");
		}
		
		JsArray<MQObject> request = JsArray.createArray().cast();
		request.set(request.length(), MQXmlNodeObject.newInstance("SessionID", sessionID));

		doTransaction("GetRouteBoundingBoxFromSession", request, ROUTE_VERSION, new RequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				com.google.gwt.xml.client.Document doc = XMLParser.parse(response.getText());
				
				NodeList nodeList = doc.getElementsByTagName("LatLng");
				if (nodeList.getLength() > 0) {
					MQALatLng upperLeft = MQALatLng.newInstance();
					upperLeft.loadXml(nodeToString(nodeList.item(0)));
					
					MQALatLng lowerRight = MQALatLng.newInstance();
					lowerRight.loadXml(nodeToString(nodeList.item(1)));
					
					MQARectLL boundingBox = MQARectLL.newInstance(upperLeft, lowerRight);
					callback.onSuccess(boundingBox);
				} else {
					callback.onError(request, new Exception("LatLng element not in response: " + response.getText()));
				}
			}
			public void onError(Request request, Throwable error) {
				callback.onError(request, error);
			}
		});
	}
	
	/**
	 * @param node
	 * @return the XML string for the node and it's children (deep)
	 */
	private static String nodeToString(Node node) {
		StringBuffer result = new StringBuffer();

		if (node.getNodeType() == Node.TEXT_NODE) {
			result.append(node.getNodeValue());
			return result.toString();
		}
		
		result.append("<");
		result.append(node.getNodeName());
		
		if (node.hasAttributes())
			result.append(" ");
		
		NamedNodeMap attributes = node.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			result.append(attribute.getNodeName());
			result.append("=\"");
			result.append(attribute.getNodeValue());
			result.append("\" ");
		}
		
		if (node.hasChildNodes()) {
			result.append(">");
			
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				result.append(nodeToString(child));
			}
			
			result.append("</");
			result.append(node.getNodeName());
			result.append(">");
		} else {
			result.append("/>");
		}
		
		return result.toString();
	}
	
}
