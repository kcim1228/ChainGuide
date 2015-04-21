
window.edu_ubbcluj_web_AdminMapLoader = function() {

	

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
        
        //document.getElementById('update').onclick= Admin.kiir() ;
        
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
        			 	 //alert(document.getElementById('lat').value)
        			 	 
        			 	var evt = document.createEvent('HTMLEvents');
        			    evt.initEvent('change', true, false);
        			 	var x = document.getElementById('lat');
        			 	x.value = lat;
        			 	var y = document.getElementById('lng');
        			 	y.value = lng;
        			 	x.dispatchEvent(evt);
        			 	y.dispatchEvent(evt);
        			 	 
                    // p.setRolloverContent('lat: '+p.latLng.lat+' lng: '+p.latLng.lng);
                    // p.setInfoTitleHTML(p.getRolloverContent());
                     p.setInfoContentHTML('Set as: <button type="button" onclick="setStartPoint('+p.latLng.lat+','+p.latLng.lng+')">START</button>');

                    // map.addShape(p);
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
        
        
}




	

