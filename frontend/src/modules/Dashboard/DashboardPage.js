import React, { useEffect, useState } from 'react';
import { Bar, Pie } from 'react-chartjs-2';
import { Chart, ArcElement, CategoryScale, LinearScale, BarElement, Tooltip, Legend } from 'chart.js';
import { fetchJobsByStatus, fetchTopParts } from './MetricsApi';

// Register Chart.js components
Chart.register(ArcElement, CategoryScale, LinearScale, BarElement, Tooltip, Legend);

export default function DashboardPage() {
  const [jobsData, setJobsData] = useState([]);
  const [partsData, setPartsData] = useState([]);

  useEffect(() => {
    fetchJobsByStatus().then(res => setJobsData(res.data));
    fetchTopParts().then(res => setPartsData(res.data));
  }, []);

  const jobsChart = {
    labels: jobsData.map(j => j.status),
    datasets: [{ data: jobsData.map(j => j.count), label: 'Jobs by Status' }]
  };

  const partsChart = {
    labels: partsData.map(p => p.partNumber),
    datasets: [{ data: partsData.map(p => p.totalConsumed), label: 'Top Parts Used' }]
  };

  return (
    <div className="p-4 grid grid-cols-1 md:grid-cols-2 gap-4">
      <div className="card p-4">
        <h2 className="text-xl mb-2">Jobs by Status</h2>
        <Pie data={jobsChart} />
      </div>
      <div className="card p-4">
        <h2 className="text-xl mb-2">Top Parts Usage</h2>
        <Bar data={partsChart} />
      </div>
    </div>
  );
}

export const api = {
  baseURL: process.env.REACT_APP_BASE_URL || 'http://localhost:8080/api',
};