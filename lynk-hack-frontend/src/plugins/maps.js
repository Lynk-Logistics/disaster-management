
function Maps (params) {
  var defaults = {
    locations: [],
    loggerEnabled: false,
    mapsDiv: '',
    listDiv: '',
    needList: false,
    needPopUp: false,
    findLocationOnLaunch: true,
    needZoom: true,
    zoomLevel: 5.5,
    panX: 0,
    beforeIcon: '',
    afterIcon: ''
  }

  var inputParams = params || {}
  this.locations = inputParams.locations || defaults.locations
  this.loggerEnabled = inputParams.loggerEnabled || defaults.loggerEnabled
  this.mapsDiv = inputParams.mapsDiv || defaults.mapsDiv
  this.listDiv = inputParams.listDiv || defaults.listDiv
  this.needList = inputParams.needList || defaults.needList
  this.needPopUp = inputParams.needPopUp === false ? false : defaults.needPopUp
  this.findLocationOnLaunch = inputParams.findLocationOnLaunch === false ? false : defaults.needZoom
  this.needZoom = inputParams.needZoom === false ? false : defaults.needZoom
  this.zoomLevel = inputParams.zoomLevel || defaults.zoomLevel
  this.panX = inputParams.panX || defaults.panX
  this.beforeIcon = inputParams.beforeIcon || defaults.beforeIcon
  this.afterIcon = inputParams.afterIcon || defaults.afterIcon
  this.infoWindow = ''
  this.location = ''
  this.markers = ''
  this.markerCluster = ''
  this.locationSelectCallback = function () { };

  this.logger('Maps constructor', params)
}

Maps.prototype.logger = function (name, data) {
  if (this.loggerEnabled) {
    console.log(name, data || '')
  }
}

Maps.prototype.listLayout = function () {
  var data = this.locations
  var newData = `<div class="list-container">`
  data.map(function(place) {
    newData = newData + `<li class="maps-list" data-num="` + place.id + `">`
    + place.name +`</li>`
  })
  newData = newData + `</div>`
  return newData
}

Maps.prototype.showContents = function (callback) {
  if (this.mapsDiv) {
    this.logger('Showing Maps')
    this.showMaps()
  } else {
    this.logger('Maps Div is not provided')
  }
  if (typeof callback === 'function') {
    this.setLocationSelectCallBack(callback)
    this.logger('Callback is set')
  } else {
    this.logger('Error: Callback Should be a function')
  }
}

Maps.prototype.showMaps = function () {
  this.createMap()
  this.createInfoWindow()
  this.createMarkerCluster()
//   this.setBounds()
}

Maps.prototype.createMap = function () {
  var centerPosition = { lat: 13.057194, lng: 80.277987 }
  this.map = new google.maps.Map(document.getElementById(this.mapsDiv), {
    center: centerPosition,
    scrollwheel: false,
    disableDefaultUI: true,
    zoomControl: true,
    gestureHandling: 'cooperative',
    zoom: this.zoomLevel,
  })
  this.setPan()
}

Maps.prototype.handleLocationError = function (browserSupport) {
  this.infoWindow.setPosition(this.map.getCenter())
  this.infoWindow.setContent(browserSupport ? 'ok' : 'done')
  // this.infoWindow.open(this.map)
}

Maps.prototype.createInfoWindow = function () {
  this.infoWindow = new google.maps.InfoWindow({
    content: ''
  })
  // try to get user geo location and set for the map
  if (this.findLocationOnLaunch) {
    var me = this
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {
        var pos = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };

        me.map.setCenter(pos)
      }, function() {
        // me.handleLocationError(true)
        // not able to get user location - so try setBounds
        me.setBounds()
      });
    } else {
      // Browser doesn't support Geolocation
      me.handleLocationError(false)
    }
  } else {
    // if find location is not enabled try to center the map with available locations
    this.setBounds()
  }
}

Maps.prototype.setBounds = function () {
  if (this.locations && this.locations.length > 0) {
    var lat_min = this.locations[0].latitude;
    var lat_max = this.locations[0].latitude;
    var lng_min = this.locations[0].longitude;
    var lng_max = this.locations[0].longitude;

    // process to find out the min/max lat and lng values
    var i;
    var len = this.locations.length;
    var lat;
    var lng;
    for (i = 1; i < len; i++) {
      lat = this.locations[i].latitude;
      lat_min = (lat < lat_min) ? lat : lat_min;
      lat_max = (lat > lat_max) ? lat : lat_max;

      lng = this.locations[i].longitude;
      lng_min = (lng < lng_min) ? lng : lng_min;
      lng_max = (lng > lng_max) ? lng : lng_max;
    }
    var zoomAdjustment = 0.001
    lat_min -= zoomAdjustment
    lat_max += zoomAdjustment
    lng_min -= zoomAdjustment
    lng_max += zoomAdjustment

    this.map.setCenter(new google.maps.LatLng(
      ((lat_max + lat_min) / 2.0),
      ((lng_max + lng_min) / 2.0)
    ));
    //fix for show two markers on same location
    var bounds = new google.maps.LatLngBounds();
    for (var j = 0; j < this.markers.length; j++) {
      bounds.extend(this.markers[j].getPosition());
    }
    this.map.fitBounds(bounds);
  }
}

Maps.prototype.openInfoWindow = function (marker, location) {

  this.infoWindow.setContent('<div style="padding:10px;width:194px;margin-right:-11px"><span style="font-size:14px">' + location.name + '<br/>' + '<button data-lat="' + location.latitude + '" data-lng="' + location.longitude + '" id="infoButton" style="margin-top:10px;color:#fff;padding:10px;font-size:13px;border:solid;border-radius:10px;background:#1428a0;font-weight:bold">View the issue</button></div>')
  setTimeout(() => {
    this.infoWindow.open(this.map, marker, location)
    this.registerMapEvents()
  }, 100)
}

Maps.prototype.createMarkerCluster = function () {
  const vm = this
  this.markers = this.locations.map(function (location, i) {
    var marker = vm.createMarker(location, vm.beforeIcon)
    marker.addListener('click', function () {
      vm.selectLocation(location, marker)
    })
    return marker
  })
  if (this.needZoom) {
    this.markerCluster = new MarkerClusterer(this.map, this.markers,
      { imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m', maxZoom: 13 })
  }
  this.markerCluster.setMinClusterSize(3)
  // removing clustering #SEG-2124
}

Maps.prototype.createMarker = function (location, icon) {
  var LatLng = { lat: Number(location.latitude), lng: Number(location.longitude) }
  var marker = new google.maps.Marker({
    position: LatLng,
    map: this.map,
    icon
  })
  return marker
}

Maps.prototype.getMarker = function (location) {
  var marker = this.markers.find((marker) => {
    if (this.isLatLngEqual(marker.getPosition().lat(), location.latitude, marker.getPosition().lng(), location.longitude)) {
      return marker
    }
  })
  return marker
}

Maps.prototype.registerMapEvents = function () {
  const vm = this
  var infoButton = document.getElementById('infoButton')
  if (infoButton) {
    infoButton.onclick = function (e) {
      vm.logger('Selected Location', infoButton.dataset)
      vm.clickLocation(vm.generateLocationJSON(infoButton.dataset.latitude, infoButton.dataset.longitude))
      vm.infoWindow.close()
    }
  }
}

Maps.prototype.registerListEvents = function () {
  var lists = document.getElementsByClassName('maps-list')
  var vm = this
  for( var i = 0; i < lists.length; i++ ) {
    var list = lists[i]
    list.onclick = function(e) {
      var location = vm.generateLocationJSON(e.target.dataset.latitude, e.target.dataset.longitude)
      vm.selectLocation(location)
    }
  }
}

Maps.prototype.isLatLngEqual = function (lat1, lat2, lng1, lng2) {
  if (parseFloat(lat1).toFixed(7) === parseFloat(lat2).toFixed(7) && parseFloat(lng1).toFixed(7) === parseFloat(lng2).toFixed(7)) {
    return true
  }
  return false
}
Maps.prototype.generateLocationJSON = function (lat, lng) {
  var vm = this
  var location = this.locations.find(function(location) {
    return vm.isLatLngEqual(location.latitude, lat, location.longitude, lng)
  })
  return location
}

Maps.prototype.clickLocation = function (currentLocation) {
  var previousLocation = this.location
  this.location = currentLocation
  this.setMarkerIcon(previousLocation, this.beforeIcon)
  this.setMarkerIcon(currentLocation, this.afterIcon)
  this.locationSelectCallback(currentLocation)
  console.log('hello')
}

Maps.prototype.selectLocation = function(location, marker = null) {
  if (this.needZoom) {
    this.map.setZoom(18)
    this.map.setCenter({
      lat: Number(location.latitude),
      lng: Number(location.longitude)
    })
    this.setPan()
  }
  if (this.needPopUp) {
    if (!marker) {
      marker = this.getMarker(location)
    }
    this.openInfoWindow(marker, location)
  } else {
    this.clickLocation(location)
  }
}

Maps.prototype.setLocationSelectCallBack = function (callback) {
  this.locationSelectCallback = callback
}

Maps.prototype.setPan = function () {
  if (this.panX) {
    this.map.panBy(this.panX, 0)
  }
}

Maps.prototype.setMarkerIcon = function (location, icon) {
  var marker = this.getMarker(location)
  if (marker) {
    marker.setIcon(icon)
  }
}

Maps.prototype.setCenter = function (lat, lng) {
  if (lat && lng) {
    this.map.setCenter({ lat: Number(lat), lng: Number(lng) });
  }
}

export default Maps
