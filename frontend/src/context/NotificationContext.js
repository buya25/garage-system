import React, { createContext, useContext, useReducer, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

// Actions
const ADD_NOTIFICATION = 'ADD_NOTIFICATION';

// Reducer
function notifReducer(state, action) {
  switch (action.type) {
    case ADD_NOTIFICATION:
      return [action.payload, ...state];
    default:
      return state;
  }
}

// Context
const NotificationStateCtx = createContext();
const NotificationDispatchCtx = createContext();

export function NotificationProvider({ children }) {
  const [notifications, dispatch] = useReducer(notifReducer, []);

  useEffect(() => {
    const socket = new SockJS(`${process.env.REACT_APP_BASE_URL}/ws`);
    const client = new Client({
      webSocketFactory: () => socket,
      debug: () => {},
      reconnectDelay: 5000,
      onConnect: () => {
        client.subscribe('/topic/jobcards/approval', message => {
          const card = JSON.parse(message.body);
          dispatch({ type: ADD_NOTIFICATION, payload: {
            type: 'approval',
            data: card,
            timestamp: Date.now()
          }});
        });
        client.subscribe('/topic/assignments', message => {
          const assignment = JSON.parse(message.body);
          dispatch({ type: ADD_NOTIFICATION, payload: {
            type: 'assignment',
            data: assignment,
            timestamp: Date.now()
          }});
        });
        client.subscribe('/topic/assignments/progress', message => {
          const progress = JSON.parse(message.body);
          dispatch({ type: ADD_NOTIFICATION, payload: {
            type: 'progress',
            data: progress,
            timestamp: Date.now()
          }});
        });
      }
    });
    client.activate();
    return () => client.deactivate();
  }, []);

  return (
    <NotificationStateCtx.Provider value={notifications}>
      <NotificationDispatchCtx.Provider value={dispatch}>
        {children}
      </NotificationDispatchCtx.Provider>
    </NotificationStateCtx.Provider>
  );
}

export function useNotifications() {
  return useContext(NotificationStateCtx);
}
export function useNotificationDispatch() {
  return useContext(NotificationDispatchCtx);
}