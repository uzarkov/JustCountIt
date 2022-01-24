import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#222222',
    },
    icon: {
        textAlign: 'center'
    },
    button: {
        flex: 1,
        height: 40,
        paddingTop: 6,
        marginBottom: 28
    },
    buttonText: {
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    activeStripe: {
        width: '100%',
        height: 2,
        marginTop: 8
    },
})