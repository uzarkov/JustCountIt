import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#353535',
        alignItems: 'center'
    },
    titleContainer: {
        flex: 0.15,
        alignItems: 'center',
        justifyContent: 'center',
    },
    title: {
        color: 'white',
        fontSize: 21,
        fontWeight: 'bold',
        letterSpacing: 1
    },
    listContainer: {
        flex: 0.7,
        width: '85%'
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
})