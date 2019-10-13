const API_URL = 'https://lynk-aid-backend.herokuapp.com/'

module.exports = {
    get_list_of_regions: `${API_URL}zone/getallzones`,
    set_service_Zone : `${API_URL}restorepoint/createrestorepoint`,
    get_nearby_details : `${API_URL}restorepoint/getnearbydetails`
}