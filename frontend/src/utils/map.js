const mapboxPolyline = require('@mapbox/polyline');

export const decodePolyline = overviewPolyline =>
  mapboxPolyline
    .decode(overviewPolyline)
    .map(item => ({ lat: item[0], lng: item[1] }));
