import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#353535',
    },
    titleContainer: {
        flex: 1,
        justifyContent: 'flex-end',
    },
    inputContainer: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    submitContainer: {
        flex: 1.6,
        alignItems: 'center',
    },
    input: {
        height: 60,
        width: 320,
    },
    button: {
        height: 60,
        width: 250,
        marginBottom: 30
    },
    buttonText: {
        color: '#353535',
        fontSize: 20,
        fontWeight: 'bold'
    },
    link: {
        color: 'white',
        backgroundColor: '#353535',
        fontSize: 18,
    },
})