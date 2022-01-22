import React, { useState } from 'react';
import { storeData } from './src/utils/asyncStorage';
import { doPost } from './src/utils/fetchUtils';
import { GroupsView } from './src/views/groups/GroupsView';
import { SignInView } from './src/views/sign-in/SignInView';

export default function App() {
  const [user, setUser] = useState({ signedIn: false })

  const signIn = (email, password) => {
    const body = JSON.stringify({
      email: email,
      password: password,
    })

    doPost("/api/auth/login", body)
      .then(response => {
        if (!response.ok) {
          throw new Error("Invalid credentials")
        }

        const jwt = response.headers.get("Authorization")
        storeData("JWT", jwt)
        return response.json()
      })
      .then(userInfo => {
        setUser({
          ...user,
          signedIn: true,
          ...userInfo,
        })
      })
      .catch(error => console.error(error))
  }

  const logout = () => {
    setUser({ signedIn: false })
  }

  return (
    <>
      {user.signedIn ?
        <GroupsView user={user} logout={() => logout()} />
        :
        <SignInView signIn={(email, password) => signIn(email, password)} />
      }
    </>
  );
}
