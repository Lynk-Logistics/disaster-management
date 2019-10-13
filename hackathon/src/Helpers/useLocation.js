function useLocation(){
    
    const showPosition = (position) => {
        let latitude = position.coordinates.latitude;
        let longitude = position.coordinates.longitude;
        return {location: {
            lat: latitude,
            long: longitude
        }};
    }

    const errorHandler = (err) => {
        console.warn('ERROR(' + err.code + '): ' + err.message);
    }

    const pos = navigator.getCurrentPosition(showPosition, errorHandler);
    
    export default pos;
}