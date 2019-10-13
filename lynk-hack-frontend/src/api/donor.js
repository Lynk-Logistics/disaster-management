import axios from 'axios';
import { SERVER_URL } from '../config'


export const getDonorsByIssueId = (issueId) => {
    return axios
        .get(SERVER_URL + '/issues/' + issueId + '/donor-recommendations')
}

export const getDonorByDonorId = (donorId) => {
    return axios
        .get(SERVER_URL + '/donors/' + donorId)
}

export const getAllDonors = () => {
    return axios
        .get(SERVER_URL + '/donors')
}


export const createDonation = (donor_id, data) => {
    return axios
        .post(SERVER_URL + '/donors/' + donor_id + '/donate', data)
}


export const getEssentialRequirements = () => {
    return axios
        .get(SERVER_URL + '/donors/essential-requirements')
}

export const createDonor = (data) => {
    return axios
        .post(SERVER_URL + '/donors', data)
}

export const getDonations = (donorId) => {
    return axios
        .get(SERVER_URL + '/donors/' + donorId + '/donate')
}