var MainText;
var lastStartIndex=0;
var lastEndIndex=0;
var routeTypeForPath="bicycle";
var mainSerachType="adress";
var serviceName;
var serviceLat;
var serviceLng;
var oldSize=0;
var actionState;
var nearestLoc =[];
var nearestStreet = [];
var nearestCity = [];
var nearestState = [];
var nearestPostalCode = [];
var Nearestindex = 0;
var allLat = [];
var allLng = [];
var routeAdded = false;
window.edu_ubbcluj_web_user_MapLoader = function() {

	

	var lat,lng;
	var actualPOIkey;
	var startLng;
	var startLat;
	var endLng;
	var endLat;
	//leellenorizni, amikor rateszem a contentet h mi a key
	
	var beginLat  = this.getState().latCoord;
	var beginLng  = this.getState().lngCoord;
	
	var options = {
            elt: document.getElementById('map'),             // ID of map element on page
            zoom: 14,                                        // initial zoom level of the map
            latLng: { lat:beginLat, lng:beginLng }     // center of map in latitude/longitude
        };

        // construct an instance of MQA.TileMap with the options object


        window.map = new MQA.TileMap(options);
        navigator.geolocation.getCurrentPosition(function(position) {
            map.setCenter({ lat: position.coords.latitude, lng: position.coords.longitude });
          });
        
        MQA.withModule('largezoom','viewoptions','geolocationcontrol','mousewheel', function() {

            map.addControl(
              new MQA.LargeZoom(),
              new MQA.MapCornerPlacement(MQA.MapCorner.TOP_LEFT, new MQA.Size(5,5))
            );


            map.addControl(new MQA.ViewOptions());

            map.addControl(
              new MQA.GeolocationControl(),
              new MQA.MapCornerPlacement(MQA.MapCorner.TOP_RIGHT, new MQA.Size(10,50))
            );

            map.enableMouseWheelZoom();
          });
}


	setStartPoint = function(X,Y){
		caption = "startPoint"+lastStartIndex;
		map.removeShape(map.getByKey(caption)); 
		lastStartIndex = lastStartIndex + 1;
		MQA.withModule('geocoder', function() {
			 MQA.Geocoder.constructPOI = function(location) {
			 	 lat = location.latLng.lat,
	            lng = location.latLng.lng,
	            p = new MQA.Poi({ lat: lat, lng: lng });
			 	 p.draggable = false;
			 	 caption2 = 'startPoint'+lastStartIndex;
			 	 p.key = caption2;
	
	        p.setRolloverContent('start-point');
	        p.setInfoTitleHTML(p.getRolloverContent());
	        p.setIcon(new MQA.Icon('http://open.mapquestapi.com/staticmap/geticon?uri=poi-red_1.png', 20, 30));
	        map.addShape(p);	       
	        return p;
	    };
			
			 
		    // executes a geocode with an object containing lat/lng properties,
		    // adds result to the map, and calls showAddress once geocoding is complete
		    map.reverseGeocodeAndAddLocation(
		      { lat: X, lng: Y }, showAddress
		    );
		 
		    // show the address of the geocoded location
		    function showAddress(data) {
		      var adress = '';
		      var response = data.results[0].locations[0];
		      adress = response.street + ', ';
		      adress += response.adminArea5 + ', ' + response.adminArea3 + ' ' + response.postalCode;
		      if(document.getElementById('aPoint')==null){
		    	  document.getElementById('nearestStart').value = adress;
		    	  var evt = document.createEvent('HTMLEvents');
			      evt.initEvent('change', true, false);
			      ns = document.getElementById('nearestStart');
			      ns.dispatchEvent(evt);
		      }
		      if(document.getElementById('nearestStart')==null){
		    	  document.getElementById('aPoint').value = adress;  
			      var evt2 = document.createEvent('HTMLEvents');
			      evt2.initEvent('change', true, false);
			      st = document.getElementById('aPoint');
			      st.dispatchEvent(evt2); 
		      }	      
		    }
		  });
		map.removeShape(map.getByKey("kezdeti"));
		map.bestFit();
	}
	
	setEndPoint = function(X,Y){
		caption = "endPoint"+lastEndIndex;
		map.removeShape(map.getByKey(caption)); 
		lastEndIndex = lastEndIndex + 1;
		MQA.withModule('geocoder', function() {
			//overwrite POI-s attributes
			 MQA.Geocoder.constructPOI = function(location) {
				 	 lat = location.latLng.lat,
	                lng = location.latLng.lng,
	                p = new MQA.Poi({ lat: lat, lng: lng });
				 	 p.draggable = false;
				 	 
				p.key = 'endPoint'+lastEndIndex;
	            p.setRolloverContent('end-point');
	            p.setInfoTitleHTML(p.getRolloverContent());
	            map.addShape(p);
	           p.setIcon(new MQA.Icon('http://open.mapquestapi.com/staticmap/geticon?uri=poi-green_1.png', 20, 30));
	            return p;
	        };
		    // executes a geocode with an object containing lat/lng properties,
		    // adds result to the map, and calls showAddress once geocoding is complete
		    map.reverseGeocodeAndAddLocation(
		      { lat: X, lng: Y }, showAddress
		    );
		 
		    // show the address of the geocoded location
		    function showAddress(data) {
		      var adress = '';
		      var response = data.results[0].locations[0];
		      adress = response.street + ', ';
		      adress += response.adminArea5 + ', ' + response.adminArea3 + ' ' + response.postalCode;
		      document.getElementById('bPoint').value = adress;    
		      var evt = document.createEvent('HTMLEvents');
		      evt.initEvent('change', true, false);
		      endp = document.getElementById('bPoint');
		      endp.dispatchEvent(evt);
		      
		    }
		  });
		map.removeShape(map.getByKey("kezdeti")); 
		map.bestFit();
	}
	
	function createPlace(X,Y,name,id){
		MQA.withModule('geocoder', function() {
			 MQA.Geocoder.constructPOI = function(location) {
			 	 lat = location.latLng.lat,
	           lng = location.latLng.lng,
	           p = new MQA.Poi({ lat: lat, lng: lng });
			 	 p.draggable = false;
			 	 p.key = id;
		       p.setRolloverContent(name);
		       p.setInfoTitleHTML(p.getRolloverContent());
		       p.setIcon(new MQA.Icon('http://www.wsdot.wa.gov/NR/rdonlyres/91C3220B-A412-4821-9901-B002AD261CF7/28730/i405_TDM_icon_bike8.gif', 30, 30)); 
		       return p;
			 };			 
		    // executes a geocode with an object containing lat/lng properties,
		    // adds result to the map, and calls showAddress once geocoding is complete
		    map.reverseGeocodeAndAddLocation(
		      { lat: X, lng: Y }, showAddress
		    );		 
		    // show the address of the geocoded location
		    function showAddress(data) {		    	
		    }
		  });
		 MQA.Geocoder.processResults = function(response, map) { 
            if(response && response.info && response.info.statuscode == 0 && response.results) {
                var locations = response.results[0].locations;
                    var location = locations[0];
                    if (location) {    	 
                    	map.removeShape(map.getByKey(id));
                    	map.addShape(this.constructPOI(location));
                    }                         
              }
              map.bestFit();
          };	
	}
	
	function createService(X,Y,name,id){
		MQA.withModule('geocoder', function() {
			 MQA.Geocoder.constructPOI = function(location) {
			 	 lat = location.latLng.lat,
	           lng = location.latLng.lng,
	           p = new MQA.Poi({ lat: lat, lng: lng });
			 	 p.draggable = false;
			 	 p.key = id;
		       p.setRolloverContent(name);
		       p.setInfoTitleHTML(p.getRolloverContent());
		       p.setIcon(new MQA.Icon('http://zizaza.com/cache/icon_256/iconset/581024/581034/PNG/512/map_marker/home_home_icon_map_marker_flat_icon_home_png_map_marker_icon_png.png', 30, 30)); 
		       return p;
			 };			 
		    // executes a geocode with an object containing lat/lng properties,
		    // adds result to the map, and calls showAddress once geocoding is complete
		    map.reverseGeocodeAndAddLocation(
		      { lat: X, lng: Y }, showAddress
		    );		 
		    // show the address of the geocoded location
		    function showAddress(data) {		    	
		    }
		  });
		 MQA.Geocoder.processResults = function(response, map) { 
             if(response && response.info && response.info.statuscode == 0 && response.results) {
                 var locations = response.results[0].locations;
                     var location = locations[0];
                     if (location) {                   	 
                     	map.removeShape(map.getByKey(id));
                     	map.addShape(this.constructPOI(location));
                     }                         
               }
               map.bestFit();
           };		
	}
	
	function createService2(X,Y,name,id){
		MQA.withModule('geocoder', function() {
			 MQA.Geocoder.constructPOI = function(location) {
			 	 lat = location.latLng.lat,
	           lng = location.latLng.lng,
	           p = new MQA.Poi({ lat: lat, lng: lng });
			 	 p.draggable = false;
			 	 p.key = id;
		       p.setRolloverContent(name);
		       p.setInfoTitleHTML(p.getRolloverContent());
		       p.setIcon(new MQA.Icon('http://zizaza.com/cache/icon_256/iconset/581024/581034/PNG/512/map_marker/home_home_icon_map_marker_flat_icon_home_png_map_marker_icon_png.png', 30, 30)); 
		       return p;
			 };			 
		    // executes a geocode with an object containing lat/lng properties,
		    // adds result to the map, and calls showAddress once geocoding is complete
		    map.reverseGeocodeAndAddLocation(
		      { lat: X, lng: Y }, showAddress
		    );		 
		    // show the address of the geocoded location
		    function showAddress(data) {		    	
		    }
		  });	
	}
	
	
	window.edu_ubbcluj_web_user_JsConnecter = function() {
		
		routeTypeForPath=this.getState().routeType;
		mainSerachType=this.getState().searchType;
		serviceName = this.getState().serviceName;
		serviceLat = this.getState().serviceLat;
		serviceLng = this.getState().serviceLng;	
		allNames = this.getState().allNames;
		allLat = this.getState().allLat;
		allLng = this.getState().allLng;
		allSize = this.getState().allSize;
		actionState = this.getState().action;

		//search action
		if(actionState=="searchAction"){
			if(mainSerachType=='adress'){
	    		//search type was adress
	    		var text = document.getElementById('searchTextField').value;           	
	        	MQA.withModule('geocoder', function() {
	        		//overwrite POI's attributes
	        		 MQA.Geocoder.constructPOI = function(location) {
	        			 	 lat = location.latLng.lat,
	                         lng = location.latLng.lng,
	                         city = location.adminArea5,
	                         state = location.adminArea3,
	                         p = new MQA.Poi({ lat: lat, lng: lng });
	        			 	 p.draggable = true;
	        			 	 p.key="kezdeti";

	                    MQA.EventManager.addListener(p, 'mouseover', function(evt){
	                    	startPkey = "startPoint"+lastStartIndex;
	                    	endPkey = "endPoint"+ lastEndIndex;
	                    	
	                    	if((p.key!=startPkey)&&(p.key!=endPkey)){
	                            p.setInfoContentHTML('Set as: <button type="button" onclick="setStartPoint('+p.latLng.lat+','+p.latLng.lng+')">START</button>'+
	                           		 '<button type="button" onclick="setEndPoint('+p.latLng.lat+','+p.latLng.lng+')">END</button> ');    
	                    	}   
	                    });                  
	                     return p;
	                 };
	                
	                map.geocodeAndAddLocations(text, processRawData);              
	                function processRawData(response) {
	                    if (response.results.length > 0 && response.results[0].locations.length > 0) { 
	                    	var location = response.results[0].locations[0];     		
	                    	}else
	                    		{
	                    			alert(response.results.length);
	                    			alert("notfound");
	                    		}
	                    }
	                   
	                //first result
	                MQA.Geocoder.processResults = function(response, map) { 
	                    if(response && response.info && response.info.statuscode == 0 && response.results) {
	                        var locations = response.results[0].locations;
	                            var location = locations[0];
	                            if (location) {
	                            	map.removeShape(map.getByKey("kezdeti"));
	                            	map.addShape(this.constructPOI(location));
	                            }                         
	                      }
	                      map.bestFit();
	                  };
	              });
	    	}
			if(mainSerachType=='service'){
	    		//seach type was service
	    		createService(serviceLat,serviceLng,serviceName,"actualservice");
	    		
	    	}
			if(mainSerachType=='place'){
				//seach type was place
	    		createPlace(serviceLat,serviceLng,serviceName,"actualplace");	
	    	}		
		}
		if(actionState=="routeAction"){	           
	        	startPoint = map.getByKey("startPoint"+lastStartIndex);
	        	endPoint = map.getByKey("endPoint"+lastEndIndex);
	        	drawRoute(startPoint, endPoint, routeTypeForPath);
	        
	        
		}
		if(actionState=="serviceListAction"){
			if(allSize>-1){
				map.removeAllShapes();
				for(i=0;i<allSize;i++){
					caption = "allServices";
					createService2(allLat[i],allLng[i],allNames[i],caption);
				}
				map.bestFit();	
			oldSize = allSize;	
				
			}
		}
		if(actionState=="rateAction"){
			createService(serviceLat,serviceLng,serviceName,"actualservice");
		}
		if(actionState=="placeListAction"){
			if(allSize>-1){
				map.removeAllShapes();
				for(i=0;i<allSize;i++){
					caption = "allPlaces";
					createPlace(allLat[i],allLng[i],allNames[i],caption);
				}
				map.bestFit();	
			oldSize = allSize;	
				
			}
		}
		if(actionState=="nearestAction"){
			Nearestindex = 0;
				startPoint = map.getByKey("startPoint"+lastStartIndex);
				nearestLoc[0]=""+startPoint.latLng.lat+","+startPoint.latLng.lng;
				for(j=0;j<allSize;j++){
					nearestLoc[j+1]=""+allLat[j]+","+allLng[j];			
				}
				
			    MQA.withModule('directions', 'longurl', function() {
			        /*Executes callback to the Directions Service for a route matrix with 3 parameters.*/
			        MQA.Directions.routeMatrix(			   
			          /*The first parameter is an array of location objects.*/
			        		 nearestLoc,		   
			          /*The second parameter is an options object {allToAll:true | false}.
			            If none specified, it defaults to allToAll=false.*/
			          { routeOptions: { allToAll:false }},
			          /*The final parameter is a callback method to execute after completion.*/			          
			          renderMatrixResults
			        );
			      });	
		}	
	}
function renderMatrixResults(response) {
		var minDistance=10000;
		var minIndex = 0;
	    var allToAll = response.allToAll;
	    var distances = response.distance;
	    var times = response.time;
	    var locations = response.locations; 
	    var numRows = allToAll ? times.length : 1;
	    for (i = 0; i < numRows; i++) {
	      var location = locations[i];	 
	      var distanceList = allToAll ? distances[i] : distances;
	      for (j = 0; j < allSize+1; j++) {
	    	  if(distanceList[j]>0){
	    		  
	    		  if(distanceList[j]<minDistance){
	    			  minDistance = distanceList[j];
	    			  minIndex = j-1;
	    		  }
	    	  }
	      }
	    }
	    startPoint = map.getByKey("startPoint"+lastStartIndex);
	    endPoint  = new MQA.Poi({ lat:allLat[minIndex], lng:allLng[minIndex] });
	    endPoint.setIcon(new MQA.Icon('http://open.mapquestapi.com/staticmap/geticon?uri=poi-green_1.png', 20, 30));
	    routeType = 'shortest';
	    drawRoute(startPoint, endPoint, routeType);
	    var evt = document.createEvent('HTMLEvents');
	      evt.initEvent('change', true, false);
	      sp = document.getElementById('nearestStart');
	      sp.value = "";
	      sp.dispatchEvent(evt); 
	  }

function drawRoute(startPoint,endPoint, routeType){ 
	MQA.withModule('new-route', function() {
		
		 var opt = {
                request: {
               	 locations: [startPoint, endPoint ],
                    options: {
                        avoids: [],
                        avoidTimedConditions: false,
                        doReverseGeocode: true,
                        generalize: 0,
                        routeType:routeType,
                        timeType: 1,
                        locale: 'en_US',
                        unit: 'k',
                        enhancedNarrative: false,
                        drivingStyle: 2,
                        highwayEfficiency: 21.0
                    }
                },
                display: {
                    color: '#800000',
                    borderWidth: 5,
                    draggable: true,
                    draggablepoi: true
                },
                success: function displayNarrative(data) {
               	 map.bestFit();
                }
		 }
		 map.addRoute(opt);
		 map.bestFit();
	  });
}