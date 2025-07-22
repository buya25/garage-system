import axios from '../../api/axios';

export const fetchJobsByStatus = () =>
  axios.get('/admin/metrics/jobs-by-status');

export const fetchTopParts = () =>
  axios.get('/admin/metrics/top-parts');