import axios from 'axios';
import { SERVER_URL } from '../config'

export const getAllIssues = () => {
    return axios
        .get(SERVER_URL + '/issues')
}

export const createVolunteer = (data) => {
    return axios
        .post(SERVER_URL + '/volunteers', data)
}


export const acknowledgeIssue = (issueId, data) => {
    return axios
        .post(SERVER_URL + '/issues/' + issueId + '/acknowledge', data)
}

export const getNonAcknowledgedIssues = (volunteer_id = null) => {
    if (volunteer_id === null) {
        return axios
            .get(SERVER_URL + '/issues')
    }
    return axios
        .get(SERVER_URL + '/volunteers/' + volunteer_id + '/non-acknowledged-issues')
}

export const getAcknowledgedIssues = (volunteer_id) => {
    return axios
        .get(SERVER_URL + '/volunteers/' + volunteer_id + '/acknowledged-issues')
}