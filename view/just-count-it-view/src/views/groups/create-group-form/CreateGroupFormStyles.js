import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
      alignItems: 'center',
    },
    picker: {
        backgroundColor: 'transparent',
        borderWidth: 1,
        borderRadius: 14,
        borderColor: '#BB86FC',
        height: 45,
        marginTop: 5,
        fontSize: 20,
        paddingHorizontal: 15
    },
    pickerText: {
      fontSize: 20,
        color: 'white'
    },
    inputContainer: {
        height: 320,
        marginTop: 20,
        justifyContent: 'flex-start',
        width: '80%',
    },
    label: {
      color: 'white',
      fontSize: 20,
      letterSpacing: 1
    },
    input: {
        marginTop: 5,
        height: 45
    },
    buttonsContainer: {
        width: '90%',
        flexDirection: 'row',
        justifyContent: 'space-around',
        paddingBottom: 10,
        paddingHorizontal: 30
    },
    buttonText: {
        letterSpacing: 1,
        color: '#BB86FC',
        fontSize: 20
    },
    buttonTextLocked: {
        opacity: 0.35,
    },
})