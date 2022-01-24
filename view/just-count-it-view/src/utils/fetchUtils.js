import { APP_URL } from "../config/constants"
import { readData } from "./asyncStorage"

export const doGet = async (endpoint, body = {}) => {
    const headers = await addAuthorizationHeader({
        'Content-Type': 'application/json',
    })

    return fetch(APP_URL + endpoint, {
        method: 'GET',
        headers: headers,
        body: body
    })
}

export const doPost = async (endpoint, body = {}) => {
    const headers = await addAuthorizationHeader({
        'Content-Type': 'application/json',
    })

    return fetch(APP_URL + endpoint, {
        method: 'POST',
        headers: headers,
        body: body
    })
}

const addAuthorizationHeader = async (headers) => {
    const jwt = await readData("JWT")

    if (jwt) {
        return {
            ...headers,
            ['Authorization']: `Bearer ${jwt}`,
        }
    }

    return headers
}