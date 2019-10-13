import axios from 'axios';
import { SERVER_URL } from '../config'


// export const getEssentials = (issueId) => {
//   return axios
//     .get(SERVER_URL + '/essemtials/' + issueId + '/donor-recommendations')
// }

export const getAllEssentials = () => {
    return axios
      .get(SERVER_URL + '/essentials')
  }

  export const createEssential = (essential) => {
    return axios
      .post(SERVER_URL + '/essentials?essential=' + essential)
  }