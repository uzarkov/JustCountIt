import { APP_URL } from "../config/constants"
import { readData } from "./asyncStorage"

const checkForError = async (response) => {
    if (!response.ok) {
        if (response.headers.get('content-type') === 'application/json') {
            const json = await response.json()
            throw new Error(json.message)
        }
        throw new Error("Something went wrong")
    }
    return response
}

export const doGet = async (endpoint) => {
    const headers = await addAuthorizationHeader({
        'Accept': 'application/json',
    })

    return fetch(APP_URL + endpoint, {
        method: 'GET',
        headers: headers,
    })
        .then(response => checkForError(response))
}

export const doPost = async (endpoint, body = {}, authorize = true) => {
    let headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    }

    if (authorize) {
        headers = await addAuthorizationHeader(headers)
    }

    return fetch(APP_URL + endpoint, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(body)
    })
        .then(response => checkForError(response))
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