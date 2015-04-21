var MainText;
var lastStartIndex=0;
var lastEndIndex=0;
window.edu_ubbcluj_web_MapLoader = function() {

	

	var lat,lng;
	var actualPOIkey;
	var startLng;
	var startLat;
	var endLng;
	var endLat;
	//leellenorizni, amikor rateszem a contentet h mi a key
	
	
	
	var options = {
            elt: document.getElementById('map'),             // ID of map element on page
            zoom: 12,                                        // initial zoom level of the map
            latLng: { lat:46.766667, lng:23.583333 }     // center of map in latitude/longitude
        };

        // construct an instance of MQA.TileMap with the options object


        window.map = new MQA.TileMap(options);
        //MQA.TileMap.setSize(MQA.setSize(200,200));
        
        MQA.withModule('largezoom','traffictoggle','viewoptions','geolocationcontrol','insetmapcontrol','mousewheel', function() {

            map.addControl(
              new MQA.LargeZoom(),
              new MQA.MapCornerPlacement(MQA.MapCorner.TOP_LEFT, new MQA.Size(5,5))
            );

            map.addControl(new MQA.TrafficToggle());

            map.addControl(new MQA.ViewOptions());

            map.addControl(
              new MQA.GeolocationControl(),
              new MQA.MapCornerPlacement(MQA.MapCorner.TOP_RIGHT, new MQA.Size(10,50))
            );

            /*Inset Map Control options */
            var options={
              size:{width:150, height:125},
              zoom:3,
              mapType:'map',
              minimized:true
            };

            map.addControl(
              new MQA.InsetMapControl(options),
              new MQA.MapCornerPlacement(MQA.MapCorner.BOTTOM_RIGHT)
            );

            map.enableMouseWheelZoom();
          });
     
       
        /*document.getElementById('getDirection').onclick=function(){
        	
        	alert("alma");
        };*/
        
        
        document.getElementById('topsearchButton').onclick=function(){
        	var text = document.getElementById('searchTextField').value;
        	MQA.withModule('geocoder', function() {
        		//felulirom az alapertelmezett pontot
        		 MQA.Geocoder.constructPOI = function(location) {
        			 	 lat = location.latLng.lat,
                         lng = location.latLng.lng,
                         city = location.adminArea5,
                         state = location.adminArea3,
                         p = new MQA.Poi({ lat: lat, lng: lng });
        			 	 p.draggable = true;
        			 	 p.key="kezdeti";
        			 	
        			 	 
                    // p.setRolloverContent('lat: '+p.latLng.lat+' lng: '+p.latLng.lng);
                    // p.setInfoTitleHTML(p.getRolloverContent());
                     p.setInfoContentHTML('Set as: <button type="button" onclick="setStartPoint('+p.latLng.lat+','+p.latLng.lng+')">START</button>'+
                    		 '<button type="button" onclick="setEndPoint('+p.latLng.lat+','+p.latLng.lng+')">END</button> ');

                    // map.addShape(p);
                    MQA.EventManager.addListener(p, 'mouseover', function(evt){
                    	startPkey = "startPoint"+lastStartIndex;
                    	endPkey = "endPoint"+ lastEndIndex;
                    	
                    	if((p.key!=startPkey)&&(p.key!=endPkey)){
                    		//p.setRolloverContent('lat: '+p.latLng.lat+' lng: '+p.latLng.lng);
                            //p.setInfoTitleHTML(p.getRolloverContent());
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
                   
                //felulirjuk, hogy csak a legelso talalatot mutassa
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
        };	
        
        document.getElementById('getDirection').onclick=function(){
        	startPoint = map.getByKey("startPoint"+lastStartIndex);
        	endPoint = map.getByKey("endPoint"+lastEndIndex);
        	
        	//var e = document.getElementById("routeType").value;
        	//var val = e.options[e.selectedIndex].text
        	//type ='bicycle';//
        	type = document.getElementById("rtype").innerHTML;
        	
        	MQA.withModule('new-route', function() {
        		 var opt = {
                         request: {
                        	 locations: [
                        	            startPoint,endPoint
                        	           ],

                             options: {
                                 avoids: [],
                                 avoidTimedConditions: false,
                                 doReverseGeocode: true,
                                 generalize: 0,
                                 routeType: type,
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
                        	 if (data.route) {
                                 var legs = data.route.legs,
                                     html = '',
                                     i = 0,
                                     j = 0,
                                     trek,
                                     maneuver;

                                 html += '<table><tbody>';

                                 for (; i<legs.length; i++) {
                                     for (j=0; j<legs[i].maneuvers.length; j++) {
                                         maneuver = legs[i].maneuvers[j];
                                         html += '<tr>';
                                         html += '<td>';

                                         if (maneuver.iconUrl) {
                                             html += '<img src="' + maneuver.iconUrl + '" />  ';
                                         }

                                         for (k=0; k<maneuver.signs.length; k++) {
                                             var sign = maneuver.signs[k];

                                             if (sign && sign.url) {
                                                 html += '<img src="' + sign.url + '" />  ';
                                             }
                                         }

                                         html += '</td><td>' + maneuver.narrative + '</td>';
                                         html += '</tr>';
                                     }
                                 }

                                 html += '</tbody></table>';
                                 document.getElementById('route-results').innerHTML = html;
                             }
                         }
        		 }
        		 map.addRoute(opt);
        		 map.bestFit();
        		// alert("ut hozzadava");
        	  });
        } ;
        
       
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
		      document.getElementById('aPoint').value = adress;
		      document.getElementById('nearestStart').value = adress;
		    }
		  });
		map.removeShape(map.getByKey("kezdeti"));
		map.bestFit();
	}
	
	setEndPoint = function(X,Y){
		caption = "endPoint"+lastEndIndex;
		map.removeShape(map.getByKey(caption)); 
		lastEndIndex = lastEndIndex + 1;
		map.removeShape(map.getByKey("endPoint"+lastEndIndex)); 
		MQA.withModule('geocoder', function() {
			
			//felulirom az alapertelmezett pontot
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
		    }
		  });
		map.removeShape(map.getByKey("kezdeti")); 
		map.bestFit();
	}
	

