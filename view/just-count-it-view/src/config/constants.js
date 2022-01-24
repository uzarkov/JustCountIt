import Constants from 'expo-constants';

const { manifest } = Constants

export const APP_URL = `http://${manifest.debuggerHost.split(':').shift()}:8080`;
