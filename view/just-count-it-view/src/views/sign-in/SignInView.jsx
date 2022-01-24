import React, { useState } from 'react';
import { Keyboard, Pressable, Text, TextInput, TouchableWithoutFeedback, View } from 'react-native';
import { styles } from './SignInViewStyles';
import { globalStyles } from "../../styles/globalStyles";

export const SignInView = ({ signIn }) => {
    const [emailInput, setEmailInput] = useState('');
    const [passwordInput, setPasswordInput] = useState('');

    const onSignIn = () => {
        signIn(emailInput, passwordInput)
        setEmailInput('');
        setPasswordInput('');
    }

    return (
        <TouchableWithoutFeedback
            onPress={Keyboard.dismiss}
            accessible={false}
        >
            <View style={styles.container}>
                <View style={styles.titleContainer}>
                    <View style={styles.basicContainer}>
                        <Text style={styles.title}>{"JustCountIt"}</Text>
                    </View>
                </View>
                <View style={styles.inputContainer}>
                    <TextInput
                        style={[globalStyles.inputAndroid, styles.input]}
                        placeholder={"Email"}
                        placeholderTextColor={"grey"}
                        value={emailInput}
                        onChangeText={(text) => setEmailInput(text)}
                    />
                    <TextInput
                        style={[globalStyles.inputAndroid, styles.input]}
                        placeholder={"Hasło"}
                        placeholderTextColor={"grey"}
                        value={passwordInput}
                        onChangeText={(text) => setPasswordInput(text)}
                        secureTextEntry={true}
                    />
                </View>
                <View style={styles.submitContainer}>
                    <Pressable
                        style={[globalStyles.button, styles.button]}
                        onPress={() => onSignIn()}
                    >
                        <Text style={styles.buttonText}>{"Zaloguj się"}</Text>
                    </Pressable>
                </View>
            </View>
        </TouchableWithoutFeedback>
    )
}

