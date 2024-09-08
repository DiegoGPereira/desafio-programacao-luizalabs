const storage = typeof localStorage !== 'undefined' ? localStorage : null;

export function getToken() {
  const token = storage ? storage.getItem('token') : null;
  return token;
}

export function setToken(token) {
  if (storage) {
    storage.setItem('token', token);
  }
}

export const isAuthenticated = () => {
  const token = storage ? storage.getItem('token') : null;
  if (!token) {
    return false;
  }
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const isExpired = payload.exp * 1000 < Date.now();
    return !isExpired;
  } catch (e) {
    return false;
  }
};