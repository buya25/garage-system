import React from 'react';
import { useNotifications } from '../context/NotificationContext';

export default function NotificationsPanel() {
  const notifications = useNotifications();

  return (
    <div className="notifications-panel fixed top-0 right-0 m-4 w-80">
      {notifications.map((n, idx) => (
        <div key={idx} className="mb-2 p-2 bg-white shadow rounded">
          <strong>{n.type.toUpperCase()}</strong> 
          <span className="text-sm text-gray-600">
            {new Date(n.timestamp).toLocaleTimeString()}
          </span>
          <pre className="text-xs mt-1">{JSON.stringify(n.data, null, 2)}</pre>
        </div>
      ))}
    </div>
  );
}