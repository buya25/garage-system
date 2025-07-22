import { BrowserRouter, Routes, Route } from 'react-router-dom';
import DashboardPage from './modules/Dashboard/DashboardPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* other routes */}
        <Route path="/admin/dashboard" element={<DashboardPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;