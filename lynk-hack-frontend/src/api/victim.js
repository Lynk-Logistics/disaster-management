import axios from 'axios';
import { SERVER_URL } from '../config'

export const getIssuesBasedOnCurrentLocation = (lat, long, radius = 1000) => {
  return axios
    .get(SERVER_URL + '/issues?long=' + long + '&lat=' + lat + '&radius=' + radius)
}

export const getParticularIssue = (issueId) => {
  return axios
    .get(SERVER_URL + '/issues/' + issueId)
}

export const plusOne = (issueId) => {
  return axios
    .post(SERVER_URL + '/issues/' + issueId + '/plus-one')
}

export const report = (issueId) => {
  return axios
    .post(SERVER_URL + '/issues/' + issueId + '/report')
}

export const createIssue = (data) => {
  return axios
    .post(SERVER_URL + '/issues', data)
}