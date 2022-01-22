import { APP_URL } from "../config/constants"
import { readData } from "./asyncStorage"

export const doPost = async (endpoint, body = {}) => {
    let headers = {
        'Content-Type': 'application/json',
    }

    const jwt = await readData("JWT")
    if (jwt) {
        headers['Authorization'] = `Bearer ${jwt}`
    }

    return fetch(APP_URL + endpoint, {
        method: 'POST',
        headers: headers,
        body: body
    })
}