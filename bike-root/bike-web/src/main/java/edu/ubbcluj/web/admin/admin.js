var mainSerachType = "adress";
var serviceName = "none";
var serviceLat;
var serviceLng;
var actionType;

window.edu_ubbcluj_web_admin_AdminMapLoader = function() {

	var lat,lng;
	var actualPOIkey;
	var startLng;
	var startLat;
	var endLng;
	var endLat;
	
	var beginLat  = this.getState().latCoord;
	var beginLng  = this.getState().lngCoord;

	var options = {
            elt: document.getElementById('map'),             // ID of map element on page
            zoom: 14,                                        // initial zoom level of the map
            latLng: { lat:beginLat, lng:beginLng }     // center of map in latitude/longitude
        };

        // construct an instance of MQA.TileMap with the options object
        window.map = new MQA.TileMap(options);

        //dinamic ceter of the map
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

function createService(X,Y,name){
	
	MQA.withModule('geocoder', function() {
		 MQA.Geocoder.constructPOI = function(location) {
		 	 lat = location.latLng.lat,
           lng = location.latLng.lng,
           p = new MQA.Poi({ lat: lat, lng: lng });
		 	 p.draggable = false;
		 	p.key = "actualservice";
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
                	map.removeShape(map.getByKey("actualservice"));
                	map.addShape(this.constructPOI(location));
                }                         
          }
          map.bestFit();
      };
	
}

function createPlace(X,Y,name){
	
	MQA.withModule('geocoder', function() {
		 MQA.Geocoder.constructPOI = function(location) {
		 	 lat = location.latLng.lat,
           lng = location.latLng.lng,
           p = new MQA.Poi({ lat: lat, lng: lng });
		 	 p.draggable = false;
		 	p.key = "actualplace";
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
                	map.removeShape(map.getByKey("actualplace"));
                	map.addShape(this.constructPOI(location));
                }                         
          }
          map.bestFit();
      };
	
}
	

window.edu_ubbcluj_web_admin_AdminJsConnecter = function() {
	
	mainSerachType=this.getState().searchType;
	serviceName = this.getState().serviceName;
	serviceLat = this.getState().serviceLat;
	serviceLng = this.getState().serviceLng;
	actionType = this.getState().actionType;

	
	if(actionType=="searchAction"){
		if (mainSerachType=="adress"){
			//if the search type was adress
			var text = document.getElementById('searchTextField').value;
	    	MQA.withModule('geocoder', function() {
	    		//redifine the POI's attributes
	    		 MQA.Geocoder.constructPOI = function(location) {
	    			 	 lat = location.latLng.lat,
	                     lng = location.latLng.lng,
	                     city = location.adminArea5,
	                     state = location.adminArea3,
	                     p = new MQA.Poi({ lat: lat, lng: lng });
	    			 	 p.draggable = true;
	    			 	 p.key="kezdeti";
	    			 	var evt = document.createEvent('HTMLEvents');
	    			    evt.initEvent('change', true, false);			 	
	    			 	var x = document.getElementById('lat');
	    			 	x.value = lat;
	    			 	var y = document.getElementById('lng');
	    			 	y.value = lng;
	    			 	x.dispatchEvent(evt);
	    			 	y.dispatchEvent(evt);
	                MQA.EventManager.addListener(p, 'mouseout', function(evt){
	                	var evt = document.createEvent('HTMLEvents');
	                    evt.initEvent('change', true, false);                	
	    			 	var x = document.getElementById('lat');
	    			 	x.value = p.latLng.lat;
	    			 	var y = document.getElementById('lng');
	    			 	y.value = p.latLng.lng;
	    			 	x.dispatchEvent(evt);
	    			 	y.dispatchEvent(evt);
	    			 	
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
	                		}
	                }
	               
	            //over write to show only the first result
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
		if (mainSerachType=="service"){
			createService(serviceLat,serviceLng,serviceName);
		}
		if (mainSerachType=="place"){
			createPlace(serviceLat,serviceLng,serviceName);
		}
	}
	if(actionType=="deletePlace"){
		map.removeShape(map.getByKey("actualplace"));
		map.bestFit();
	}
	if(actionType=="deleteService"){
		map.removeShape(map.getByKey("actualservice"));
		map.bestFit();
	}
	if(actionType=="insertPlace"){
		map.removeShape(map.getByKey("kezdeti"));
		map.bestFit();
	}
	if(actionType=="insertService"){
		map.removeShape(map.getByKey("kezdeti"));
		map.bestFit();
	}
    

}



	

