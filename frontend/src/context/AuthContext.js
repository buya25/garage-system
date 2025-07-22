import React, { createContext, useState, useEffect, useContext } from 'react';
import jwtDecode from 'jwt-decode';

const AuthCtx = createContext();

export function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [user, setUser] = useState(null);

  useEffect(() => {
    if (token) {
      const decoded = jwtDecode(token);
      setUser({ username: decoded.sub, roles: decoded.roles.map(r => r.authority) });
    }
  }, [token]);

  const login = newToken => { localStorage.setItem('token', newToken); setToken(newToken); };
  const logout = () => { localStorage.removeItem('token'); setToken(null); setUser(null); };

  return (
    <AuthCtx.Provider value={{ user, token, login, logout }}>
      {children}
    </AuthCtx.Provider>
  );
}

export const useAuth = () => useContext(AuthCtx);